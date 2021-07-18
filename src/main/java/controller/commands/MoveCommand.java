package controller.commands;

import view.MainPanel;


public class MoveCommand implements Command {

    private int x1, y1, x2, y2;
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

        int diffX = x2 - x1;
        int diffY = y2 - y1;


        for (int[] point : mainPanel.selectedPoints) {
            int x = point[0];
            int y = point[1];
            try {
                mainPanel.getAsciiPanel().setCursorX(x + diffX);
                mainPanel.getAsciiPanel().setCursorY(y + diffY);

                mainPanel.getAsciiPanel().write((char) (int) mainPanel.getAsciiPanel().pickChar(x, y), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());

            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

            //TODO sta cosa crea un bug ma ci siamo quasi
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write(mainPanel.beforeSelectionGrid[x][y], mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());


        }
        mainPanel.getAsciiPanel().repaint();
        mainPanel.getCommandStack().push(this);

        System.out.println(diffX);
        System.out.println(diffY);
        mainPanel.selectedPoints.forEach(point -> {
            point[0] = point[0] + diffX;
            point[1] = point[1] + diffY;
        });


    }


    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setCursorX(mainPanel.getAsciiPanelMouseListener().getInitialCursorX());
        mainPanel.getAsciiPanel().setCursorY(mainPanel.getAsciiPanelMouseListener().getInitialCursorY());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
        int diffX = x2 - x1;
        int diffY = y2 - y1;
        mainPanel.selectedPoints.forEach(point -> {
            point[0] -= diffX;
            point[1] -= diffY;
        });
    }
}
