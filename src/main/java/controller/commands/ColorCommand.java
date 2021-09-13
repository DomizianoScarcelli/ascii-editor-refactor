package controller.commands;

import model.AsciiPanel;
import view.MainPanel;

import java.awt.*;

/**
 * The command that changes the foreground or background color of the characters inside the current selection
 */
public class ColorCommand implements Command {
    /**
     * A boolean that indicates whether the foreground or background color of the character has to be
     * changes.
     * If true the command changes the foreground color, if false it changes the background color.
     */
    private boolean colorForeground;
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel = MainPanel.getInstance();

    public ColorCommand(boolean colorForeground) {
        this.colorForeground = colorForeground;
    }

    /**
     * Change the foreground or background color of the characters inside the current selection
     */
    @Override
    public void execute() {
        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        for (int[] point : mainPanel.getSelectedPoints()) {
            int x = point[0];
            int y = point[1];
            char currentChar = currentChars[x][y];
            Color currentFC = mainPanel.getAsciiPanel().pickFC(x, y);
            Color currentBC = mainPanel.getAsciiPanel().pickBC(x, y);
            Color selectedFC = mainPanel.getDefaultForegroundColor();
            Color selectedBC = mainPanel.getDefaultBackgroundColor();
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            if (colorForeground)
                mainPanel.getAsciiPanel().write(currentChar, selectedFC, currentBC);
            else mainPanel.getAsciiPanel().write(currentChar, currentFC, selectedBC);
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
