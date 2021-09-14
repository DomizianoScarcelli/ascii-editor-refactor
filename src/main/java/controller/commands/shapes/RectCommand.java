package controller.commands.shapes;

import controller.ToolsPanelController;
import controller.commands.Command;
import view.MainPanel;

import java.util.ArrayList;

/**
 * The command that draws a rectangle
 */
public class RectCommand extends Command {
    /**
     * The up-right and down-left point coordinates
     */
    private int x1, y1, x2, y2;
    /**
     * The point that form the rectangle (inside points included)
     */
    private ArrayList<int[]> rectPoints = new ArrayList<>();

    public RectCommand(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    //TODO vedi se riesci a usare l'eredità per non usare due volte lo stesso codice sia qua che in select

    /**
     * Draws a rectangle on the ascii panel in correspondence of the mouse position
     */
    @Override
    public void execute() {
        super.execute();
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
            mainPanel.getAsciiPanel().setCursorX(point[0]);
            mainPanel.getAsciiPanel().setCursorY(point[1]);
            mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
            mainPanel.getAsciiPanel().repaint();
        }
    }

}
