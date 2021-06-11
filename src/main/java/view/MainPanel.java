package view;

import controller.*;
import controller.menubar.MenuBarActionImport;
import controller.menubar.MenuBarActionLoad;
import controller.menubar.ImageNewActionNew;
import controller.menubar.MenuBarActionSave;
import model.AsciiFont;
import model.AsciiPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class MainPanel extends JFrame {
    private AsciiPanel asciiPanel;
    private int currentToolId = 0;
    private int selectedChar = 1;
    private Color defaultForegroundColor;
    private Color defaultBackgroundColor;

    private AsciiPanel charPreviewPanel;
    private JPanel foregroundColorPanel;
    private JPanel backgroundColorPanel;

    private JButton paint, fill, pick;

    private BufferedImage importedBufferedImage;

    private JPanel mainContainer;

    private static MainPanel instance;

    public static MainPanel getInstance() {
        if (instance == null) instance = new MainPanel();
        return instance;
    }

    private MainPanel() {
        super("Pannello principale");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setMinimumSize(new Dimension(700, 700));
        this.setLayout(new BorderLayout());

        //---------------------MenuBar items-------------------------
        //Creates the Menu Bar and puts it on the top of the window
        JMenuBar menuBar = new JMenuBar();
        JMenu menuBarFile = new JMenu("File");
        menuBar.add(menuBarFile);
        this.add(menuBar, BorderLayout.NORTH);
        JMenuItem menuBarFileNew = new JMenuItem("New...");
        JMenuItem menuBarFileLoad = new JMenuItem("Load...");
        JMenuItem menuBarFileSave = new JMenuItem("Save...");
        JMenuItem menuBarFileImport = new JMenuItem("Import...");
        menuBarFile.add(menuBarFileNew);
        menuBarFile.add(menuBarFileLoad);
        menuBarFile.add(menuBarFileSave);
        menuBarFile.add(menuBarFileImport);

        //MenuBar action listeners
        menuBarFileNew.addActionListener(new MenuBarActionNew());
        menuBarFileLoad.addActionListener(new MenuBarActionLoad());
        menuBarFileSave.addActionListener(new MenuBarActionSave());
        menuBarFileImport.addActionListener(new MenuBarActionImport());



        //Creates the main container
        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        this.add(mainContainer, BorderLayout.CENTER);

        //JPanel containing the buttons
        JPanel buttonPanel = new JPanel();
        paint = ButtonFactory.createToolButton("Paint", "src/main/resources/pencil.png");
        fill = ButtonFactory.createToolButton("Fill", "src/main/resources/bucket.png");
        pick = ButtonFactory.createToolButton("Pick", "src/main/resources/tap.png");
        buttonPanel.add(paint);
        buttonPanel.add(fill);
        buttonPanel.add(pick);
        paint.setBackground(Color.GRAY);

        //JPanel containing color selection and color display
        JPanel colorPanel = new JPanel(new GridLayout(2, 2));
        foregroundColorPanel = new JPanel();
        backgroundColorPanel = new JPanel();
        foregroundColorPanel.setBackground(AsciiPanel.WHITE);
        backgroundColorPanel.setBackground(Color.black);
        foregroundColorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        backgroundColorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        foregroundColorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backgroundColorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        colorPanel.setPreferredSize(new Dimension(100, 100));
        colorPanel.add(foregroundColorPanel);
        colorPanel.add(new JPanel()); //aggiungo due pannelli vuoti per dare l'effetto scacchiera
        colorPanel.add(new JPanel());
        colorPanel.add((backgroundColorPanel));


        //JPanel containing the character preview and character selector
        JPanel characterPanel = new JPanel(new BorderLayout());

        charPreviewPanel = new AsciiPanel(3,3, AsciiFont.CP437_16x16);
        charPreviewPanel.setBackground(Color.BLACK);
        charPreviewPanel.setPreferredSize(new Dimension(40,50));
        characterPanel.add(charPreviewPanel, BorderLayout.CENTER);
        characterPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JButton previousCharacter = ButtonFactory.createCharacterSelectorButton("src/main/resources/previous.png");
        JButton nextCharacter = ButtonFactory.createCharacterSelectorButton("src/main/resources/next.png");

        JPanel characterNextPrevSelectorPanel = new JPanel();
        characterNextPrevSelectorPanel.add(previousCharacter);
        characterNextPrevSelectorPanel.add(nextCharacter);
        characterPanel.add(characterNextPrevSelectorPanel, BorderLayout.SOUTH);


        //JPanel containing other JPanels that contains tools
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolsPanel.setMinimumSize(new Dimension(300, 110));
        toolsPanel.setPreferredSize(new Dimension(300, 110));
        toolsPanel.add(buttonPanel);
        toolsPanel.add(characterPanel);
        toolsPanel.add(colorPanel);
        mainContainer.add(toolsPanel, BorderLayout.NORTH);

        charPreviewPanel.write((char) selectedChar, 1, 1);
        charPreviewPanel.repaint();

        //JPanel containing the AsciiPanel
        asciiPanel = new AsciiPanel(80, 60, AsciiFont.CP437_16x16);
        mainContainer.add(asciiPanel, BorderLayout.CENTER);

        asciiPanel.write("Marion");
        asciiPanel.setCursorX(0);
        asciiPanel.setCursorY(0);


        // -------------------------------------Add Listeners-------------------------------------------------
        asciiPanel.addMouseListener(new AsciiPanelMouseListener(this));
        asciiPanel.addMouseMotionListener(new AsciiPanelMouseMotionListener(this));

        // Adds character selector event listeners
        characterPanel.addMouseListener(new CharacterPanelMouseListener());
        previousCharacter.addActionListener(new PreviousCharacterActionListener());
        nextCharacter.addActionListener(new NextCharacterActionListener());

        //-------------------Color Panels Mouse Listeners-----------------
        foregroundColorPanel.addMouseListener(new ForegroundColorPanelMouseListener(this));
        backgroundColorPanel.addMouseListener(new BackgroundColorPanelMouseListener(this));


        // -------Change currentToolId on tool button click-------
        //TODO inserisci degli action listener ad hoc da mettere nel controller
        paint.addActionListener(e -> {
            setCurrentToolId(0);
            ToolsPanelController.updateSelectedToolButtonBackground();
        });
        pick.addActionListener(e -> {
            setCurrentToolId(1);
            ToolsPanelController.updateSelectedToolButtonBackground();
        });
        fill.addActionListener(e -> {
            setCurrentToolId(2);
            ToolsPanelController.updateSelectedToolButtonBackground();
        });





    }

    public AsciiPanel getAsciiPanel() {
        return asciiPanel;
    }

    public void setAsciiPanel(AsciiPanel asciiPanel) {
        this.asciiPanel = asciiPanel;
    }

    /**
     * An integer that identifies the current selected tool
     */
    public int getCurrentToolId() {
        return currentToolId;
    }

    public void setCurrentToolId(int currentToolId) {
        this.currentToolId = currentToolId;
    }

    /**
     * The selected character is the character on which the focus is on.
     * This character is always displayed on the preview panel.
     */
    public int getSelectedChar() {
        return selectedChar;
    }

    public void setSelectedChar(int selectedChar) {
        this.selectedChar = selectedChar;
    }


    public AsciiPanel getCharPreviewPanel() {
        return charPreviewPanel;
    }


    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public Color getDefaultForegroundColor() {
        return defaultForegroundColor;
    }

    public void setDefaultForegroundColor(Color defaultForegroundColor) {
        this.defaultForegroundColor = defaultForegroundColor;
    }

    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public JPanel getForegroundColorPanel() {
        return foregroundColorPanel;
    }

    public JPanel getBackgroundColorPanel() {
        return backgroundColorPanel;
    }

    public JButton getPaint() {
        return paint;
    }

    public JButton getFill() {
        return fill;
    }

    public JButton getPick() {
        return pick;
    }

    public BufferedImage getImportedBufferedImage() {
        return importedBufferedImage;
    }

    public void setImportedBufferedImage(BufferedImage importedBufferedImage) {
        this.importedBufferedImage = importedBufferedImage;
    }

    public JPanel getMainContainer() {
        return mainContainer;
    }


    public static void main(String[] args) {
        MainPanel.getInstance().setVisible(true);
    }
}
