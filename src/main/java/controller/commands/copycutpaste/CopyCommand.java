package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;


public class CopyCommand implements Command {

    private MainPanel mainPanel = MainPanel.getInstance();

    @Override
    public void execute() {

        RightClickMenu.getInstance().setCopiedChars(mainPanel.selectedPoints);
        mainPanel.selectedPoints.forEach(point -> {
            int x = point[0];
            int y = point[1];
            mainPanel.beforeSelectionGrid[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
        });

    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
