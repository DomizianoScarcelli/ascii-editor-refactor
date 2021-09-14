package controller.commands.colorfilters;

import controller.commands.Command;

import java.awt.*;

/**
 * The command that changes the foreground or background color of the characters inside the current selection
 */
public class ColorCommand extends Command {
    /**
     * A boolean that indicates whether the foreground or background color of the character has to be
     * changes.
     * If true the command changes the foreground color, if false it changes the background color.
     */
    private boolean colorForeground;

    public ColorCommand(boolean colorForeground) {
        this.colorForeground = colorForeground;
    }

    /**
     * Change the foreground or background color of the characters inside the current selection
     */
    @Override
    public void execute() {
        super.execute();
        if (mainPanel.getSelectedPoints() == null || mainPanel.getSelectedPoints().isEmpty()) {
            int width = mainPanel.getAsciiPanel().getWidthInCharacters();
            int height = mainPanel.getAsciiPanel().getHeightInCharacters();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    applyFilter(x, y);
                }
            }
        } else {
            for (int[] point : mainPanel.getSelectedPoints()) {
                applyFilter(point[0], point[1]);
            }
        }
        mainPanel.getAsciiPanel().repaint();
    }

    private void applyFilter(int x, int y) {
        try {
            char currentChar = oldCharGrid[x][y];
            Color currentFC = mainPanel.getAsciiPanel().pickFC(x, y);
            Color currentBC = mainPanel.getAsciiPanel().pickBC(x, y);
            Color selectedFC = mainPanel.getDefaultForegroundColor();
            Color selectedBC = mainPanel.getDefaultBackgroundColor();
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            if (colorForeground)
                mainPanel.getAsciiPanel().write(currentChar, selectedFC, currentBC);
            else mainPanel.getAsciiPanel().write(currentChar, currentFC, selectedBC);
        } catch (NullPointerException ignored) {
        }
    }

}

