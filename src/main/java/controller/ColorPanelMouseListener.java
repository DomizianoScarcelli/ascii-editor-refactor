package controller;

import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorPanelMouseListener implements MouseListener {
    private JPanel colorPanel;
    private boolean isBackground;

    public ColorPanelMouseListener(JPanel colorPanel, boolean isBackground){
        this.colorPanel = colorPanel;
        this.isBackground = isBackground;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Color newColor = JColorChooser.showDialog(colorPanel, "Choose Foreground Color", colorPanel.getBackground());

        if (newColor != null) {
            if (isBackground) MainPanel.getInstance().setDefaultBackgroundColor(newColor);
            else MainPanel.getInstance().setDefaultForegroundColor(newColor);
            colorPanel.setBackground(newColor);
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
