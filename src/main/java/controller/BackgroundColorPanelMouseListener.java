package controller;

import com.sun.tools.javac.Main;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BackgroundColorPanelMouseListener implements MouseListener {
    private MainPanel mainPanel;

    public BackgroundColorPanelMouseListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Color newColor = JColorChooser.showDialog(mainPanel.getBackgroundColorPanel(), "Choose Foreground Color", mainPanel.getBackgroundColorPanel().getBackground());

        if (newColor != null) {
            mainPanel.setDefaultBackgroundColor(newColor);
            mainPanel.getBackgroundColorPanel().setBackground(newColor);
        }
        ToolsPanelController.getInstance().updatePreview();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
