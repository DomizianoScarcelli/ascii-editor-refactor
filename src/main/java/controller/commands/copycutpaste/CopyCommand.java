package controller.commands.copycutpaste;

import controller.commands.Command;
import view.RightClickMenu;

import java.util.ArrayList;

/**
 * The command that copies the current selections
 */
public class CopyCommand extends Command {
    /**
     * The copied chars before the executions of the command.
     */
    private ArrayList<int[]> previousCopiedChars;

    /**
     * Copies the current selections
     */
    @Override
    public void execute() {
        if (RightClickMenu.getInstance().getCopiedChars() != null) {
            previousCopiedChars = (ArrayList<int[]>) RightClickMenu.getInstance().getCopiedChars().clone();
        } else {
            previousCopiedChars = null;
        }

        RightClickMenu.getInstance().setCopiedChars(mainPanel.getSelectedPoints());
        mainPanel.getSelectedPoints().forEach(point -> {
            int x = point[0];
            int y = point[1];
            mainPanel.getBeforeSelectionGrid()[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
        });
        mainPanel.getCommandStack().push(this);

    }

    /**
     * Sets the copied chars to the chars that where copied before the copy action
     */
    @Override
    public void undo() {
        RightClickMenu.getInstance().setCopiedChars(previousCopiedChars);
        mainPanel.getCurrentSelection().undo();
    }
}
