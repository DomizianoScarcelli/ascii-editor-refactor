package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;
import view.RightClickMenu;

import java.util.Comparator;
import java.util.Optional;

public class PasteCommand implements Command {

    private MainPanel mainPanel = MainPanel.getInstance();

    @Override
    public void execute() {
        //Removes the selection
        mainPanel.currentSelection.undo();
        //Gets the top left corner of the selection
        Optional<int[]> min = RightClickMenu.getInstance().getCopiedChars().stream().min(Comparator.comparingInt(point -> point[0] + point[1]));
        int x1 = min.get()[0];
        int y1 = min.get()[1];
        int x2 = RightClickMenu.getInstance().getRightClickMouseCursorX();
        int y2 = RightClickMenu.getInstance().getRightClickMouseCursorY();

        int offsetX = x2 - x1;
        int offsetY = y2 - y1;
        for (int[] point : RightClickMenu.getInstance().getCopiedChars()) {
            int x = point[0];
            int y = point[1];
            try {
                mainPanel.getAsciiPanel().setCursorX(x + offsetX);
                mainPanel.getAsciiPanel().setCursorY(y + offsetY);
                //TODO il pickchar qui bugga il cut and paste (non più ma c'è sempre un piccolo bug)
                mainPanel.getAsciiPanel().write(mainPanel.beforeSelectionGrid[y][x], mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());

            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
