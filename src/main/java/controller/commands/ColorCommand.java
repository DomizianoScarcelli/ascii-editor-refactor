package controller.commands;

public class ColorCommand implements Command {

    private boolean colorForeground;

    public ColorCommand(boolean colorForeground) {
        this.colorForeground = colorForeground;
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
