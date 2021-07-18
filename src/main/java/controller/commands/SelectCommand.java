package controller.commands;

import controller.commands.shapes.RectCommand;
import view.MainPanel;

import java.util.ArrayList;

public class SelectCommand extends RectCommand {

    private ArrayList<int[]> selectedPoints = new ArrayList<>();
    private MainPanel mainPanel = MainPanel.getInstance();

    public SelectCommand(int x1, int y1, int x2, int y2) {
        super(x1, y1, x2, y2);
    }

    @Override
    public void execute() {
        if (mainPanel.currentSelection != null)
            mainPanel.currentSelection.undo(); //vedi pure sta cosa minchia non funziona niente ahah
        mainPanel.currentSelection = this;
        super.execute();
        ArrayList<int[]> points = super.getRectPoints();
        selectedPoints.addAll(points);

        selectedPoints.forEach(point -> {
            mainPanel.getAsciiPanel().setCursorX(point[0]);
            mainPanel.getAsciiPanel().setCursorY(point[1]);
            mainPanel.getAsciiPanel().write((char) 219, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        });

        mainPanel.getAsciiPanel().repaint();
        for (int x = super.getX1(); x <= super.getX2(); x++) {
            for (int y = super.getY1(); y <= super.getY2(); y++) {
                int[] point = new int[]{x, y};
                selectedPoints.add(point); //anche qui ci sta la cosa del verificare se x è maggiore di y eccetera.
            }
        }
        mainPanel.selectedPoints = selectedPoints;
        mainPanel.getCommandStack().push(this);
    }

    @Override
    public void undo() {
        super.undo(); //cambia perchè c`è un bug quando viene selezionato qualcosa e viene disegnato qualcos'altro.
    }
}
