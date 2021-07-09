package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

import javax.swing.*;
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

    private MainPanel mainPanel;

    private int cursorX, cursorY;

    public FillCommand(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void execute() {
        cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        System.out.println( mainPanel.getSelectedChar());

        if (mainPanel.getCurrentButtonPressed() == 1) {
             mainPanel.getAsciiPanel().fill((char) mainPanel.getSelectedChar(), mainPanel.getAsciiPanel().getMouseCursorX(), mainPanel.getAsciiPanel().getMouseCursorY(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        }
        else {
            mainPanel.getAsciiPanel().fill((char) (0), cursorX, cursorY, Color.black, Color.black);
        }
//        mainPanel.getAsciiPanel().repaint();

        mainPanel.getCommandStack().push(this);
        System.out.println(mainPanel.getCurrentButtonPressed());
        System.out.println(mainPanel.getCommandStack());

    }

    //TODO implementa l'undo
    @Override
    public void undo() {
        System.out.println(mainPanel.getCommandStack());
    }

}
