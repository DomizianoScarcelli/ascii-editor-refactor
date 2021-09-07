package controller.commands.copycutpaste;

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
            mainPanel.getAsciiPanel().write((char) 219, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
            //TODO cambia sta cosa dei colori in modo da prendere il colore di ogni singolo punto selezionato, altrimenti in caso di colori diversi può modificare l'immagine, oppure fai qualcosa nell'undo idk
        });

        selectedPoints.removeIf(point -> mainPanel.getAsciiPanel().pickChar(point[0], point[1]) == 219); //delete the selection char in order to not paste it on the ascii panel


        mainPanel.getAsciiPanel().repaint();
        for (int x = super.getX1()+1; x <= super.getX2()-1; x++) {
            for (int y = super.getY1()+1; y <= super.getY2()-1; y++) {
                int[] point = new int[]{x, y};
                selectedPoints.add(point); //TODO anche qui ci sta la cosa del verificare se x è maggiore di y eccetera, altrimenti il quadrato succ cose strante
            }
        }
        mainPanel.selectedPoints = selectedPoints;


        mainPanel.getCommandStack().push(this);
    }

    @Override
    public void undo() {
        for (int[] point : super.getRectPoints()) {
            mainPanel.getAsciiPanel().setCursorX(point[0]);
            mainPanel.getAsciiPanel().setCursorY(point[1]);
            mainPanel.getAsciiPanel().write(getOldCharGrid()[point[0]][point[1]], mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        }
        mainPanel.selectedPoints = new ArrayList<>();
    }
}
