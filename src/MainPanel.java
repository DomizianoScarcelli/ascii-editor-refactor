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
        buttonPanel.setSize(300,300);
        GridBagConstraints bc = new GridBagConstraints();
        bc.fill = GridBagConstraints.BOTH;
        bc.weighty = 1;
        bc.weightx = 2;
        bc.insets = new Insets(0,30,0,30);
        JButton paint = new JButton("Paint");
        JButton fill = new JButton("Fill");
        JButton pick = new JButton("Pick");

        paint.setBackground(new Color(255, 255, 255));
        paint.setBorderPainted(false);
        fill.setBackground(new Color(255, 255, 255));
        fill.setBorderPainted(false);
        pick.setBackground(new Color(255, 255, 255));
        pick.setBorderPainted(false);
        buttonPanel.add(paint, bc);
        buttonPanel.add(fill, bc);
        buttonPanel.add(pick, bc);

        //Insert icon inside buttons
        ImageIcon pickIcon = getImageIcon("src/resources/tap.png", 50, 50);
        ImageIcon fillIcon = getImageIcon("src/resources/bucket.png", 50, 50);
        ImageIcon paintIcon = getImageIcon("src/resources/pencil.png", 50, 50);
        setButtonIcon(pick, pickIcon);
        setButtonIcon(fill, fillIcon);
        setButtonIcon(paint, paintIcon);



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


        //JPanel containing the AsciiPanel
        JPanel asciiPanel = new JPanel();
        mainConstraints.gridy = GridBagConstraints.RELATIVE;
        mainConstraints.weighty = 1;
        asciiPanel.setBackground(new Color(100,0,0));
        mainContainer.add(asciiPanel, mainConstraints);

    }

    /**
     * Import and scales an image from the filesystem
     * @param filename
     * @param width
     * @param height
     * @return
     */
    public ImageIcon getImageIcon(String filename, int width, int height){
        ImageIcon imageIcon = new ImageIcon(filename);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);  // transform it back
        return imageIcon;
    }

    /**
     * Sets the icon to the button and puts the text on the center bottom
     * @param button
     * @param icon
     */
    public void setButtonIcon(JButton button, ImageIcon icon){
        button.setIcon(icon);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
    }




    public static void main(String[] args) {
        MainPanel.getInstance().setVisible(true);
    }
}
