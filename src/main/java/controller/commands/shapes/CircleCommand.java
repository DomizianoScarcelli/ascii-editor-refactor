package controller.commands.shapes;

import controller.ToolsPanelController;
import controller.commands.Command;
import view.MainPanel;

import java.util.Arrays;


public class CircleCommand implements Command {

    private int cursorX;
    private int cursorY;
    private MainPanel mainPanel = MainPanel.getInstance();
    private int radius;
    private char[][] oldCharGrid;

    public CircleCommand(int radius) {
        this.radius = radius;
    }

    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();

        cursorX = mainPanel.getAsciiPanel().getCursorX();
        cursorY = mainPanel.getAsciiPanel().getCursorY();

        mainPanel.getCommandStack().push(this);
        midPointCircleDraw(cursorX, cursorY, radius);
    }

    //TODO aggiusta sta cosa, non so perchè ma due punti non vengono disegnati (mi sa che devi proprio cambiare implementazione dell'algoritmo)
    //Source: https://www.geeksforgeeks.org/mid-point-circle-drawing-algorithm/
    public void midPointCircleDraw(int x_centre, int y_centre, int r) {
        int x = r, y = 0;


        // Printing the initial point on the axes after translation
        drawPoint(x + x_centre, y + y_centre);

        // When radius is zero only a single point will be printed
        if (r > 0) {
            drawPoint(x + x_centre, -y + y_centre);
            drawPoint(y + x_centre, x + y_centre);
            drawPoint(-y + x_centre, x + y_centre);
        }
        // Initialising the value of P
        int P = 1 - r;
        while (x > y) {
            y++;
            // Mid-point is inside or on the perimeter
            if (P <= 0) P = P + 2 * y + 1;
                // Mid-point is outside the perimeter
            else {
                x--;
                P = P + 2 * y - 2 * x + 1;
            }

            // All the perimeter points have already been printed
            if (x < y) break;

            // Printing the generated point and its reflection in the other octants after translation
            drawPoint(x + x_centre, y + y_centre);
            drawPoint(-x + x_centre, y + y_centre);
            drawPoint(x + x_centre, -y + y_centre);
            drawPoint(-x + x_centre, -y + y_centre);

            // If the generated point is on the line x = y then the perimeter points have already been printed
            if (x != y) {
                drawPoint(y + x_centre, x + y_centre);
                drawPoint(-y + x_centre, x + y_centre);
                drawPoint(y + x_centre, -x + y_centre);
                drawPoint(-y + x_centre, -x + y_centre);
            }
        }
    }

    public void drawPoint(int x, int y) {
        mainPanel.getAsciiPanel().setCursorX(x);
        mainPanel.getAsciiPanel().setCursorY(y);
        mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
    }


    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setCursorX(mainPanel.getAsciiPanelMouseListener().getInitialCursorX());
        mainPanel.getAsciiPanel().setCursorY(mainPanel.getAsciiPanelMouseListener().getInitialCursorY());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
    }

}
