package controller.commands.copycutpaste;

import controller.commands.Command;

import java.awt.*;
import java.util.ArrayList;

/**
 * The command that selects the character in a certain area of the ascii panel
 */
public class SelectCommand extends Command {
    /**
     * The list of selected points
     */
    private ArrayList<int[]> selectedPoints = new ArrayList<>();
    /**
     * The up-right and down-left point coordinates in order to detect the selected area
     */
    private int x1, y1, x2, y2;
    /**
     * The point that form the rectangle
     */
    private ArrayList<int[]> rectPoints = new ArrayList<>();

    public SelectCommand(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Select the character in a certain area of the ascii panel
     */
    @Override
    public void execute() {
        super.execute();
        if (mainPanel.getCurrentSelection() != null) {
            mainPanel.getCurrentSelection().undo();
            mainPanel.setCurrentSelection(null);
        }
        mainPanel.setCurrentSelection(this);

        if (x2 > x1) {
            for (int x = x1; x <= x2; x++) {
                rectPoints.add(new int[]{x, y1});
                rectPoints.add(new int[]{x, y2});
            }
        }

        if (x1 >= x2) {
            for (int x = x2; x <= x1; x++) {
                rectPoints.add(new int[]{x, y1});
                rectPoints.add(new int[]{x, y2});
            }
        }

        if (y2 > y1) {
            for (int y = y1; y <= y2; y++) {
                rectPoints.add(new int[]{x1, y});
                rectPoints.add(new int[]{x2, y});
            }
        }

        if (y1 > y2) {
            for (int y = y2; y <= y1; y++) {
                rectPoints.add(new int[]{x1, y});
                rectPoints.add(new int[]{x2, y});
            }
        }

        for (int[] point : rectPoints) {
            int x = point[0];
            int y = point[1];
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write((char) 219, oldForegroundColorGrid[x][y], oldBackgroundColorGrid[x][y]);
        }
        mainPanel.getAsciiPanel().repaint();

        ArrayList<int[]> points = rectPoints;
        selectedPoints.addAll(points);

        selectedPoints.removeIf(point -> mainPanel.getAsciiPanel().pickChar(point[0], point[1]) == 219); //delete the selection char in order to not paste it on the ascii panel

        mainPanel.getAsciiPanel().repaint();
        int tempx1 = x1;
        int tempx2 = x2;
        int tempy1 = y1;
        int tempy2 = y2;
        if (x1 > x2) {
            tempx2 = x1;
            tempx1 = x2;
        }
        if (y1 > y2) {
            tempy1 = y2;
            tempy2 = y1;
        }
        for (int x = tempx1 + 1; x <= tempx2 - 1; x++) {
            for (int y = tempy1 + 1; y <= tempy2 - 1; y++) {

                int[] point = new int[]{x, y};
                selectedPoints.add(point);
            }
        }

        mainPanel.setSelectedPoints(selectedPoints);

        mainPanel.getCommandStack().push(this);
    }

    @Override
    public void undo() {
        for (int[] point : rectPoints) {
            int x = point[0];
            int y = point[1];
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write(oldCharGrid[x][y], mainPanel.getAsciiPanel().getOldForegroundColors()[x][y], mainPanel.getAsciiPanel().getOldBackgroundColors()[x][y]);
        }
        mainPanel.setSelectedPoints(new ArrayList<>());
    }

    public Color[][] getBackgroundColorGrid() {
        return oldBackgroundColorGrid;
    }

    public Color[][] getForegroundColorGrid() {
        return oldForegroundColorGrid;
    }
}
