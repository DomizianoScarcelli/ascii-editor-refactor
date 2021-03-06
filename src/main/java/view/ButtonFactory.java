package view;

import javax.swing.*;
import java.awt.*;

/**
 * Models the Factory that contains functions to create the buttons that are used in the GUI
 */
public class ButtonFactory {
    /**
     * Creates the button that is used for the main tools
     *
     * @param buttonText   the text shown under the label
     * @param iconFileName the label image path
     * @return the JButton
     */
    public static JButton createToolButton(String buttonText, String iconFileName) {
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(100, 100));
        addIconToButton(button, iconFileName, 50, 50);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Creates the button that is used for the secondary tools
     *
     * @param iconFileName the label image path
     * @return the JButton
     */
    public static JButton createSmallToolButton(String iconFileName) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        addIconToButton(button, iconFileName, 25, 25);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        return button;
    }

    /**
     * Creates the button that is used for the character previous and next selector
     *
     * @param iconFileName the label image path
     * @return the JButton
     */
    public static JButton createCharacterSelectorButton(String iconFileName) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(20, 20));
        button.setBackground(Color.WHITE);
        addIconToButton(button, iconFileName, 20, 20);
        button.setFocusPainted(false);

        return button;
    }

    /**
     * Creates the button that is used for the tools in the menu bar.
     *
     * @param iconFileName the label image path
     * @return
     */
    public static JButton menuBarButton(String iconFileName) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(15, 15));
        button.setOpaque(false);
        button.setContentAreaFilled(false);

        button.setBorder(BorderFactory.createLineBorder(Color.white, 8));
        button.setBorderPainted(false);
        addIconToButton(button, iconFileName, 15, 15);
        return button;
    }

    /**
     * Adds an image icon to a button
     *
     * @param button       the button on which the image has to be addes
     * @param iconFileName the icon file path
     * @param sizex        the icon height
     * @param sizey        the icon width
     */
    public static void addIconToButton(JButton button, String iconFileName, int sizex, int sizey) {
        ImageIcon imageIcon = new ImageIcon(iconFileName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(sizex, sizey, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);  // transform it back
        button.setIcon(imageIcon);
    }
}
