package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;


/**
 * Writes the selected char on the panel when the left mouse button is pressed.
 * the character is written on the mouse current position.
 * If another mouse button is pressed, it deletes the character on the current mouse position.
 * <p>
 * button is the button pressed by the mouse encoded with an integer:
 * <ol>
 * <li>left button;</li>
 * <li>center button;</li>
 * <li>right button.</li>
 * </ol>
 */
public class PaintCommand implements Command {
    private MainPanel mainPanel;

    //Stores the cursor position so that it can undo the last command
    private int cursorX, cursorY;
    private int oldChar;

    private Command previousCommand = null;


    private char[][] oldCharGrid;

    public PaintCommand(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    /**
     * Execute the command
     */
    @Override
    public void execute() {

        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        oldCharGrid = new char[currentChars.length][currentChars[0].length];
        for (int y = 0; y < currentChars.length; y++){
            for (int x = 0; x < currentChars[0].length; x++){
                oldCharGrid[y][x] = currentChars[y][x];
            }
        }

        cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        cursorY = mainPanel.getAsciiPanel().getMouseCursorY();
        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        oldChar = mainPanel.getAsciiPanel().pickChar(cursorX, cursorY);
        if (mainPanel.getCurrentButtonPressed() == 1) {
            //Prevents a char is not written twice on the same position, so the undo doesn't fully work.
            if (!(oldChar == mainPanel.getSelectedChar() && cursorX == mainPanel.getAsciiPanel().getMouseCursorX() && cursorY == mainPanel.getAsciiPanel().getMouseCursorY())) {
                mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
                mainPanel.getCommandStack().push(this);
            }
        } else {
            mainPanel.getAsciiPanel().write((char) 0);
        }
        mainPanel.getAsciiPanel().repaint();
        ToolsPanelController.selectPaintButton();
    }


    /**
     * Undo the command
     */
    @Override
    public void undo() {
        mainPanel.getAsciiPanel().setChars(oldCharGrid);
//        mainPanel.getAsciiPanel().setCursorX(cursorX);
//        mainPanel.getAsciiPanel().setCursorY(cursorY);
//        mainPanel.getAsciiPanel().write((char) oldChar);
        mainPanel.getAsciiPanel().repaint();
//        if (this.previousCommand != null) {
//            System.out.println("daje");
//            this.previousCommand.undo();
//        }
    }

    public void setPreviousCommand(Command previousCommand) {
        this.previousCommand = previousCommand;
    }

    public Command getPreviousCommand() {
        return previousCommand;
    }
}
