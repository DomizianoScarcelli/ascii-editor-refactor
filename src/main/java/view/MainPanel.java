package view;

import controller.AsciiPanelMouseListener;
import controller.AsciiPanelMouseMotionListener;
import model.AsciiFont;
import model.AsciiPanel;

import javax.swing.*;
import java.awt.*;


public class MainPanel extends JFrame {
    private AsciiPanel asciiPanel;
    private int currentToolId = 0;
    private int selectedChar = 1;
    private JButton selectCharButton;
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

        //Creates the Menu Bar and puts it on the top of the window
        JMenuBar menuBar = new JMenuBar();
        JMenuItem file = new JMenuItem("File");
        menuBar.add(file);
        this.add(menuBar, BorderLayout.NORTH);

        //Creates the main container
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        this.add(mainContainer, BorderLayout.CENTER);

        //JPanel containing the buttons
        JPanel buttonPanel = new JPanel();
        JButton paint = ButtonFactory.createToolButton("Paint", "src/main/resources/pencil.png");
        JButton fill = ButtonFactory.createToolButton("Fill", "src/main/resources/bucket.png");
        JButton pick = ButtonFactory.createToolButton("Pick", "src/main/resources/tap.png");
        buttonPanel.add(paint);
        buttonPanel.add(fill);
        buttonPanel.add(pick);

        //JPanel containing color selection and color display
        JPanel colorPanel = new JPanel(new GridLayout(2, 2));
        JPanel foregroundColorPanel = new JPanel();
        JPanel backgroundColorPanel = new JPanel();
        foregroundColorPanel.setBackground(Color.black);
        backgroundColorPanel.setBackground(Color.white);
        foregroundColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundColorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        colorPanel.setPreferredSize(new Dimension(100, 100));
        colorPanel.add(foregroundColorPanel);
        colorPanel.add(new JPanel()); //aggiungo due pannelli vuoti per dare l'effetto scacchiera
        colorPanel.add(new JPanel());
        colorPanel.add((backgroundColorPanel));

        //JPanel containing the character preview and character selector
        JPanel characterPanel = new JPanel(new BorderLayout());
        JPanel previewCharacter = new JPanel();
        previewCharacter.setPreferredSize(new Dimension(50, 50));
        previewCharacter.setBackground(Color.BLACK);
        JLabel selectedCharacterLabel = new JLabel("Character");
        characterPanel.add(previewCharacter, BorderLayout.CENTER);
        characterPanel.add(selectedCharacterLabel, BorderLayout.SOUTH);

        //JPanel containing other JPanels that contains tools
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolsPanel.setMinimumSize(new Dimension(300, 110));
        toolsPanel.setPreferredSize(new Dimension(300, 110));
        toolsPanel.add(buttonPanel);
        toolsPanel.add(characterPanel);
        toolsPanel.add(colorPanel);
        mainContainer.add(toolsPanel, BorderLayout.NORTH);

        //JPanel containing the AsciiPanel
        asciiPanel = new AsciiPanel(80, 60, AsciiFont.CP437_16x16);
        mainContainer.add(asciiPanel, BorderLayout.CENTER);

        asciiPanel.write("Marion");
        asciiPanel.setCursorX(0);
        asciiPanel.setCursorY(0);


        // -------------------------------------Add Listeners-------------------------------------------------
        asciiPanel.addMouseListener(new AsciiPanelMouseListener(this));
        asciiPanel.addMouseMotionListener(new AsciiPanelMouseMotionListener(this));

        // -------Change currentToolId on tool button click-------
        //Todo implementa un modo di riuso del codice per il cambio di sfondo
        paint.addActionListener(e -> {
            setCurrentToolId(0);
            paint.setBackground(Color.GRAY);
            pick.setBackground(Color.WHITE);
            fill.setBackground(Color.WHITE);
        });
        pick.addActionListener(e -> {
            setCurrentToolId(1);
            paint.setBackground(Color.WHITE);
            pick.setBackground(Color.GRAY);
            fill.setBackground(Color.WHITE);
        });
        fill.addActionListener(e -> {
            setCurrentToolId(2);
            paint.setBackground(Color.WHITE);
            pick.setBackground(Color.WHITE);
            fill.setBackground(Color.GRAY);
        });
    }

    public AsciiPanel getAsciiPanel() {
        return asciiPanel;
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

    /**
     * The {@link JButton} component that allows to select a character from a grid of characters.
     */
    public JButton getSelectCharButton() {
        return selectCharButton;
    }

    public static void main(String[] args) {
        MainPanel.getInstance().setVisible(true);
    }
}
