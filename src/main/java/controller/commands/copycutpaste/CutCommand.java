package controller.commands.copycutpaste;

import view.MainPanel;
import view.RightClickMenu;

import java.util.ArrayList;

public class CutCommand extends ClearCommand {

    @Override
    public void execute() {
        RightClickMenu.getInstance().setCopiedChars(MainPanel.getInstance().selectedPoints);
        super.execute();
        MainPanel.getInstance().selectedPoints = new ArrayList<>();
    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
