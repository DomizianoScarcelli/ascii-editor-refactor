package view;

import controller.menubar.importerdialog.ImageImporterConvertActionListener;
import controller.menubar.importerdialog.ImageImporterImportActionListener;

import javax.swing.*;

/**
 * Models the tool to import an local image from the filesystem, that will be converted to a grid of ascii characters.
 * The class define the button that triggers the import GUI and all the components within the import GUI.
 * This class implements the "singleton" design pattern.
 */
public class ImageImporterDialog extends JDialog {
    /**
     * The unique instance of the class
     */
    private static ImageImporterDialog instance;

    /**
     * Private class constructor.
     */
    private ImageImporterDialog() {
        this.setTitle("Import an image");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setBounds(0, 0, 320, 230);
        this.setResizable(false);
        this.setLayout(null);
        JLabel thresholdLabel = new JLabel("Threshold:");
        JTextField thresholdSetter = new JTextField("240");
        JCheckBox convertColorOptions = new JCheckBox("all colors", true);
        JButton convertButton = new JButton("Convert");
        JButton importButton = new JButton("Import ...");
        importButton.addActionListener(new ImageImporterImportActionListener());
        convertButton.addActionListener(new ImageImporterConvertActionListener(MainPanel.getInstance().getAsciiPanel(), thresholdSetter, convertColorOptions));
        this.add(importButton);
        this.add(thresholdLabel);
        this.add(thresholdSetter);
        this.add(convertColorOptions);
        this.add(convertButton);
        importButton.setBounds(0, 0, 320, 40);
        thresholdLabel.setBounds(0, 40, 320, 40);
        thresholdSetter.setBounds(0, 80, 320, 40);
        convertColorOptions.setBounds(0, 120, 320, 40);
        convertButton.setBounds(0, 160, 320, 40);
    }

    /**
     * The unique {@link ImageImporterDialog} instance. The default value is null.
     *
     * @return the unique {@link ImageImporterDialog} instance.
     */
    public static ImageImporterDialog getInstance() {
        if (instance == null)
            instance = new ImageImporterDialog();
        return instance;
    }

    /**
     * Closes the Image importer GUI.
     */
    public void close() {
        instance.setVisible(false);
        instance.dispose();
        instance = null;
    }
}
