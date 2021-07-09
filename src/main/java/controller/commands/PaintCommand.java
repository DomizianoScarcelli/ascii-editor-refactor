package controller.commands;

import controller.ToolsPanelController;
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

    //Stores the cursor position so that it can undo the last command
    int cursorX, cursorY;

    //TODO store the old char on the cursor position for the undo

    public PaintCommand(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    /**
     * Execute the command
     */
    @Override
    public void execute() {
        cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        cursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        mainPanel.getAsciiPanel().setCursorX(cursorX);
        mainPanel.getAsciiPanel().setCursorY(cursorY);
        if (mainPanel.getCurrentButtonPressed() == 1) {
            mainPanel.getAsciiPanel().write((char) mainPanel.getSelectedChar(), mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        } else {
            mainPanel.getAsciiPanel().write((char) 0);
        }
        mainPanel.getAsciiPanel().repaint();

        mainPanel.getCommandStack().push(this);
        ToolsPanelController.selectPaintButton();
    }

//    /**
//     * Undo the command
//     */
//    @Override
//    public void undo() {
//        mainPanel.getAsciiPanel().setCursorX(cursorX);
//        mainPanel.getAsciiPanel().setCursorY(cursorY);
//        mainPanel.getAsciiPanel().write((char) 0); //TODO write the old char instead of a blank char
//        mainPanel.getAsciiPanel().repaint();
//        System.out.println("Undone");
//    }


}
