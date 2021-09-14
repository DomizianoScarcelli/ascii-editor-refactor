package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

import java.awt.*;

/**
 * A command is an object that does an action on the ascii panel and can undo the action
 */
public abstract class Command {
    /**
     * The MainPanel instance
     */
    protected MainPanel mainPanel;
    /**
     * The char grid before the action
     */
    protected char[][] oldCharGrid;
    /**
     * The color grid that stores the foreground color of the characters before the action
     */
    protected Color[][] oldForegroundColorGrid;
    /**
     * The color grid that stores the background color of the characters before the action
     */
    protected Color[][] oldBackgroundColorGrid;

    /**
     * Copies the old grid and color and adds the command to the main panel command stack.
     */
    public void execute() {
        mainPanel = MainPanel.getInstance();
        oldCharGrid = ToolsPanelController.copyCharGrid();
        oldForegroundColorGrid = ToolsPanelController.copyFCGrid();
        oldBackgroundColorGrid = ToolsPanelController.copyBCGrid();
        mainPanel.getCommandStack().push(this);
    }

    /**
     * Undoes the action executed
     */
    public void undo() {
        int width = mainPanel.getAsciiPanel().getWidthInCharacters();
        int height = mainPanel.getAsciiPanel().getHeightInCharacters();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mainPanel.getAsciiPanel().setCursorX(x);
                mainPanel.getAsciiPanel().setCursorY(y);
                mainPanel.getAsciiPanel().write(oldCharGrid[x][y], oldForegroundColorGrid[x][y], oldBackgroundColorGrid[x][y]);
            }
        }
        mainPanel.getAsciiPanel().repaint();
    }

}
