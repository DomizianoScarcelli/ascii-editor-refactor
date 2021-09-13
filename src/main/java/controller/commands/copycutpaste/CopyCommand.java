package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;

/**
 * The command that copies the current selections
 */
public class CopyCommand implements Command {
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel = MainPanel.getInstance();

    /**
     * Copies the current selections
     */
    @Override
    public void execute() {
        RightClickMenu.getInstance().setCopiedChars(mainPanel.getSelectedPoints());
        mainPanel.getSelectedPoints().forEach(point -> {
            int x = point[0];
            int y = point[1];
            mainPanel.getBeforeSelectionGrid()[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
        });

    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
