package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;

//TODO prima di documentare vedi se conviene o no togliere sta roba
public class MoveCommand implements Command {
    /**
     *
     */
    private int x1, y1, x2, y2;
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel = MainPanel.getInstance();
    private char[][] oldCharGrid;

    public MoveCommand(int x1, int y1, int x2, int y2) {
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
        for (int y = 0; y < currentChars.length; y++) {
            for (int x = 0; x < currentChars[0].length; x++) {
                oldCharGrid[y][x] = currentChars[y][x];
            }
        }

        int offsetX = x2 - x1;
        int offsetY = y2 - y1;

        for (int[] point : mainPanel.getSelectedPoints()) {
            int x = point[0];
            int y = point[1];
            try {
                mainPanel.getAsciiPanel().setCursorX(x + offsetX);
                mainPanel.getAsciiPanel().setCursorY(y + offsetY);
                mainPanel.getAsciiPanel().write((char) (int) mainPanel.getAsciiPanel().pickChar(x, y), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());

//                //TODO sta cosa crea un bug ma ci siamo quasi
//                mainPanel.getAsciiPanel().setCursorX(x);
//                mainPanel.getAsciiPanel().setCursorY(y);
//                mainPanel.getAsciiPanel().write(mainPanel.beforeSelectionGrid[x][y], mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());

            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }

        mainPanel.getAsciiPanel().repaint();
        mainPanel.getCommandStack().push(this);

//        System.out.println(offsetX);
//        System.out.println(offsetY);
        mainPanel.getSelectedPoints().forEach(point -> {
            point[0] = point[0] + offsetX;
            point[1] = point[1] + offsetY;
        });
    }

    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setCursorX(mainPanel.getAsciiPanelMouseListener().getInitialCursorX());
        mainPanel.getAsciiPanel().setCursorY(mainPanel.getAsciiPanelMouseListener().getInitialCursorY());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
        int offsetX = x2 - x1;
        int offsetY = y2 - y1;
        mainPanel.getSelectedPoints().forEach(point -> {
            point[0] -= offsetX;
            point[1] -= offsetY;
        });
    }
}
