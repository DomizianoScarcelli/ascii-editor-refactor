package controller.commands;

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

    public FillCommand(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void execute() {
        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        oldCharGrid = new char[currentChars.length][currentChars[0].length];
        //Matrix copy
        for (int y = 0; y < currentChars.length; y++) {
            for (int x = 0; x < currentChars[0].length; x++) {
                oldCharGrid[y][x] = currentChars[y][x];
            }
        }
        cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        if (mainPanel.getCurrentButtonPressed() == 1) {
            mainPanel.getAsciiPanel().fill((char) mainPanel.getSelectedChar(), mainPanel.getAsciiPanel().getMouseCursorX(), mainPanel.getAsciiPanel().getMouseCursorY(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().fill((char) (0), cursorX, cursorY, Color.black, Color.black);
        }
        mainPanel.getAsciiPanel().repaint();

        mainPanel.getCommandStack().push(this);

        System.out.println(mainPanel.getCommandStack());

    }

    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
    }

}
