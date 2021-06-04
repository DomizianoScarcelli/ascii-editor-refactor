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
        this.setMinimumSize(new Dimension(200, 100));
        this.setLayout(new BorderLayout());

        //Creates the Menu Bar and puts it on the top of the window
        JMenuBar menuBar = new JMenuBar();
        JMenuItem file = new JMenuItem("File");
        menuBar.add(file);
        this.add(menuBar, BorderLayout.NORTH);

        //Creates the main container
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new GridBagLayout());
        this.add(mainContainer, BorderLayout.CENTER);

        //Creates the panel where the tools and the menu bar will be inserted
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.weightx = 1;
        mainConstraints.weighty = 0.1;
        mainConstraints.fill = GridBagConstraints.BOTH;
        mainConstraints.ipady = 40;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;

        //JPanel containing the buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bc = new GridBagConstraints();
        bc.fill = GridBagConstraints.BOTH;
        bc.weighty = 1;
        bc.weightx = 2;
        bc.insets = new Insets(0,30,0,30);
        JButton paint = createButton("Paint","src/resources/pencil.png", 50, 50, Color.white);
        JButton fill = createButton("Fill","src/resources/bucket.png", 50, 50, Color.white);
        JButton pick = createButton("Pick","src/resources/tap.png", 50, 50, Color.white);
        buttonPanel.add(paint, bc);
        buttonPanel.add(fill, bc);
        buttonPanel.add(pick, bc);

        //JPanel containing color selection and color display
        JPanel colorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cp = new GridBagConstraints();
        cp.fill = GridBagConstraints.BOTH;
        cp.weighty = 1;
        cp.weightx =1;
        JPanel foregroundColorPanel = new JPanel();
        JPanel backgroundColorPanel = new JPanel();
        foregroundColorPanel.setBackground(Color.black);
        backgroundColorPanel.setBackground(Color.white);
        colorPanel.add(foregroundColorPanel, cp);
        colorPanel.add(backgroundColorPanel, cp);

        //JPanel containing the character preview and character selector
        JPanel characterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints charP = new GridBagConstraints();



        //JPanel containing other JPanels
        JPanel toolsPanel = new JPanel();
        toolsPanel.setBackground(new Color(0, 100, 0));
        mainContainer.add(toolsPanel, mainConstraints);
        toolsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.weighty = 1;
        c.weightx = 1;
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.BOTH; //Vertical
        toolsPanel.add(buttonPanel,c);
        toolsPanel.add(colorPanel, c);


        //JPanel containing the AsciiPanel
        JPanel asciiPanel = new JPanel();
        mainConstraints.gridy = GridBagConstraints.RELATIVE;
        mainConstraints.weighty = 1;
        asciiPanel.setBackground(new Color(100,0,0));
        mainContainer.add(asciiPanel, mainConstraints);

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
        return button;
    }




    public static void main(String[] args) {
        MainPanel.getInstance().setVisible(true);
    }
}
