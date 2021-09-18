package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

import java.awt.*;
import java.util.Arrays;

/**
 * Writes the selected char on the panel when the left mouse button is pressed.
 * the character is written on the mouse current position.
 * If another mouse button is pressed, it deletes the character on the current mouse position.
 * <p>
 * button is the button pressed by the mouse encoded with an integer:
 * <ol>
 * <li>left button;</li>
 * <li>center button;</li>
 * <li>right button.</li>
 * </ol>
 */
public class PaintCommand extends Command {
    /**
     * The mouse cursor position
     */
    private int cursorX, cursorY;
    /**
     * The char on the mouse cursor position before the erase
     */
    private int oldChar;

    public PaintCommand(MainPanel mainPanel, int cursorX, int cursorY) {
        this.mainPanel = mainPanel;
        this.cursorY = cursorY;
        this.cursorX = cursorX;
    }

    /**
     * Paints the current selected character on the ascii panel in correspondence of the mouse position
     */
    @Override
    public void execute() {
        super.execute();
        mainPanel.getCommandStack().pop();
        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        oldChar = mainPanel.getAsciiPanel().pickChar(cursorX, cursorY);
        Color oldFC = mainPanel.getAsciiPanel().pickFC(cursorX, cursorY);
        Color oldBC = mainPanel.getAsciiPanel().pickFC(cursorX, cursorY);

        //Prevents a char is not written twice on the same position, so the undo doesn't fully work.
        if (!(oldChar == mainPanel.getSelectedChar()
                && oldFC == mainPanel.getDefaultForegroundColor()
                && oldBC == mainPanel.getDefaultBackgroundColor()
                && cursorX == mainPanel.getAsciiPanel().getMouseCursorX()
                && cursorY == mainPanel.getAsciiPanel().getMouseCursorY())) {
            mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
            mainPanel.getCommandStack().push(this);
        }

        mainPanel.getAsciiPanel().repaint();

        ToolsPanelController.selectButton(mainPanel.getPaint());

    }
}
