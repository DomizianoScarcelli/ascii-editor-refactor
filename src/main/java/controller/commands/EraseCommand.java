package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

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
     * The char grid before the action
     */
    private char[][] oldCharGrid;

    public EraseCommand(MainPanel mainPanel, int cursorX, int cursorY) {
        this.mainPanel = mainPanel;

        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }

    /**
     * Erases the character in correspondence of the mouse position
     */
    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();
        try {
            mainPanel.getAsciiPanel().clear((char) 0, cursorX, cursorY, mainPanel.getEraserSize(), mainPanel.getEraserSize());
        } catch (IllegalArgumentException ignored) {
        }

    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
