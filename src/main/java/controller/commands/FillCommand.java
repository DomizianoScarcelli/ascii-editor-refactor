package controller.commands;

/**
 * The action performed after the "Fill" button is clicked by the mouse.
 * It fills the closed area in the current mouse position with the selected character.
 * <p>
 * button is the button pressed by the mouse encoded with an integer:
 * <ol>
 * <li>left button;</li>
 * <li>center button;</li>
 * <li>right button.</li>
 * </ol>
 */
public class FillCommand extends Command {
    /**
     * The mouse cursor position
     */
    private int cursorX, cursorY;

    public FillCommand(int cursorX, int cursorY) {
        this.cursorY = cursorY;
        this.cursorX = cursorX;
    }

    /**
     * Fills up the area with the current selected character in correspondence of the mouse position
     */
    @Override
    public void execute() {
        super.execute();

        mainPanel.getAsciiPanel().fill((char) mainPanel.getSelectedChar(), cursorX, cursorY, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        mainPanel.getAsciiPanel().repaint();

    }
}
