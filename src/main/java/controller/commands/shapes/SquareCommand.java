package controller.commands.shapes;

/**
 * The command that draws a square
 */
public class SquareCommand extends RectCommand {
    public SquareCommand(int x, int y, int pointDistance) {
        super(x, y, x + pointDistance, y + pointDistance);
    }

    /**
     * Draws a square on the ascii panel in correspondence of the mouse position
     */
    @Override
    public void execute() {
        super.execute();
    }

}
