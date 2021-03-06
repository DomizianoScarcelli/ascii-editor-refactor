package view;

import controller.CharacterSelectorActionListener;

import javax.swing.*;
import java.awt.image.BufferedImage;

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
            glyphButton.addMouseListener(new CharacterSelectorActionListener(i));
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

}