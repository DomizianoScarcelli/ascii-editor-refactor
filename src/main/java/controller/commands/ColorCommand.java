package controller.commands;

import model.AsciiPanel;
import view.MainPanel;

import java.awt.*;

public class ColorCommand implements Command {

    private boolean colorForeground;
    private MainPanel mainPanel = MainPanel.getInstance();
    private AsciiPanel asciiPanel = mainPanel.getAsciiPanel();


    public ColorCommand(boolean colorForeground) {
        this.colorForeground = colorForeground;
    }

    @Override
    public void execute() {
        char[][] currentChars = asciiPanel.getChars();
        for (int[] point : mainPanel.selectedPoints) {
            int x = point[0];
            int y = point[1];
            char currentChar = currentChars[x][y];
            Color currentFC = asciiPanel.pickFC(x, y);
            Color currentBC = asciiPanel.pickBC(x, y);
            Color selectedFC = mainPanel.getDefaultForegroundColor();
            Color selectedBC = mainPanel.getDefaultBackgroundColor();
            asciiPanel.setCursorX(x);
            asciiPanel.setCursorY(y);
            if (colorForeground)
                asciiPanel.write(currentChar, selectedFC, currentBC);
            else asciiPanel.write(currentChar, currentFC, selectedBC);
        }
        asciiPanel.repaint();
    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
