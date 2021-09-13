package controller.commands.shapes;

import controller.ToolsPanelController;
import controller.commands.Command;
import view.MainPanel;

/**
 * The command that draws a square
 */
public class SquareCommand implements Command {
    /**
     * The mouse cursor position
     */
    private int cursorX, cursorY;
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel = MainPanel.getInstance();
    /**
     * The square side dimension
     */
    private int sideDimension;
    /**
     * The char grid before the action
     */
    private char[][] oldCharGrid;

    public SquareCommand(int sideDimension) {
        this.sideDimension = sideDimension;
    }

    /**
     * Draws a square on the ascii panel in correspondence of the mouse position
     */
    @Override
    public void execute() {

        oldCharGrid = ToolsPanelController.copyCharGrid();

        cursorX = mainPanel.getAsciiPanel().getCursorX();
        cursorY = mainPanel.getAsciiPanel().getCursorY();

        for (int y = cursorY; y < cursorY + sideDimension; y++) {
            for (int x = cursorX; x < cursorX + sideDimension; x++) {
                if (x == cursorX || y == cursorY || x == cursorX + sideDimension - 1 || y == cursorY + sideDimension - 1) {
                    mainPanel.getAsciiPanel().setCursorX(x);
                    mainPanel.getAsciiPanel().setCursorY(y);
                    mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
                }
            }
        }
        mainPanel.getCommandStack().push(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setCursorX(mainPanel.getAsciiPanelMouseListener().getInitialCursorX());
        mainPanel.getAsciiPanel().setCursorY(mainPanel.getAsciiPanelMouseListener().getInitialCursorY());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
    }

}
