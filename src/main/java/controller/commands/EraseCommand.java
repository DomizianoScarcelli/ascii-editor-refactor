package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

import java.awt.*;

/**
 * The command that erases the character in correspondence of the mouse position
 */
public class EraseCommand implements Command {
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel;
    /**
     * The mouse cursor position
     */
    private int cursorX, cursorY;
    /**
     * The char on the mouse cursor position before the erase
     */
    private int oldChar;
    /**
     * The char grid before the action
     */
    private char[][] oldCharGrid;

    public EraseCommand(MainPanel mainPanel, int cursorX, int cursorY) {
        this.mainPanel = mainPanel;
        this.cursorY = cursorY;
        this.cursorX = cursorX;
    }

    /**
     * Erases the character in correspondence of the mouse position
     */
    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();
        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        oldChar = mainPanel.getAsciiPanel().pickChar(cursorX, cursorY);
        //Prevents a char is not written twice on the same position, so the undo doesn't fully work.
        if (!(oldChar == (char) 0 && cursorX == mainPanel.getAsciiPanel().getMouseCursorX() && cursorY == mainPanel.getAsciiPanel().getMouseCursorY())) {
            mainPanel.getAsciiPanel().write((char) 0, Color.BLACK, Color.BLACK);
            mainPanel.getCommandStack().push(this);
        }
        mainPanel.getAsciiPanel().repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        //TODO implementa undo
    }
}
