package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;

import java.awt.*;
import java.util.ArrayList;

public class CutCommand implements Command {
    private MainPanel mainPanel = MainPanel.getInstance();

    @Override
    public void execute() {
        RightClickMenu.getInstance().setCopiedChars(mainPanel.selectedPoints);


        mainPanel.selectedPoints = new ArrayList<>();
        for (int[] point : RightClickMenu.getInstance().getCopiedChars()) {
            int x = point[0];
            int y = point[1];
            mainPanel.beforeSelectionGrid[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x,y);
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write((char) 0, Color.BLACK, Color.BLACK);
        }
        mainPanel.getAsciiPanel().repaint();
    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
