package view;

import controller.*;
import controller.commands.*;
import controller.commands.colorfilters.BlackWhiteCommand;
import controller.commands.colorfilters.ColorCommand;
import controller.commands.colorfilters.InvertColorsCommand;
import controller.commands.copycutpaste.PasteCommand;
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

public class MainPanel extends JFrame {
    /**
     * The AsciiPanel where the characters are shown
     */
    private AsciiPanel asciiPanel;
    /**
     * The ascii code of the current selected char
     */
    private int selectedChar = 1;
    /**
     * The current selected foreground color
     */
    private Color defaultForegroundColor = Color.WHITE;
    /**
     * The current selected background color
     */
    private Color defaultBackgroundColor = Color.BLACK;
    /**
     * The AsciiPanel where the current selected character is shown
     */
    private AsciiPanel charPreviewPanel;
    /**
     * The panel where the selected foreground color is shown
     */
    private JPanel foregroundColorPanel;
    /**
     * The panel where the selected foreground color is shown
     */
    private JPanel backgroundColorPanel;
    /**
     * The tool buttons
     */
    private JButton paint, fill, pick, eraser;
    /**
     * A boolean that indicates if the mouse is being dragged through the ascii panel
     */
    private boolean isDrag = false;
    /**
     * The imported image
     */
    private BufferedImage importedBufferedImage;
    /**
     * The panel that contais all the other elements
     */
    private JPanel mainContainer;
    /**
     * The mouse button that is currently pressed
     */
    private int currentButtonPressed = 1;
    /**
     * The stack that remembers the recent 20 executed commands
     */
    private CommandStack commandStack = new CommandStack();
    /**
     * The stack that remembres the recend 20 undone commands
     */
    private CommandStack redoCommandStack = new CommandStack();
    /**
     * The current selection command that was uses to select the current selected points
     */
    private SelectCommand currentSelection;
    /**
     * The char grid before the selection was done
     */
    private char[][] beforeSelectionGrid;
    /**
     * The list of selected points
     */
    private ArrayList<int[]> selectedPoints;
    /**
     * The current selected command that reflects the current selected tool
     */
    private Command command;
    /**
     * The mouse listener used to write characters on mouse click
     */
    private AsciiPanelMouseListener asciiPanelMouseListener;
    /**
     * The mouse listener used to write characters on mouse drag
     */
    private AsciiPanelMouseMotionListener asciiPanelMouseMotionListener;
    /**
     * The list of tool buttons on the toolbar
     */
    private ArrayList<JButton> toolButtonList = new ArrayList<>();
    /**
     * The eraser tool size
     */
    private int eraserSize = 1;
    /**
     * The panel containing the eraser size slider and the label
     */
    private JPanel sliderPanel;
    /**
     * The unique instance of the class
     */
    private static MainPanel instance;

    /**
     * Gets the unique instance of the class if existing, creates it otherwise
     *
     * @return the unique instance of the class
     */
    public static MainPanel getInstance() {
        if (instance == null) instance = new MainPanel();
        return instance;
    }

    private MainPanel() {
        super("Pannello principale");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 800);
        this.setMinimumSize(new Dimension(700, 700));
        this.setLayout(new BorderLayout());
        asciiPanel = new AsciiPanel(80, 60, AsciiFont.CP437_16x16);
        int cursorX = asciiPanel.getMouseCursorX();
        int cursorY = asciiPanel.getMouseCursorY();
        this.command = new PaintCommand(this, cursorX, cursorY);
        //---------------------MenuBar items-------------------------
        //Creates the Menu Bar and puts it on the top of the window
        JMenuBar menuBar = new JMenuBar();
        JMenu menuBarFile = new JMenu("File");
        JMenu menuBarFilters = new JMenu("Filters");
        //Undo button and listener
        JButton undo = ButtonFactory.menuBarButton("src/main/resources/undo.png");
        menuBar.add(undo);
        undo.addActionListener(e -> {
            Command command = commandStack.pop();
            command.undo();
            redoCommandStack.push(command);
        });
        JButton redo = ButtonFactory.menuBarButton("src/main/resources/redo.png");
        menuBar.add(redo);
        redo.addActionListener(e -> {
            Command command = redoCommandStack.pop();
            command.execute();
            commandStack.push(command);
        });
        menuBar.add(menuBarFile);
        this.add(menuBar, BorderLayout.NORTH);
        //Add items inside the "File" menu bar
        JMenuItem menuBarFileNew = new JMenuItem("New...");
        JMenuItem menuBarFileLoad = new JMenuItem("Load...");
        JMenuItem menuBarFileSave = new JMenuItem("Save...");
        JMenuItem menuBarFileImport = new JMenuItem("Import...");
        menuBarFile.add(menuBarFileNew);
        menuBarFile.add(menuBarFileLoad);
        menuBarFile.add(menuBarFileSave);
        menuBarFile.add(menuBarFileImport);
        //MenuBar File action listeners
        menuBarFileNew.addActionListener(new MenuBarActionNew());
        menuBarFileLoad.addActionListener(new MenuBarActionLoad());
        menuBarFileSave.addActionListener(new MenuBarActionSave());
        menuBarFileImport.addActionListener(new MenuBarActionImport());
        //Add items inside the "Filters" menu bar
        menuBar.add(menuBarFilters);
        JMenuItem menuBarFilterColorForeGround = new JMenuItem("Color Foreground");
        JMenuItem menuBarFilterColorBackground = new JMenuItem("Color Background");
        JMenuItem menuBarFilterInvert = new JMenuItem("Invert");
        JMenuItem menuBarFilterBlackWhite = new JMenuItem("Black & White");
        menuBarFilters.add(menuBarFilterColorForeGround);
        menuBarFilters.add(menuBarFilterColorBackground);
        menuBarFilters.add(menuBarFilterInvert);
        menuBarFilters.add(menuBarFilterBlackWhite);
        //Menu bar Filters action listeners
        menuBarFilterColorForeGround.addActionListener(e -> {
            new ColorCommand(true).execute();
            MainPanel.getInstance().getCurrentSelection().undo();
        });
        menuBarFilterColorBackground.addActionListener(e -> {
            new ColorCommand(false).execute();
            MainPanel.getInstance().getCurrentSelection().undo();
        });
        menuBarFilterInvert.addActionListener(e -> {
            new InvertColorsCommand().execute();
            MainPanel.getInstance().getCurrentSelection().undo();
        });
        menuBarFilterBlackWhite.addActionListener(e -> {
            new BlackWhiteCommand().execute();
            MainPanel.getInstance().getCurrentSelection().undo();

        });
        //Creates the main container
        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        this.add(mainContainer, BorderLayout.CENTER);
        //JPanel containing the buttons
        JPanel buttonPanel = new JPanel();
        paint = ButtonFactory.createToolButton("Paint", "src/main/resources/pencil.png");
        fill = ButtonFactory.createToolButton("Fill", "src/main/resources/bucket.png");
        pick = ButtonFactory.createToolButton("Pick", "src/main/resources/tap.png");
        eraser = ButtonFactory.createToolButton("Erase", "src/main/resources/eraser.png");
        buttonPanel.add(paint);
        buttonPanel.add(fill);
        buttonPanel.add(pick);
        buttonPanel.add(eraser);
        paint.setBackground(Color.GRAY);
        //JPanel containing color selection and color display
        JPanel colorPanel = new JPanel(new GridLayout(2, 2));
        foregroundColorPanel = new JPanel();
        backgroundColorPanel = new JPanel();
        foregroundColorPanel.setBackground(Color.WHITE);
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
        JPanel otherTools = new JPanel();
        toolsPanel.add(otherTools);
        otherTools.setLayout(new GridLayout(2, 2));
        JButton squareButton = ButtonFactory.createSmallToolButton("src/main/resources/square.png");
        otherTools.add(squareButton);
        JButton circleButton = ButtonFactory.createSmallToolButton("src/main/resources/dry-clean.png");
        otherTools.add(circleButton);
        JButton rectButton = ButtonFactory.createSmallToolButton("src/main/resources/rectangle.png");
        otherTools.add(rectButton);
        JButton selectButton = ButtonFactory.createSmallToolButton("src/main/resources/selection.png");
        toolsPanel.add(selectButton);

        JButton cloneButton = ButtonFactory.createSmallToolButton("src/main/resources/stamp.png");
        toolsPanel.add(cloneButton);
        squareButton.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/square.png");
            ToolsPanelController.selectButton(squareButton);
            this.command = new SquareCommand(cursorX, cursorY, 1);
        });
        circleButton.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/circle.png");
            ToolsPanelController.selectButton(circleButton);
            this.command = new CircleCommand(1);
        });
        rectButton.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/rect.png");
            ToolsPanelController.selectButton(rectButton);
            this.command = new RectCommand(cursorX, cursorY, cursorX, cursorY);
        });
        selectButton.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/select.png");
            ToolsPanelController.selectButton(selectButton);
            this.command = new SelectCommand(cursorX, cursorY, cursorX, cursorY);
        });

        cloneButton.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/stamp.png");
            ToolsPanelController.selectButton(cloneButton);
            this.command = new PasteCommand(0, 0);
        });
        //-----------------Adds the tool buttons to the list-------------------
        toolButtonList.add(paint);
        toolButtonList.add(fill);
        toolButtonList.add(pick);
        toolButtonList.add(eraser);
        toolButtonList.add(squareButton);
        toolButtonList.add(circleButton);
        toolButtonList.add(rectButton);
        toolButtonList.add(selectButton);
        toolButtonList.add(cloneButton);
        mainContainer.add(toolsPanel, BorderLayout.NORTH);
        charPreviewPanel.write((char) selectedChar, 1, 1);
        charPreviewPanel.repaint();
        //JPanel containing the AsciiPanel
        mainContainer.add(asciiPanel, BorderLayout.CENTER);
        asciiPanel.write("Empty");
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
        changeCursor("src/main/resources/whiteIcons/pencil2.png");
        paint.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/pencil2.png");
            ToolsPanelController.selectButton(paint);
            this.command = new PaintCommand(this, cursorX, cursorY);
        });
        pick.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/tap.png");
            ToolsPanelController.selectButton(pick);
            this.command = new PickCommand(this);
        });
        fill.addActionListener(e -> {
            changeCursor("src/main/resources/whiteIcons/bucket.png");
            ToolsPanelController.selectButton(fill);
            this.command = new FillCommand(this, cursorX, cursorY);
        });
        JSlider eraserSizeSlider = new JSlider(1, 30, 1);
        JLabel eraserSizeSliderLabel = new JLabel("Erases Size: " + eraserSizeSlider.getValue());
        sliderPanel = new JPanel(new GridLayout(2, 1));
        toolsPanel.add(sliderPanel);
        sliderPanel.add(eraserSizeSliderLabel);
        sliderPanel.add(eraserSizeSlider);
        sliderPanel.setVisible(false);

        eraser.addActionListener((e -> {
            changeCursor("src/main/resources/whiteIcons/eraser.png");
            ToolsPanelController.selectButton(eraser);
            this.command = new EraseCommand(this, cursorX, cursorY);
            //Makes the slider visible
            sliderPanel.setVisible(true);
        }));

        eraserSizeSlider.addChangeListener(e -> {
            eraserSizeSliderLabel.setText("Erases Size: " + eraserSizeSlider.getValue());
            this.eraserSize = eraserSizeSlider.getValue();
        });

        this.setFocusable(true);
        this.addKeyListener(new MainPanelKeyListener());

    }

    public AsciiPanel getAsciiPanel() {
        return asciiPanel;
    }

    public void setAsciiPanel(AsciiPanel asciiPanel) {
        this.asciiPanel = asciiPanel;
    }

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

    public AsciiPanelMouseListener getAsciiPanelMouseListener() {
        return asciiPanelMouseListener;
    }

    public CommandStack getRedoCommandStack() {
        return redoCommandStack;
    }

    private void changeCursor(String imagePath) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(imagePath);
        Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "Cursor");
        asciiPanel.setCursor(c);
    }

    public boolean isDrag() {
        return isDrag;
    }

    public void setDrag(boolean drag) {
        isDrag = drag;
    }

    public SelectCommand getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(SelectCommand currentSelection) {
        this.currentSelection = currentSelection;
    }

    public char[][] getBeforeSelectionGrid() {
        return beforeSelectionGrid;
    }

    public ArrayList<int[]> getSelectedPoints() {
        return selectedPoints;
    }

    public void setSelectedPoints(ArrayList<int[]> selectedPoints) {
        this.selectedPoints = selectedPoints;
    }

    public ArrayList<JButton> getToolButtonList() {
        return toolButtonList;
    }

    public int getEraserSize() {
        return eraserSize;
    }

    public JPanel getSliderPanel() {
        return sliderPanel;
    }

}
