package controller;

import com.sun.tools.javac.Main;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ForegroundColorPanelMouseListener implements MouseListener {
    private MainPanel mainPanel;

    public ForegroundColorPanelMouseListener(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }


    
    @Override
    public void mouseClicked(MouseEvent e) {
        Color newColor = JColorChooser.showDialog(mainPanel.getForegroundColorPanel(), "Choose Foreground Color", mainPanel.getForegroundColorPanel().getBackground());

        if (newColor != null) {
            mainPanel.setDefaultForegroundColor(newColor);
            mainPanel.getForegroundColorPanel().setBackground(newColor);
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
