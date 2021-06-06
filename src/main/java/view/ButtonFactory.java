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
        button.setMinimumSize(new Dimension(100,100));
        button.setPreferredSize(new Dimension(100,100));
        ImageIcon imageIcon = new ImageIcon(iconFileName);
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);  // transform it back
        button.setIcon(imageIcon);
        button.setFocusPainted(false);
        button.setHorizontalTextPosition(AbstractButton.CENTER);
        button.setVerticalTextPosition(AbstractButton.BOTTOM);
        button.setBackground(Color.WHITE);
        button.setBorderPainted(false);
        button.setMinimumSize(new Dimension(100,100));
        button.setPreferredSize(new Dimension(100,100));
        return button;
    }
}
