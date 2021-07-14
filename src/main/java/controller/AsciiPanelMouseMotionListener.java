package controller;



import controller.commands.*;
import controller.commands.shapes.CircleCommand;
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
     * Class constructor from an {@link MainPanel} object.
     *
     * @param mainPanel the {@link MainPanel} object.
     */
    public AsciiPanelMouseMotionListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        //TODO modifica l'undo nel mouse dragged facendo in modo che l'azione sia il tratto tracciato e non il singolo carattere
        updateMousePosition(e);
        Command currentCommand = mainPanel.getCommand();
        if (currentCommand instanceof FillCommand) mainPanel.setCommand(new FillCommand(mainPanel));
        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
        else if (currentCommand instanceof PaintCommand) mainPanel.setCommand(new PaintCommand(mainPanel));
        else if (currentCommand instanceof SquareCommand) mainPanel.setCommand(new SquareCommand(mainPanel.getShapeDimension().getValue()));
        else if (currentCommand instanceof CircleCommand) mainPanel.setCommand(new CircleCommand(mainPanel.getShapeDimension().getValue()));
        mainPanel.executeCommand();
    }

    /**
     * {@inheritDoc}
     * Triggers the method that updates the mouse coordinates when it moves.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    public void updateMousePosition(MouseEvent e){
        mainPanel.getAsciiPanel().setMouseCursorX(e.getX() / 16);
        mainPanel.getAsciiPanel().setMouseCursorY(e.getY() / 16);
        mainPanel.getAsciiPanel().repaint();
    }

}
