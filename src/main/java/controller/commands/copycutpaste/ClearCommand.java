package controller.commands.copycutpaste;

import controller.ToolsPanelController;
import controller.commands.Command;
import view.MainPanel;

import java.awt.*;

/**
 * The command that clears the current selection
 */
public class ClearCommand implements Command {
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel = MainPanel.getInstance();
    /**
     * The char grid before the action
     */
    private char[][] oldCharGrid;
    /**
     * The color grid that stores the foreground color of the characters before the action
     */
    private Color[][] oldForegroundColorGrid;
    /**
     * The color grid that stores the background color of the characters before the action
     */
    private Color[][] oldBackgroundColorGrid;

    /**
     * Clears the current selection
     */
    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();
        oldForegroundColorGrid = ToolsPanelController.copyFCGrid();
        oldBackgroundColorGrid = ToolsPanelController.copyBCGrid();

        for (int[] point : mainPanel.getSelectedPoints()) {
            int x = point[0];
            int y = point[1];
            mainPanel.getBeforeSelectionGrid()[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
            mainPanel.getAsciiPanel().clear((char) 0, x, y, 1, 1);
        }
        mainPanel.getAsciiPanel().repaint();
        mainPanel.getCommandStack().push(this);

    }

    @Override
    public void undo() {
        System.out.println("undone clear");
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
