package controller.commands;

import com.sun.tools.javac.Main;
import view.MainPanel;

import java.awt.*;

/**
 * The action performed after the "Fill" button is clicked by the mouse.
 * It fills the closed area in the current mouse position with the selected character.
 *
 * button is the button pressed by the mouse encoded with an integer:
 *               <ol>
 *               <li>left button;</li>
 *               <li>center button;</li>
 *               <li>right button.</li>
 *               </ol>
 */
public class FillCommand implements Command {

    MainPanel mainPanel;
    int button;


    public FillCommand(MainPanel mainPanel, int button) {
        this.mainPanel = mainPanel;
        this.button = button;
    }

    @Override
    public void execute() {
        int cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        int cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        if (button == 1) {
            mainPanel.getAsciiPanel().fill((char) mainPanel.getSelectedChar(), cursorX, cursorY, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().fill((char) (0), cursorX, cursorY, Color.black, Color.black);
        }
        mainPanel.getAsciiPanel().repaint();
    }

}
