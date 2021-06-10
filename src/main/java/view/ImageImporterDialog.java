package view;



import controller.menubar.importerdialog.ImageImporterActionConvert;
import controller.menubar.importerdialog.ImageImporterActionImport;


import javax.swing.*;

/**
 * Models the tool to import an local image from the filesystem, that will be converted to a grid of ascii characters.
 * The class define the button that triggers the import GUI and all the components within the import GUI.
 * This class implements the "singleton" design pattern.
 */
public class ImageImporterDialog extends JDialog {
    private static ImageImporterDialog instance;
    private JButton importButton;
    private JButton convertButton;
    private JLabel thresholdLabel;
    private JTextField thresholdSetter;
    private JCheckBox convertColorOptions;

    /**
     * Private class constructor.
     */
    private ImageImporterDialog() {
        this.setTitle("Import an image");
//        super("ASCII ImageEditor - Import");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setBounds(0, 0, 320, 230);
        this.setResizable(false);
        this.setLayout(null);
        thresholdLabel = new JLabel("Threshold:");
        thresholdSetter = new JTextField("240");
        convertColorOptions = new JCheckBox("all colors", true);
        convertButton = new JButton("Convert");
        importButton = new JButton("Import ...");
        importButton.addActionListener(new ImageImporterActionImport());
        convertButton.addActionListener(new ImageImporterActionConvert(MainPanel.getInstance().getAsciiPanel(), thresholdSetter, convertColorOptions));
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
     * The unique {@link ImageImporter} instance. The default value is null.
     */ /**
     * Gets the unique instance if existing, creates it otherwise.
     *
     * @return the unique {@link ImageImporterDialog} instance.
     */
    public static ImageImporterDialog getInstance() {
        if (instance == null)
            instance = new ImageImporterDialog();
        return instance;
    }

    public static void setInstance(ImageImporterDialog instance) {
        ImageImporterDialog.instance = instance;
    }

    /**
     * Closes the Image importer GUI.
     */
    public void close() {
        System.out.println("Chiuso");
        instance.setVisible(false);
        instance.dispose();
        instance = null;

    }

    /**
     * The {@link JButton} component in the main GUI that triggers the action to import an image from the filesystem into the ascii panel.
     */
    public JButton getImportButton() {
        return importButton;
    }

    public void setImportButton(JButton importButton) {
        this.importButton = importButton;
    }

    /**
     * The {@link JButton} component inside the import GUI that triggers the conversion from image to ascii character grid.
     */
    public JButton getConvertButton() {
        return convertButton;
    }

    public void setConvertButton(JButton convertButton) {
        this.convertButton = convertButton;
    }

    /**
     * The {@link JLabel} component inside the import GUI that describes the threshold text field.
     */
    public JLabel getThresholdLabel() {
        return thresholdLabel;
    }

    public void setThresholdLabel(JLabel thresholdLabel) {
        this.thresholdLabel = thresholdLabel;
    }

    /**
     * The {@link JTextField} component inside the import GUI that allows to set the threshold value.
     */
    public JTextField getThresholdSetter() {
        return thresholdSetter;
    }

    public void setThresholdSetter(JTextField thresholdSetter) {
        this.thresholdSetter = thresholdSetter;
    }

    /**
     * {@link JCheckBox} component.
     * If checked, it convert the image into a 16-bit color ascii image.
     * If not checked, it converts it into a 4-bit color ascii image.
     */
    public JCheckBox getConvertColorOptions() {
        return convertColorOptions;
    }

    public void setConvertColorOptions(JCheckBox convertColorOptions) {
        this.convertColorOptions = convertColorOptions;
    }
}
