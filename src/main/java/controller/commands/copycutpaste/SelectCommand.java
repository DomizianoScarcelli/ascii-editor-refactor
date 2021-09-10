package controller.commands.copycutpaste;

import controller.commands.shapes.RectCommand;
import view.MainPanel;

import java.awt.*;
import java.util.ArrayList;

public class SelectCommand extends RectCommand {
    private ArrayList<int[]> selectedPoints = new ArrayList<>();
    private MainPanel mainPanel = MainPanel.getInstance();
    private Color[][] foregroundColorGrid, backgroundColorGrid;

    public SelectCommand(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void execute() {
        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        foregroundColorGrid = new Color[currentChars.length][currentChars[0].length];
        backgroundColorGrid = new Color[currentChars.length][currentChars[0].length];
        //Matrix copy
        for (int y = 0; y < currentChars.length; y++) {
            for (int x = 0; x < currentChars[0].length; x++) {
                foregroundColorGrid[y][x] = mainPanel.getAsciiPanel().pickFC(y, x);
                backgroundColorGrid[y][x] = mainPanel.getAsciiPanel().pickBC(y, x);
            }
        }
//        if (mainPanel.currentSelection != null)
//            mainPanel.currentSelection.undo(); //TODO vedi pure sta cosa minchia non funziona niente ahah
        mainPanel.currentSelection = this;
        super.execute();
        ArrayList<int[]> points = super.getRectPoints();
        selectedPoints.addAll(points);
        mainPanel.selectionChars.addAll(points);


        selectedPoints.forEach(point -> {
            mainPanel.getAsciiPanel().setCursorX(point[0]);
            mainPanel.getAsciiPanel().setCursorY(point[1]);
            System.out.println(mainPanel.getAsciiPanel().pickFC(point[0], point[1]));
            mainPanel.getAsciiPanel().write((char) 219, Color.white, Color.black);
        });

        selectedPoints.removeIf(point -> mainPanel.getAsciiPanel().pickChar(point[0], point[1]) == 219); //delete the selection char in order to not paste it on the ascii panel


        mainPanel.getAsciiPanel().repaint();
        for (int x = super.getX1() + 1; x <= super.getX2() - 1; x++) {
            for (int y = super.getY1() + 1; y <= super.getY2() - 1; y++) {
                int[] point = new int[]{x, y};
                selectedPoints.add(point); //TODO anche qui ci sta la cosa del verificare se x è maggiore di y eccetera, altrimenti succ cose strane
            }
        }
        mainPanel.selectedPoints = selectedPoints;


        mainPanel.getCommandStack().push(this);
    }

    @Override
    public void undo() {
        for (int[] point : super.getRectPoints()) {
            int x = point[0];
            int y = point[1];
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write(getOldCharGrid()[x][y], mainPanel.getAsciiPanel().getOldForegroundColors()[x][y], mainPanel.getAsciiPanel().getOldBackgroundColors()[x][y]);
        }
        mainPanel.selectedPoints = new ArrayList<>();
    }

    public Color[][] getBackgroundColorGrid() {
        return backgroundColorGrid;
    }

    public Color[][] getForegroundColorGrid() {
        return foregroundColorGrid;
    }
}
