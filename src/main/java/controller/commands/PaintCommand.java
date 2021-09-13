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
public class PaintCommand implements Command {
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
    /**
     * The color grid that stores the foreground color of the characters before the action
     */
    private Color[][] oldForegroundColorGrid;
    /**
     * The color grid that stores the background color of the characters before the action
     */
    private Color[][] oldBackgroundColorGrid;

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
        oldCharGrid = ToolsPanelController.copyCharGrid();
        oldForegroundColorGrid = ToolsPanelController.copyFCGrid();
        oldBackgroundColorGrid = ToolsPanelController.copyBCGrid();
        oldForegroundColorGrid = (mainPanel.getAsciiPanel().getOldForegroundColors());

        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        oldChar = mainPanel.getAsciiPanel().pickChar(cursorX, cursorY);
        if (mainPanel.getCurrentButtonPressed() == 1) {
            //Prevents a char is not written twice on the same position, so the undo doesn't fully work.
            if (!(oldChar == mainPanel.getSelectedChar()
                    && cursorX == mainPanel.getAsciiPanel().getMouseCursorX()
                    && cursorY == mainPanel.getAsciiPanel().getMouseCursorY())) {
                mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
                mainPanel.getCommandStack().push(this);
            }
        } else {
            mainPanel.getAsciiPanel().write((char) 0);
        }
        mainPanel.getAsciiPanel().repaint();

        ToolsPanelController.selectButton(mainPanel.getPaint());

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
