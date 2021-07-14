package controller.commands;

import view.MainPanel;


public class SquareCommand implements Command{

    private int cursorX;
    private int cursorY;
    private MainPanel mainPanel = MainPanel.getInstance();
    private int sideDimension;

    public SquareCommand(int sideDimension){
        this.sideDimension = sideDimension;
    }

    @Override
    public void execute() {
        cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        for (int y = cursorY; y < cursorY + sideDimension; y++){
            for (int x = cursorX; x < cursorX + sideDimension; x++){
                if(x == cursorX || y == cursorY || x == cursorX + sideDimension -1 || y == cursorY + sideDimension -1){
                    mainPanel.getAsciiPanel().setCursorX(x);
                    mainPanel.getAsciiPanel().setCursorY(y);
                    mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
                }
            }
        }
    }




    @Override
    public void undo() {

    }

}
