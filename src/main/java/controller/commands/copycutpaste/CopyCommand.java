package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;


public class CopyCommand implements Command {

    private MainPanel mainPanel = MainPanel.getInstance();

    @Override
    public void execute() {
        System.out.println("Copiato");
        RightClickMenu.getInstance().setCopiedChars(mainPanel.selectedPoints);
    }

    @Override
    public void undo() {

    }
}
