package controller.commands.copycutpaste;

import controller.ToolsPanelController;
import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;

import java.util.Comparator;
import java.util.Optional;

public class PasteCommand implements Command {
    private MainPanel mainPanel = MainPanel.getInstance();

    private char[][] oldCharGrid;
    private int x2, y2;

    public PasteCommand(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();
        //Gets the top left corner of the selection
        Optional<int[]> min = RightClickMenu.getInstance().getCopiedChars().stream().min(Comparator.comparingInt(point -> point[0] + point[1]));
        int x1 = min.get()[0];
        int y1 = min.get()[1];

        int offsetX = x2 - x1;
        int offsetY = y2 - y1;
        for (int[] point : RightClickMenu.getInstance().getCopiedChars()) {
            int x = point[0];
            int y = point[1];
            try {
                mainPanel.getAsciiPanel().setCursorX(x + offsetX);
                mainPanel.getAsciiPanel().setCursorY(y + offsetY);
                mainPanel.getAsciiPanel().write(
                        mainPanel.beforeSelectionGrid[y][x],
                        mainPanel.currentSelection.getForegroundColorGrid()[x][y],
                        mainPanel.currentSelection.getBackgroundColorGrid()[x][y]
                );


            } catch (ArrayIndexOutOfBoundsException ignored) {
                System.out.println("Occhioo");
            }
        }
        mainPanel.getCommandStack().push(this);

    }

    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setForegroundColors(mainPanel.getAsciiPanel().getOldForegroundColors());
        mainPanel.getAsciiPanel().setBackgroundColors(mainPanel.getAsciiPanel().getOldBackgroundColors());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
    }
}
