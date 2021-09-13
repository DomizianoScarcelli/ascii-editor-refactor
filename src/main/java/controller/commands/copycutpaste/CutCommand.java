package controller.commands.copycutpaste;

import view.MainPanel;
import view.RightClickMenu;

import java.util.ArrayList;

/**
 * The command that copies the current selection and clears it from the ascii panel
 */
public class CutCommand extends ClearCommand {
    /**
     * Copies the current selection and clears it from the ascii panel
     */
    @Override
    public void execute() {
        RightClickMenu.getInstance().setCopiedChars(MainPanel.getInstance().getSelectedPoints());
        super.execute();
        MainPanel.getInstance().setSelectedPoints(new ArrayList<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        //TODO implementa undo
    }
}
