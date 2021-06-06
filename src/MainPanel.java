import javax.swing.*;
import java.awt.*;


public class MainPanel extends JFrame {
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

        JButton paint = createButton("Paint","src/resources/pencil.png", 50, 50, Color.white);
        JButton fill = createButton("Fill","src/resources/bucket.png", 50, 50, Color.white);
        JButton pick = createButton("Pick","src/resources/tap.png", 50, 50, Color.white);
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
        mainContainer.add(toolsPanel, BorderLayout.NORTH);
//        toolsPanel.setBackground(Color.WHITE);
        toolsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        toolsPanel.setMinimumSize(new Dimension(300,110));
        toolsPanel.setPreferredSize(new Dimension(300, 110));
        toolsPanel.add(buttonPanel);
        toolsPanel.add(characterPanel);
        toolsPanel.add(colorPanel);




        //JPanel containing the AsciiPanel
        JPanel asciiPanel = new JPanel();
        asciiPanel.setBackground(new Color(0,0,0));
        mainContainer.add(asciiPanel, BorderLayout.CENTER);

    }

    /**
     * Creates a styled JButton
     * @param buttonText
     * @param iconFileName
     * @param iconWidth
     * @param iconHeight
     * @param backgroundColor
     * @return
     */
    public JButton createButton(String buttonText, String iconFileName , int iconWidth, int iconHeight, Color backgroundColor){
        JButton button = new JButton(buttonText);
        button.setMinimumSize(new Dimension(100,100));
        button.setPreferredSize(new Dimension(100,100));
        ImageIcon imageIcon = new ImageIcon(iconFileName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(iconWidth, iconHeight,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);  // transform it back
        button.setIcon(imageIcon);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setBackground(new Color(255, 255, 255));
        button.setBorderPainted(false);
        button.setMinimumSize(new Dimension(100,100));
        button.setPreferredSize(new Dimension(100,100));
        return button;
    }


    public static void main(String[] args) {
        MainPanel.getInstance().setVisible(true);
    }
}
