package view;

import controller.CharActionListener;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Models the GUI that allows to select a certain character.
 * The class implements the 'Singleton' design pattern.
 */
public class CharacterSelector extends JDialog {

    private static final long serialVersionUID = -3046096625552352801L;
    /**
     * The unique instance of the class.
     */
    private static CharacterSelector instance;
    /**
     * The {@link JLabel} component that describes the button to perform the action.
     */
    private JLabel thl;
    /**
     * The list of {@link JButton} that allows to select a character from a list of characters.
     */
    private List<JButton> palette;

    /**
     * Class constructor.
     * Create the object that allows the selection of the character to write on the panel.
     */
    private CharacterSelector() {
        this.setTitle("Select a character");
//        super("ASCII MainPanel - CharacterSelector");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setBounds(0, 0, 16 * 16, 16 * 16 + 32);
        this.setResizable(false);
        this.setLayout(null);
        MainPanel imageEditor = MainPanel.getInstance();
        BufferedImage[] glyphsArray = imageEditor.getAsciiPanel().getGlyphs();
        for (int i = 0; i < glyphsArray.length; i++) {
            int x = i % 16;
            int y = i / 16;
            JButton glyphButton = new JButton(new ImageIcon(glyphsArray[i]));
            this.add(glyphButton);
            glyphButton.setBounds(x * 16, y * 16, 16, 16);
            glyphButton.addMouseListener(new CharActionListener(i));
        }

    }

    /**
     * Gets the unique instance if existing, create it otherwise.
     * @return the unique instance.
     */
    public static CharacterSelector getInstance() {
        if (instance == null)
            instance = new CharacterSelector();
        return instance;
    }

    /**
     * Closes the GUI components that are used to select a certain character.
     */
    public void close() {
        instance.setVisible(false);
        instance.dispose();
        instance = null;

    }
}