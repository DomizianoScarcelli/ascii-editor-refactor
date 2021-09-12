package controller.commands;

import controller.ToolsPanelController;
import controller.commands.Command;
import controller.commands.PaintCommand;
import view.MainPanel;

import java.awt.*;

public class EraseCommand implements Command {
    MainPanel mainPanel;
    //Stores the cursor position so that it can undo the last command
    private int cursorX, cursorY;
    private int oldChar;


    private char[][] oldCharGrid;

    public EraseCommand(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void execute() {
        oldCharGrid = ToolsPanelController.copyCharGrid();

        cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        cursorY = mainPanel.getAsciiPanel().getMouseCursorY();
        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        oldChar = mainPanel.getAsciiPanel().pickChar(cursorX, cursorY);
        //Prevents a char is not written twice on the same position, so the undo doesn't fully work.
        if (!(oldChar == (char) 0 && cursorX == mainPanel.getAsciiPanel().getMouseCursorX() && cursorY == mainPanel.getAsciiPanel().getMouseCursorY())) {
            mainPanel.getAsciiPanel().write((char) 0, Color.BLACK, Color.BLACK);
            mainPanel.getCommandStack().push(this);
        }
        mainPanel.getAsciiPanel().repaint();
    }

    @Override
    public void undo() {
        //TODO implementa undo
    }
}
