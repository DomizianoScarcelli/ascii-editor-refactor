package view;

import javax.swing.*;
import java.awt.*;

public class ButtonFactory {

    /**
     * Creates a styled JButton
     * @param buttonText
     * @param iconFileName
     * @return
     */
    public static JButton createToolButton(String buttonText, String iconFileName){
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(100,100));
        addIconToButton(button, iconFileName, 50, 50);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        return button;
    }

    public static JButton createCharacterSelectorButton(String iconFileName){
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(20,20));
        button.setBackground(Color.WHITE);
        addIconToButton(button, iconFileName, 20, 20);
        button.setFocusPainted(false);
        return button;
    }

    public static void addIconToButton(JButton button, String iconFileName, int sizex, int sizey){
        ImageIcon imageIcon = new ImageIcon(iconFileName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(sizex, sizey,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);  // transform it back
        button.setIcon(imageIcon);
    }
}
