package controller.commands;

import view.MainPanel;

/**
 * Writes the selected char on the panel when the left mouse button is pressed.
 * the character is written on the mouse current position.
 * If another mouse button is pressed, it deletes the character on the current mouse position.
 *
 * button is the button pressed by the mouse encoded with an integer:
 *               <ol>
 *               <li>left button;</li>
 *               <li>center button;</li>
 *               <li>right button.</li>
 *               </ol>
 */
public class PaintCommand implements Command {
    MainPanel mainPanel;
    int button;

    public PaintCommand(MainPanel mainPanel, int button) {
        this.mainPanel = mainPanel;
        this.button = button;
    }

    @Override
    public void execute() {
        int cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        int cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        if (button == 1) {
            mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().write((char) 0);
        }
        mainPanel.getAsciiPanel().repaint();

    }
}
