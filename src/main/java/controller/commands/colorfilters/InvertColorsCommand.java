package controller.commands.colorfilters;

import controller.commands.Command;

import java.awt.*;

/**
 * Inverts the character's colors in the current selection.
 * If no character is selected, then it inverts all the characters' colors in the ascii panel.
 */
public class InvertColorsCommand extends Command {
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
            int rF = mainPanel.getAsciiPanel().pickFC(x, y).getRed();
            int gF = mainPanel.getAsciiPanel().pickFC(x, y).getGreen();
            int bF = mainPanel.getAsciiPanel().pickFC(x, y).getBlue();
            int rB = mainPanel.getAsciiPanel().pickBC(x, y).getRed();
            int gB = mainPanel.getAsciiPanel().pickBC(x, y).getGreen();
            int bB = mainPanel.getAsciiPanel().pickBC(x, y).getBlue();
            mainPanel.getAsciiPanel().write((char) (int) mainPanel.getAsciiPanel().pickChar(x, y), new Color(255 - rF, 255 - gF, 255 - bF), new Color(255 - rB, 255 - gB, 255 - bB));
        } catch (NullPointerException ignored) {
        }
    }
}
