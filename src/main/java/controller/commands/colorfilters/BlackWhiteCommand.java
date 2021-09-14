package controller.commands.colorfilters;

import controller.commands.Command;

import java.awt.*;

/**
 * Change the character's colors in the current selection to black and white.
 * If no character is selected, then it changes all the characters in the ascii panel.
 */
public class BlackWhiteCommand extends Command {
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
        mainPanel.getAsciiPanel().setCursorX(x);
        mainPanel.getAsciiPanel().setCursorY(y);
        try {
            int rF = oldForegroundColorGrid[x][y].getRed();
            int gF = oldForegroundColorGrid[x][y].getGreen();
            int bF = oldForegroundColorGrid[x][y].getBlue();
            int rB = oldBackgroundColorGrid[x][y].getRed();
            int gB = oldBackgroundColorGrid[x][y].getGreen();
            int bB = oldBackgroundColorGrid[x][y].getBlue();
            int greyF = Math.round((rF + gF + bF) / 3);
            int greyB = Math.round((rB + gB + bB) / 3);
            mainPanel.getAsciiPanel().write(oldCharGrid[x][y], new Color(greyF, greyF, greyF), new Color(greyB, greyB, greyB));
        } catch (NullPointerException ignored) {
        }
    }
}
