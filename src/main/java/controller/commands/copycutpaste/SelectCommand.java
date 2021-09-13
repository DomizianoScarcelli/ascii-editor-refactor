package controller.commands.copycutpaste;

import controller.ToolsPanelController;
import controller.commands.Command;
import controller.commands.shapes.RectCommand;
import view.MainPanel;

import java.awt.*;
import java.util.ArrayList;

public class SelectCommand implements Command {
    private ArrayList<int[]> selectedPoints = new ArrayList<>();
    private MainPanel mainPanel = MainPanel.getInstance();
    private Color[][] foregroundColorGrid, backgroundColorGrid;
    private char[][] oldCharGrid;
    private int x1, y1, x2, y2;
    private ArrayList<int[]> rectPoints = new ArrayList<>();

    public SelectCommand(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void execute() {
        if (mainPanel.currentSelection != null) {
            mainPanel.currentSelection.undo();
            mainPanel.currentSelection = null;
        }

        int height = mainPanel.getAsciiPanel().getHeightInCharacters();
        int width = mainPanel.getAsciiPanel().getWidthInCharacters();
        foregroundColorGrid = new Color[width][height];
        backgroundColorGrid = new Color[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                mainPanel.getAsciiPanel().setCursorX(x);
                mainPanel.getAsciiPanel().setCursorY(y);
                foregroundColorGrid[x][y] = mainPanel.getAsciiPanel().pickFC(x, y);
                backgroundColorGrid[x][y] = mainPanel.getAsciiPanel().pickBC(x, y);
            }
        }

        mainPanel.currentSelection = this;
//        super.execute();
        //--OLD SUPER--//
        oldCharGrid = ToolsPanelController.copyCharGrid();

        mainPanel.getCommandStack().push(this);

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

        //TODO aggiusta un po' sta cosa ma diciamo che più o meno ci siamo dai (per i colori intendo)
        for (int[] point : rectPoints) {
            int x = point[0];
            int y = point[1];
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write((char) 219, foregroundColorGrid[x][y], backgroundColorGrid[x][y]);
        }
        mainPanel.getAsciiPanel().repaint();

        ArrayList<int[]> points = rectPoints;
        selectedPoints.addAll(points);
        mainPanel.selectionChars.addAll(points);

//        selectedPoints.forEach(point -> {
//            int x = point[0];
//            int y = point[1];
//            mainPanel.getAsciiPanel().setCursorX(x);
//            mainPanel.getAsciiPanel().setCursorY(y);
//            mainPanel.getAsciiPanel().write((char) 219, Color.white, Color.black);
//        });

        selectedPoints.removeIf(point -> mainPanel.getAsciiPanel().pickChar(point[0], point[1]) == 219); //delete the selection char in order to not paste it on the ascii panel

        mainPanel.getAsciiPanel().repaint();
        for (int x = x1 + 1; x <= x2 - 1; x++) {
            for (int y = y1 + 1; y <= y2 - 1; y++) {
                int[] point = new int[]{x, y};
                selectedPoints.add(point); //TODO anche qui ci sta la cosa del verificare se x è maggiore di y eccetera, altrimenti succ cose strane
            }
        }
        mainPanel.selectedPoints = selectedPoints;

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
        mainPanel.selectedPoints = new ArrayList<>();
    }

    public Color[][] getBackgroundColorGrid() {
        return backgroundColorGrid;
    }

    public Color[][] getForegroundColorGrid() {
        return foregroundColorGrid;
    }
}
