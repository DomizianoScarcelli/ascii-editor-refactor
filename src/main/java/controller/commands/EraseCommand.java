package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

/**
 * The command that erases the character in correspondence of the mouse position
 */
public class EraseCommand extends Command {
    /**
     * The mouse cursor position
     */
    private int cursorX, cursorY;

    public EraseCommand(int cursorX, int cursorY) {
        this.cursorX = cursorX;
        this.cursorY = cursorY;
    }

    /**
     * Erases the character in correspondence of the mouse position
     */
    @Override
    public void execute() {
        super.execute();
        try {
            mainPanel.getAsciiPanel().clear((char) 0, cursorX, cursorY, mainPanel.getEraserSize(), mainPanel.getEraserSize());
        } catch (IllegalArgumentException ignored) {
        }

    }

}
