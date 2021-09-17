package controller.commands.copycutpaste;

import controller.commands.Command;

/**
 * The command that clears the current selection
 */
public class ClearCommand extends Command {
    /**
     * Clears the current selection
     */
    @Override
    public void execute() {
        super.execute();
        for (int[] point : mainPanel.getSelectedPoints()) {
            int x = point[0];
            int y = point[1];
            mainPanel.getBeforeSelectionGrid()[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
            mainPanel.getAsciiPanel().clear((char) 0, x, y, 1, 1);
        }
        mainPanel.getAsciiPanel().repaint();
    }

}
