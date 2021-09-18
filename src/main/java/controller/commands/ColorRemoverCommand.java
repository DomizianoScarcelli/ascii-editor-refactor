package controller.commands;

/**
 * Removes all the chars that have the selected foreground color or background color inside the selection.
 * If the selection is empty, the effect is applied to the whole panel.
 */
public class ColorRemoverCommand extends Command {
    /**
     * Indicates wheter the char has to be removed based on his foreground color (if true) or background color (if false).
     */
    private boolean isForeground;

    public ColorRemoverCommand(boolean isForeground) {
        this.isForeground = isForeground;
    }

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
        if (isForeground) {
            if (mainPanel.getAsciiPanel().pickFC(x, y) == mainPanel.getDefaultForegroundColor())
                mainPanel.getAsciiPanel().clear((char) 0, x, y, 1, 1);
        } else {
            if (mainPanel.getAsciiPanel().pickBC(x, y) == mainPanel.getDefaultBackgroundColor())
                mainPanel.getAsciiPanel().clear((char) 0, x, y, 1, 1);
        }

    }
}
