package controller.commands.shapes;

import controller.commands.Command;
import view.MainPanel;

import java.util.ArrayList;

public class RectCommand implements Command {
    MainPanel mainPanel = MainPanel.getInstance();
    char[][] oldCharGrid;
    int x1, y1, x2, y2;
    ArrayList<int[]> rectPoints = new ArrayList<>();

    public RectCommand(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void execute() {
        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        oldCharGrid = new char[currentChars.length][currentChars[0].length];
        //Matrix copy
        for (int y = 0; y < currentChars.length; y++){
            for (int x = 0; x < currentChars[0].length; x++){
                oldCharGrid[y][x] = currentChars[y][x];
            }
        }

        mainPanel.getCommandStack().push(this);

        for (int x = x1; x <= x2; x++) {
            rectPoints.add(new int[]{x, y1});
            rectPoints.add(new int[]{x, y2});
        }
        for (int y = y1; y <= y2; y++) {
            rectPoints.add(new int[]{x1, y});
            rectPoints.add(new int[]{x2, y});
        }

        for (int[] point : rectPoints){
            mainPanel.getAsciiPanel().setCursorX(point[0]);
            mainPanel.getAsciiPanel().setCursorY(point[1]);
            mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
            mainPanel.getAsciiPanel().repaint();
        }


    }

    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setCursorX(mainPanel.getAsciiPanelMouseListener().getInitialCursorX());
        mainPanel.getAsciiPanel().setCursorY(mainPanel.getAsciiPanelMouseListener().getInitialCursorY());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
    }

}
