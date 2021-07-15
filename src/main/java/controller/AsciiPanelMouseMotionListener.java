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
    private int middleCursorX, middleCursorY;;


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
        int pointDistance = (int) Math.round(Math.sqrt( Math.pow((middleCursorX-x1), 2) + Math.pow((middleCursorY-y1), 2)));

        Command currentCommand = mainPanel.getCommand();
        if (currentCommand instanceof FillCommand) mainPanel.setCommand(new FillCommand(mainPanel));
        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
        else if (currentCommand instanceof PaintCommand) mainPanel.setCommand(new PaintCommand(mainPanel));


        if (!(currentCommand instanceof  SquareCommand || currentCommand instanceof CircleCommand)){
            mainPanel.executeCommand();
        } else{
            Command previousCommand = mainPanel.getCommandStack().pop();
            previousCommand.undo();

            if (currentCommand instanceof SquareCommand) mainPanel.setCommand(new SquareCommand(pointDistance));
            else if (currentCommand instanceof CircleCommand) mainPanel.setCommand(new CircleCommand(pointDistance));

            mainPanel.getCommand().execute();



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

    public void updateMousePosition(MouseEvent e){
        mainPanel.getAsciiPanel().setMouseCursorX(e.getX() / 16);
        mainPanel.getAsciiPanel().setMouseCursorY(e.getY() / 16);
        mainPanel.getAsciiPanel().repaint();
    }

}
