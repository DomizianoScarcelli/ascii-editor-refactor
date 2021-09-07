package view;

import controller.*;
import controller.commands.*;
import controller.commands.copycutpaste.MoveCommand;
import controller.commands.copycutpaste.SelectCommand;
import controller.commands.shapes.CircleCommand;
import controller.commands.shapes.RectCommand;
import controller.commands.shapes.SquareCommand;
import controller.menubar.MenuBarActionImport;
import controller.menubar.MenuBarActionLoad;
import controller.menubar.MenuBarActionSave;
import model.AsciiFont;
import model.AsciiPanel;
import model.CommandStack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//TODO documenta tutto

public class MainPanel extends JFrame {
    private AsciiPanel asciiPanel;
    private int selectedChar = 1;
    private Color defaultForegroundColor = Color.WHITE;
    private Color defaultBackgroundColor = Color.BLACK;

    private AsciiPanel charPreviewPanel;
    private JPanel foregroundColorPanel;
    private JPanel backgroundColorPanel;

    private JButton paint, fill, pick;


    private BufferedImage importedBufferedImage;

    private JPanel mainContainer;

    private int currentButtonPressed = 1;

    private CommandStack commandStack = new CommandStack();
    private CommandStack redoCommandStack = new CommandStack();

    public SelectCommand currentSelection;
    public char[][] beforeSelectionGrid;
    public ArrayList<int[]> selectedPoints;
    public ArrayList<int[]> selectionChars = new ArrayList<>();

    private Command command;

    private AsciiPanelMouseListener asciiPanelMouseListener;
    private AsciiPanelMouseMotionListener asciiPanelMouseMotionListener;

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

        this.command = new PaintCommand(this);

        //---------------------MenuBar items-------------------------
        //Creates the Menu Bar and puts it on the top of the window
        JMenuBar menuBar = new JMenuBar();
        JMenu menuBarFile = new JMenu("File");

        //Undo button and listener
        JButton undo = ButtonFactory.menuBarButton("src/main/resources/undo.png");
        menuBar.add(undo);
        undo.addActionListener(e -> {
//            Command command = commandStack.pop();
//            command.undo();
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute();

        });

        JButton redo = ButtonFactory.menuBarButton("src/main/resources/redo.png");
        menuBar.add(redo);
        redo.addActionListener(e -> {
            System.out.println("sium");
            Command command = redoCommandStack.pop();
            command.execute();
        });


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

        charPreviewPanel = new AsciiPanel(3, 3, AsciiFont.CP437_16x16);
        charPreviewPanel.setBackground(Color.BLACK);
        charPreviewPanel.setPreferredSize(new Dimension(40, 50));
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


        JButton squareButton = new JButton("Quadrato");
        toolsPanel.add(squareButton);
        JButton circleButton = new JButton("Cerchio");
        toolsPanel.add(circleButton);
        JButton rectButton = new JButton("Rettangolo");
        toolsPanel.add(rectButton);
        JButton selectButton = new JButton("Seleziona");
        toolsPanel.add(selectButton);
        JButton moveButton = new JButton("Muovi");
        toolsPanel.add(moveButton);


        squareButton.addActionListener(e -> {
            this.command = new SquareCommand(1);
        });
        circleButton.addActionListener(e -> {
            this.command = new CircleCommand(1);
        });
        rectButton.addActionListener(e -> {
            this.command = new RectCommand(asciiPanel.getMouseCursorX(), asciiPanel.getMouseCursorY(), asciiPanel.getMouseCursorX(), asciiPanel.getMouseCursorY());
        });
        selectButton.addActionListener(e -> {
            this.command = new SelectCommand(asciiPanel.getMouseCursorX(), asciiPanel.getMouseCursorY(), asciiPanel.getMouseCursorX(), asciiPanel.getMouseCursorY());
        });
        moveButton.addActionListener(e -> {
            this.command = new MoveCommand(asciiPanel.getMouseCursorX(), asciiPanel.getMouseCursorY(), asciiPanel.getMouseCursorX(), asciiPanel.getMouseCursorY());
        });


        mainContainer.add(toolsPanel, BorderLayout.NORTH);

        charPreviewPanel.write((char) selectedChar, 1, 1);
        charPreviewPanel.repaint();

        //JPanel containing the AsciiPanel
        asciiPanel = new AsciiPanel(80, 60, AsciiFont.CP437_16x16);
        mainContainer.add(asciiPanel, BorderLayout.CENTER);

        asciiPanel.write("Marion");
        asciiPanel.setCursorX(0);
        asciiPanel.setCursorY(0);

        asciiPanelMouseListener = new AsciiPanelMouseListener(this);
        asciiPanelMouseMotionListener = new AsciiPanelMouseMotionListener(this);

        //Initialize beforeSelectionGrid
        beforeSelectionGrid = new char[asciiPanel.getChars().length][asciiPanel.getChars()[0].length];

        // -------------------------------------Add Listeners-------------------------------------------------
        asciiPanel.addMouseListener(asciiPanelMouseListener);
        asciiPanel.addMouseMotionListener(asciiPanelMouseMotionListener);

        // Adds character selector event listeners
        characterPanel.addMouseListener(new CharacterPanelMouseListener());
        previousCharacter.addActionListener(new PreviousCharacterActionListener());
        nextCharacter.addActionListener(new NextCharacterActionListener());

        //-------------------Color Panels Mouse Listeners-----------------
        foregroundColorPanel.addMouseListener(new ColorPanelMouseListener(foregroundColorPanel, false));
        backgroundColorPanel.addMouseListener(new ColorPanelMouseListener(backgroundColorPanel, true));


        // -------Change current command on tool button click-------

        paint.addActionListener(e -> {
            ToolsPanelController.selectPaintButton();
            this.command = new PaintCommand(this);
        });
        pick.addActionListener(e -> {
            ToolsPanelController.selectPickButton();
            this.command = new PickCommand(this);
        });
        fill.addActionListener(e -> {
            ToolsPanelController.selectFillButton();
            this.command = new FillCommand(this);
        });

        this.addKeyListener(new MainPanelKeyListener());
        this.setFocusable(true);

        //Change cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src/main/resources/whiteIcons/pencil22.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(asciiPanel.getX(), asciiPanel.getY()), "img");
        asciiPanel.setCursor(c);
    }

    public AsciiPanel getAsciiPanel() {
        return asciiPanel;
    }

    public void setAsciiPanel(AsciiPanel asciiPanel) {
        this.asciiPanel = asciiPanel;
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

    public Color getInverseDefaultBackgroundColor(){
        int red = getDefaultBackgroundColor().getRed();
        int green = getDefaultBackgroundColor().getGreen();
        int blue = getDefaultBackgroundColor().getBlue();
        int newRed = Math.abs(red - 255);
        int newGreen = Math.abs(green - 255);
        int newBlue = Math.abs(blue - 255);
        return new Color(newRed, newGreen, newBlue);
    }

    public Color getInverseDefaultForegroundColor(){
        int red = getDefaultForegroundColor().getRed();
        int green = getDefaultForegroundColor().getGreen();
        int blue = getDefaultForegroundColor().getBlue();
        int newRed = Math.abs(255 - red);
        int newGreen = Math.abs(255 - green);
        int newBlue = Math.abs(255 - blue);
        return new Color(newRed, newGreen, newBlue);
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

    public int getCurrentButtonPressed() {
        return currentButtonPressed;
    }

    public void setCurrentButtonPressed(int currentButtonPressed) {
        this.currentButtonPressed = currentButtonPressed;
    }

    public void setCommand(Command strategy) {
        this.command = strategy;

    }

    public void executeCommand() {
        this.command.execute();
    }

    public Command getCommand() {
        return command;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }


    public AsciiPanelMouseListener getAsciiPanelMouseListener() {
        return asciiPanelMouseListener;
    }

    public AsciiPanelMouseMotionListener getAsciiPanelMouseMotionListener() {
        return asciiPanelMouseMotionListener;
    }

    public CommandStack getRedoCommandStack() {
        return redoCommandStack;
    }
}
