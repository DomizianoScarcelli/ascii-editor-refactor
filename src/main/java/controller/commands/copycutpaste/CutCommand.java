package controller.commands.copycutpaste;

import view.MainPanel;
import view.RightClickMenu;

import java.util.ArrayList;

/**
 * The command that copies the current selection and clears it from the ascii panel
 */
public class CutCommand extends ClearCommand {
    /**
     * The copied chars before the executions of the command.
     */
    private ArrayList<int[]> previousCopiedChars;

    /**
     * Copies the current selection and clears it from the ascii panel
     */
    @Override
    public void execute() {
        if (RightClickMenu.getInstance().getCopiedChars() != null) {
            previousCopiedChars = (ArrayList<int[]>) RightClickMenu.getInstance().getCopiedChars().clone();
        } else {
            previousCopiedChars = null;
        }
        RightClickMenu.getInstance().setCopiedChars(MainPanel.getInstance().getSelectedPoints());
        super.execute();
        mainPanel.setSelectedPoints(new ArrayList<>());
    }

    /**
     * Sets the copied chars to the chars that where copied before the cut and restore the ascii panel
     */
    @Override
    public void undo() {
        super.undo();
        RightClickMenu.getInstance().setCopiedChars(previousCopiedChars);
        mainPanel.getCurrentSelection().undo();

    }
}
