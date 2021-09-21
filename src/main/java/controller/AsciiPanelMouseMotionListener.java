package controller;

import controller.commands.*;
import controller.commands.copycutpaste.PasteCommand;
import controller.commands.copycutpaste.SelectCommand;
import controller.commands.shapes.CircleCommand;
import controller.commands.shapes.RectCommand;
import controller.commands.shapes.SquareCommand;
import view.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * Models the actions performed on the panel by the mouse when moving.
 */
public class AsciiPanelMouseMotionListener implements MouseMotionListener {
    /**
     * The {@link MainPanel} object that allows to edit the image on the ascii panel when an action is performed.
     */
    private MainPanel mainPanel;
    /**
     * The x and y coordinates of the mouse cursor.
     */
    private int cursorX, cursorY;

    /**
     * Class constructor from an {@link MainPanel} object.
     *
     * @param mainPanel the {@link MainPanel} object.
     */
    public AsciiPanelMouseMotionListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        updateMousePosition(e);
        if (mainPanel.getCurrentButtonPressed() == 1) {

            cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
            cursorY = mainPanel.getAsciiPanel().getMouseCursorY();
            int x1 = mainPanel.getAsciiPanelMouseListener().getInitialCursorX();
            int y1 = mainPanel.getAsciiPanelMouseListener().getInitialCursorY();
            int pointDistance = (int) Math.round(Math.sqrt(Math.pow((cursorX - x1), 2) + Math.pow((cursorY - y1), 2)));

            Command currentCommand = mainPanel.getCommand();
            if (currentCommand instanceof FillCommand)
                mainPanel.setCommand(new FillCommand(cursorX, cursorY));
            else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand());
            else if (currentCommand instanceof PaintCommand)
                mainPanel.setCommand(new PaintCommand(cursorX, cursorY));
            else if (currentCommand instanceof EraseCommand)
                mainPanel.setCommand(new EraseCommand(cursorX, cursorY));

            if ((currentCommand instanceof FillCommand || currentCommand instanceof PickCommand || currentCommand instanceof PaintCommand) || currentCommand instanceof EraseCommand) {
                mainPanel.executeCommand();
            } else {
                //Solves a bug where the shape couldn't be drawn twice
                Command previousCommand = mainPanel.getCommandStack().peek();
                if (mainPanel.isDrag() && !(currentCommand instanceof PasteCommand)) {
                    mainPanel.getCommandStack().pop();
                    previousCommand.undo();
                }

                if (currentCommand instanceof SquareCommand)
                    mainPanel.setCommand(new SquareCommand(x1, y1, pointDistance));
                else if (currentCommand instanceof CircleCommand)
                    mainPanel.setCommand(new CircleCommand(pointDistance));
                else if (currentCommand instanceof SelectCommand)
                    mainPanel.setCommand(new SelectCommand(x1, y1, cursorX, cursorY));
                else if (currentCommand instanceof RectCommand)
                    mainPanel.setCommand(new RectCommand(x1, y1, cursorX, cursorY));
//                else if (currentCommand instanceof PasteCommand)
//                    mainPanel.setCommand(new PasteCommand(cursorX, cursorY));

                mainPanel.getCommand().execute();
                mainPanel.setDrag(true);

            }
            mainPanel.getAsciiPanel().repaint();
        }
    }

    /**
     * {@inheritDoc}
     * Triggers the method that updates the mouse coordinates when it moves.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    /**
     * Updates the mouse cursor position
     *
     * @param e the MouseEvent
     */
    public void updateMousePosition(MouseEvent e) {
        mainPanel.getAsciiPanel().setMouseCursorX(e.getX() / 16);
        mainPanel.getAsciiPanel().setMouseCursorY(e.getY() / 16);
        mainPanel.getAsciiPanel().repaint();
    }

}
