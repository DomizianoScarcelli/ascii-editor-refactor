package view;

import controller.menubar.ImageNewActionNew;

import javax.swing.*;

/**
 * Models the tool to create a new image on the palette.
 * The class define the button that triggers the "new" GUI and all the components within the "new" GUI.
 * This class implements the "singleton" design pattern.
 */
public class ImageNewDialog extends JDialog {
    private static final long serialVersionUID = -1844498949903148619L;
    private static ImageNewDialog instance;

    /**
     * Private class constructor.
     */
    private ImageNewDialog() {
        this.setTitle("New Image");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setBounds(0, 0, 320, 230);
        this.setResizable(false);
        this.setLayout(null);

        JLabel widthLabel = new JLabel("Width:");
        JTextField widthSetter = new JTextField("80");
        JLabel heightLabel = new JLabel("Height:");
        JTextField heightSetter = new JTextField("60");
        JButton proceedButton = new JButton("Create New");
        proceedButton.addActionListener(new ImageNewActionNew());
        this.add(widthLabel);
        this.add(widthSetter);
        this.add(heightLabel);
        this.add(heightSetter);
        this.add(proceedButton);
        widthLabel.setBounds(0, 0, 320, 40);
        widthSetter.setBounds(0, 40, 320, 40);
        heightLabel.setBounds(0, 80, 320, 40);
        heightSetter.setBounds(0, 120, 320, 40);
        proceedButton.setBounds(0, 160, 320, 40);
    }

    /**
     * Returns the unique instance if existing, it creates it otherwise.
     *
     * @return the unique {@link MainPanel} instance.
     */
    public static ImageNewDialog getInstance() {
        if (instance == null)
            instance = new ImageNewDialog();
        return instance;
    }

}

