package controller.commands.shapes;

import controller.commands.Command;
import view.MainPanel;


public class SquareCommand implements Command {

    private int cursorX;
    private int cursorY;
    private MainPanel mainPanel = MainPanel.getInstance();
    private int sideDimension;
    private char[][] oldCharGrid;

    public SquareCommand(int sideDimension){
        this.sideDimension = sideDimension;
    }

    @Override
    public void execute() {

        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        oldCharGrid = new char[currentChars.length][currentChars[0].length];
        //Matrix copy
        for (int y = 0; y < currentChars.length; y++){
            for (int x = 0; x < currentChars[0].length; x++){
                oldCharGrid[y][x] = currentChars[y][x];
            }
        }

        cursorX = mainPanel.getAsciiPanel().getCursorX();
        cursorY = mainPanel.getAsciiPanel().getCursorY();

        for (int y = cursorY; y < cursorY + sideDimension; y++){
            for (int x = cursorX; x < cursorX + sideDimension; x++){
                if(x == cursorX || y == cursorY || x == cursorX + sideDimension -1 || y == cursorY + sideDimension -1){
                    mainPanel.getAsciiPanel().setCursorX(x);
                    mainPanel.getAsciiPanel().setCursorY(y);
                    mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
                }
            }
        }
        mainPanel.getCommandStack().push(this);
    }




    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setCursorX(mainPanel.getAsciiPanelMouseListener().getInitialCursorX());
        mainPanel.getAsciiPanel().setCursorY(mainPanel.getAsciiPanelMouseListener().getInitialCursorY());
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
        mainPanel.getAsciiPanel().repaint();
    }

}
