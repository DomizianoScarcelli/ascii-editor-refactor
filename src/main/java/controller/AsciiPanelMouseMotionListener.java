package controller;



import view.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Models the actions performed on the panel by the mouse when moving.
 */
public class AsciiPanelMouseMotionListener implements MouseMotionListener {
    /**
     * The {@link MainPanel} object that allows to edit the image on the ascii panel when an action is performed.
     */
    private MainPanel mainPanel;

    /**
     * Class constructor from an {@link MainPanel} object.
     *
     * @param mainPanel the {@link MainPanel} object.
     */
    public AsciiPanelMouseMotionListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     * Triggers the method that updates the mouse coordinates when it moves.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        mainPanel.getAsciiPanel().setMouseCursorX(e.getX() / 16);
        mainPanel.getAsciiPanel().setMouseCursorY(e.getY() / 16);
        mainPanel.getAsciiPanel().repaint();
    }

}
