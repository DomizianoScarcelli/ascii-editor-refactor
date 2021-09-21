package controller;

import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Models the mouse listener used to trigger an action when the user clicks on the color palette
 */
public class ColorPanelMouseListener implements MouseListener {
    /**
     * The panel where the color is shown
     */
    private JPanel colorPanel;
    /**
     * A boolean that indicates whether the panel references to the background color (True) or foreground color (False)
     */
    private boolean isBackground;

    public ColorPanelMouseListener(JPanel colorPanel, boolean isBackground) {
        this.colorPanel = colorPanel;
        this.isBackground = isBackground;
    }

    /**
     * When a user clicks on the color palette, a dialog is shown and the user can change the foreground or background color.
     *
     * @param e the MouseEvent
     */
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
