package view;

import controller.AsciiPanelMouseListener;
import controller.AsciiPanelMouseMotionListener;
import model.AsciiFont;
import model.AsciiPanel;

import javax.swing.*;
import java.awt.*;



public class MainPanel extends JFrame {
    private AsciiPanel asciiPanel;
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
        JButton paint = ButtonFactory.createToolButton("Paint","src/main/resources/pencil.png");
        JButton fill = ButtonFactory.createToolButton("Fill","src/main/resources/bucket.png");
        JButton pick = ButtonFactory.createToolButton("Pick","src/main/resources/tap.png");
        buttonPanel.add(paint);
        buttonPanel.add(fill);
        buttonPanel.add(pick);

        //JPanel containing color selection and color display
        JPanel colorPanel = new JPanel(new GridLayout(2,2));
        JPanel foregroundColorPanel = new JPanel();
        JPanel backgroundColorPanel = new JPanel();
        foregroundColorPanel.setBackground(Color.black);
        backgroundColorPanel.setBackground(Color.white);
        foregroundColorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        backgroundColorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        colorPanel.setPreferredSize(new Dimension(100,100));
        colorPanel.add(foregroundColorPanel);
        colorPanel.add(new JPanel()); //aggiungo due pannelli vuoti per dare l'effetto scacchiera
        colorPanel.add(new JPanel());
        colorPanel.add((backgroundColorPanel));

        //JPanel containing the character preview and character selector
        JPanel characterPanel = new JPanel(new BorderLayout());
        JPanel previewCharacter = new JPanel();
        previewCharacter.setPreferredSize(new Dimension(50,50));
        previewCharacter.setBackground(Color.BLACK);
        JLabel selectedCharacterLabel = new JLabel("Character");
        characterPanel.add(previewCharacter, BorderLayout.CENTER);
        characterPanel.add(selectedCharacterLabel, BorderLayout.SOUTH);

        //JPanel containing other JPanels that contains tools
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolsPanel.setMinimumSize(new Dimension(300,110));
        toolsPanel.setPreferredSize(new Dimension(300, 110));
        toolsPanel.add(buttonPanel);
        toolsPanel.add(characterPanel);
        toolsPanel.add(colorPanel);
        mainContainer.add(toolsPanel, BorderLayout.NORTH);

        //JPanel containing the AsciiPanel
        asciiPanel = new AsciiPanel(80, 60, AsciiFont.CP437_16x16);
        asciiPanel.write("Marion");
//        asciiPanel.setBackground(new Color(0,0,0));
        mainContainer.add(asciiPanel, BorderLayout.CENTER);


        //Add listeners
        asciiPanel.setCursorX(0);
        asciiPanel.setCursorY(0);
        asciiPanel.addMouseListener(new AsciiPanelMouseListener(this));
        asciiPanel.addMouseMotionListener(new AsciiPanelMouseMotionListener(this));
    }

    public AsciiPanel getAsciiPanel() {
        return asciiPanel;
    }

    public static void main(String[] args) {
        MainPanel.getInstance().setVisible(true);
    }
}
