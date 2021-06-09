package controller;


import view.MainPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Models the actions performed on the panel by the mouse when it's not moving.
 */
public class AsciiPanelMouseListener implements MouseListener {
    /**
     * The {@link MainPanel} object that allows to edit the image on the ascii panel when an action is performed.
     */
    private MainPanel mainPanel;

    /**
     * Class constructor from an {@link MainPanel} object.
     *
     * @param mainPanel the {@link MainPanel} object.
     */
    public AsciiPanelMouseListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     * Triggers the specific action of the currently selected tool.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (mainPanel.getCurrentToolId()) {
            case 0: //current tool is paint
                this.onPaint(e.getButton());
                break;
            case 1: //current tool is pick
                this.onPick(e.getButton());
                break;
            case 2: //current tool is fill
                this.onFill(e.getButton());
                break;
            default:
                break;
        }

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

    /**
     * Writes the selected char on the panel when the left mouse button is pressed.
     * the character is written on the mouse current position.
     * If another mouse button is pressed, it deletes the character on the current mouse position.
     *
     * @param button the button pressed by the mouse encoded with an integer:
     *               <ol>
     *               <li>left button;</li>
     *               <li>center button;</li>
     *               <li>right button.</li>
     *               </ol>
     */
    public void onPaint(int button) {
        int cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        int cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        if (button == 1) {
            mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().write((char) 0);
        }
        mainPanel.getAsciiPanel().repaint();
    }

    public void onPick(int button) {
        int cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        int cursorY = mainPanel.getAsciiPanel().getMouseCursorY();
        mainPanel.setSelectedChar(mainPanel.getAsciiPanel().pickChar(cursorX, cursorY));
        mainPanel.setDefaultForegroundColor(mainPanel.getAsciiPanel().pickFC(cursorX, cursorY));
        mainPanel.setDefaultBackgroundColor(mainPanel.getAsciiPanel().pickBC(cursorX, cursorY));
        mainPanel.getBackgroundColorPanel().setBackground(mainPanel.getDefaultBackgroundColor());
        mainPanel.getForegroundColorPanel().setBackground(mainPanel.getDefaultForegroundColor());
        mainPanel.getForegroundColorPanel().repaint();
        mainPanel.getBackgroundColorPanel().repaint();
//        selectCharButton.setText(Integer.toString(selectedChar));
        ToolsPanelController toolsPanelController = new ToolsPanelController();
        mainPanel.setCurrentToolId(0);
        toolsPanelController.updatePreview();
    }

    public void onFill(int button) {
        int cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        int cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        /*
         * myPanel.setCursorX(cursorX); myPanel.setCursorY(cursorY);
         */

        if (button == 1) {

            mainPanel.getAsciiPanel().fill((char) mainPanel.getSelectedChar(), cursorX, cursorY, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().fill((char) (0), cursorX, cursorY, Color.black, Color.black);
        }
        mainPanel.getAsciiPanel().repaint();

    }

}
