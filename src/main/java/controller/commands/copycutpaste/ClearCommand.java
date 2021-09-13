package controller.commands.copycutpaste;

import controller.commands.Command;
import view.MainPanel;

import java.awt.*;

/**
 * The command that clears the current selection
 */
public class ClearCommand implements Command {
    /**
     * The MainPanel instance
     */
    private MainPanel mainPanel = MainPanel.getInstance();

    /**
     * Clears the current selection
     */
    @Override
    public void execute() {
        for (int[] point : mainPanel.getSelectedPoints()) {
            int x = point[0];
            int y = point[1];
            mainPanel.getBeforeSelectionGrid()[y][x] = (char) (int) mainPanel.getAsciiPanel().pickChar(x, y);
            mainPanel.getAsciiPanel().setCursorX(x);
            mainPanel.getAsciiPanel().setCursorY(y);
            mainPanel.getAsciiPanel().write((char) 0, Color.BLACK, Color.BLACK);
        }
        mainPanel.getAsciiPanel().repaint();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        //TODO implementa l'undo
    }

}
