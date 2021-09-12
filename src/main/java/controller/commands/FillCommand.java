package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

import java.awt.*;

/**
 * The action performed after the "Fill" button is clicked by the mouse.
 * It fills the closed area in the current mouse position with the selected character.
 * <p>
 * button is the button pressed by the mouse encoded with an integer:
 * <ol>
 * <li>left button;</li>
 * <li>center button;</li>
 * <li>right button.</li>
 * </ol>
 */
public class FillCommand implements Command {

    private MainPanel mainPanel;

    private int cursorX, cursorY;

    private char[][] oldCharGrid;
    private Color[][] oldForegroundColorGrid;
    private Color[][] oldBackgroundColorGrid;

    public FillCommand(MainPanel mainPanel, int cursorX, int cursorY) {
        this.mainPanel = mainPanel;
        this.cursorY = cursorY;
        this.cursorX = cursorX;
    }

    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();
        oldForegroundColorGrid = ToolsPanelController.copyFCGrid();
        oldBackgroundColorGrid = ToolsPanelController.copyBCGrid();
        

        if (mainPanel.getCurrentButtonPressed() == 1) {
            mainPanel.getAsciiPanel().fill((char) mainPanel.getSelectedChar(), cursorX, cursorY, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().fill((char) (0), cursorX, cursorY, Color.black, Color.black);
        }
        mainPanel.getAsciiPanel().repaint();

        mainPanel.getCommandStack().push(this);

        System.out.println(mainPanel.getCommandStack());

    }

    @Override
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
