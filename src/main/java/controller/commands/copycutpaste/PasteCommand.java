package controller.commands.copycutpaste;

import controller.commands.Command;
import view.RightClickMenu;

import java.util.Comparator;
import java.util.Optional;

/**
 * The command that pastes the current selection into the ascii panel in correspondence of the mouse position
 */
public class PasteCommand extends Command {
    /**
     * The coordinates where the selection has to be pasted
     */
    private int x2, y2;

    public PasteCommand(int x2, int y2) {
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Paste the current selection into the ascii panel in correspondence of the mouse position
     */
    @Override
    public void execute() {
        super.execute();        //Gets the top left corner of the selection
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
                        mainPanel.getBeforeSelectionGrid()[y][x],
                        mainPanel.getCurrentSelection().getForegroundColorGrid()[x][y],
                        mainPanel.getCurrentSelection().getBackgroundColorGrid()[x][y]
                );

            } catch (ArrayIndexOutOfBoundsException ignored) {
                System.out.println("Occhioo");
            }
        }
        mainPanel.getCommandStack().push(this);

    }

}
