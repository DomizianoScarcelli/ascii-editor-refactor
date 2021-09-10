package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;

import java.awt.*;

public class ClearCommand implements Command {
    private MainPanel mainPanel = MainPanel.getInstance();

    @Override
    public void execute() {

        for (int[] point : mainPanel.selectedPoints) {
            int x = point[0];
            int y = point[1];
            mainPanel.beforeSelectionGrid[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write((char) 0, Color.BLACK, Color.BLACK);
        }
        mainPanel.getAsciiPanel().repaint();

    }

    @Override
    public void undo() {
        //TODO implementa l'undo
    }
}
