package controller.commands;

import view.MainPanel;

public class UndoCommand implements Command {
    Command command;
    private char[][] oldCharGrid;


    @Override
    public void execute() {

        char[][] currentChars = MainPanel.getInstance().getAsciiPanel().getChars();
        oldCharGrid = new char[currentChars.length][currentChars[0].length];
        for (int y = 0; y < currentChars.length; y++) {
            for (int x = 0; x < currentChars[0].length; x++) {
                oldCharGrid[y][x] = currentChars[y][x];
            }
        }
        command = MainPanel.getInstance().getCommandStack().pop();
        command.undo();
//        MainPanel.getInstance().getRedoCommandStack().push(command);


    }

    @Override
    public void undo() {
        MainPanel.getInstance().getCommandStack().push(command);
        MainPanel.getInstance().getAsciiPanel().setChars(oldCharGrid);
    }
}
