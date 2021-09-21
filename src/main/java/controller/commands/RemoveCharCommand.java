package controller.commands;

public class RemoveCharCommand extends Command {
    /**
     * Removes each instance of the selected character from the selection. If the selection is empty, the effect is applied to the whole panel.
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
        if (mainPanel.getAsciiPanel().pickChar(x, y) == mainPanel.getSelectedChar())
            mainPanel.getAsciiPanel().clear((char) 0, x, y, 1, 1);
    }
}
