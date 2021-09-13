package controller;

import controller.commands.*;
import controller.commands.copycutpaste.MoveCommandAlt;
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
    private int middleCursorX, middleCursorY;

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
        /*
        TODO modifica l'undo nel mouse dragged facendo in modo che l'azione sia il tratto tracciato e non il singolo carattere
        Un modo per farlo è quello di inserire nello stack delle chiamate non il singolo comando ma una lista di comandi. In caso il comando è stato draggato
        tale lista sarà maggiore di 1, altrimenti, in caso di comando singolo, nella lista sarà presente un solo elemento. Nel momento dell'undo viene effettuato l'undo
        per tutti gli elementi all'interno della lista dei comandi.
         */
        updateMousePosition(e);

        middleCursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        middleCursorY = mainPanel.getAsciiPanel().getMouseCursorY();
        int x1 = mainPanel.getAsciiPanelMouseListener().getInitialCursorX();
        int y1 = mainPanel.getAsciiPanelMouseListener().getInitialCursorY();
        int pointDistance = (int) Math.round(Math.sqrt(Math.pow((middleCursorX - x1), 2) + Math.pow((middleCursorY - y1), 2)));

        Command currentCommand = mainPanel.getCommand();
        if (currentCommand instanceof FillCommand)
            mainPanel.setCommand(new FillCommand(mainPanel, mainPanel.getAsciiPanel().getMouseCursorX(), mainPanel.getAsciiPanel().getMouseCursorY()));
        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
        else if (currentCommand instanceof PaintCommand)
            mainPanel.setCommand(new PaintCommand(mainPanel, mainPanel.getAsciiPanel().getMouseCursorX(), mainPanel.getAsciiPanel().getMouseCursorY()));

        if ((currentCommand instanceof FillCommand || currentCommand instanceof PickCommand || currentCommand instanceof PaintCommand) || currentCommand instanceof EraseCommand) {
            mainPanel.executeCommand();
        } else {
            //Solves a bug where the shape couldn't be drawn twice
            //TODO sta cosa però non risolve niente per quanto riguarda il Paste e il Move
            Command previousCommand = mainPanel.getCommandStack().peek();
            if (mainPanel.isDrag() && !(currentCommand instanceof PasteCommand)) {
                mainPanel.getCommandStack().pop();
                previousCommand.undo();
            }

            if (currentCommand instanceof SquareCommand)
                mainPanel.setCommand(new SquareCommand((int) Math.round(pointDistance / Math.sqrt(2)) + 1));
            else if (currentCommand instanceof CircleCommand)
                mainPanel.setCommand(new CircleCommand(pointDistance));
            else if (currentCommand instanceof SelectCommand)
                mainPanel.setCommand(new SelectCommand(x1, y1, middleCursorX, middleCursorY));
            else if (currentCommand instanceof RectCommand)
                mainPanel.setCommand(new RectCommand(x1, y1, middleCursorX, middleCursorY));
            else if (currentCommand instanceof MoveCommandAlt)
                mainPanel.setCommand(new MoveCommandAlt(middleCursorX, middleCursorY));
            else if (currentCommand instanceof PasteCommand)
                mainPanel.setCommand(new PasteCommand(middleCursorX, middleCursorY));

            mainPanel.getCommand().execute();
            mainPanel.setDrag(true);

        }
        mainPanel.getAsciiPanel().repaint();
    }

    /**
     * {@inheritDoc}
     * Triggers the method that updates the mouse coordinates when it moves.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        updateMousePosition(e);
    }

    public void updateMousePosition(MouseEvent e) {
        mainPanel.getAsciiPanel().setMouseCursorX(e.getX() / 16);
        mainPanel.getAsciiPanel().setMouseCursorY(e.getY() / 16);
        mainPanel.getAsciiPanel().repaint();
    }

}
