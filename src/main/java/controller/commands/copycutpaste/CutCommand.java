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
        System.out.println("Tagliato");
        RightClickMenu.getInstance().setCopiedChars(mainPanel.selectedPoints);
        mainPanel.selectedPoints = new ArrayList<>();
        for (int[] point : RightClickMenu.getInstance().getCopiedChars()){
            mainPanel.getAsciiPanel().setCursorX(point[0]);
            mainPanel.getAsciiPanel().setCursorY(point[1]);
            mainPanel.getAsciiPanel().write((char) 0, Color.BLACK, Color.BLACK);
        }
        mainPanel.getAsciiPanel().repaint();
    }

    @Override
    public void undo() {

    }
}
