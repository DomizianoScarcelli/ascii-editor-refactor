package controller;


import controller.commands.*;
import controller.commands.shapes.CircleCommand;
import controller.commands.shapes.SquareCommand;
import view.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Models the actions performed on the panel by the mouse when it's not moving.
 */
public class AsciiPanelMouseListener implements MouseListener {
    /**
     * The {@link MainPanel} object that allows to edit the image on the ascii panel when an action is performed.
     */
    private MainPanel mainPanel;

    /**
     * Class constructor from an {@link MainPanel} object.
     */
    public AsciiPanelMouseListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
//        mainPanel.setCurrentButtonPressed(e.getButton());
//        Command currentCommand = mainPanel.getCommand();
//        if (currentCommand instanceof FillCommand) mainPanel.setCommand(new FillCommand(mainPanel));
//        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
//        else if (currentCommand instanceof PaintCommand) {
////            ((PaintCommand) currentCommand).setPreviousCommand(null);
//            PaintCommand newPaintCommand = new PaintCommand(mainPanel);
//            newPaintCommand.setPreviousCommand(currentCommand);
//            mainPanel.setCommand(newPaintCommand);
//        }
//
//
//        mainPanel.executeCommand();
    }

    /**
     * {@inheritDoc}
     * Triggers the specific action of the currently selected tool.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        mainPanel.setCurrentButtonPressed(e.getButton());
        Command currentCommand = mainPanel.getCommand();
        if (currentCommand instanceof FillCommand) mainPanel.setCommand(new FillCommand(mainPanel));
        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
        else if (currentCommand instanceof PaintCommand) {
//            ((PaintCommand) currentCommand).setPreviousCommand(null);
            PaintCommand newPaintCommand = new PaintCommand(mainPanel);
            newPaintCommand.setPreviousCommand(currentCommand);
            mainPanel.setCommand(newPaintCommand);
        }
        else if (currentCommand instanceof SquareCommand) mainPanel.setCommand(new SquareCommand(mainPanel.getShapeDimension().getValue()));
        else if (currentCommand instanceof CircleCommand) mainPanel.setCommand(new CircleCommand(mainPanel.getShapeDimension().getValue()));


        mainPanel.executeCommand();
        mainPanel.getAsciiPanel().repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }

}
