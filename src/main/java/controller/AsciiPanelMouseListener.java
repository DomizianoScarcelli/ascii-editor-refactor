package controller;


import controller.commands.*;
import controller.commands.copycutpaste.MoveCommand;
import controller.commands.copycutpaste.SelectCommand;
import controller.commands.shapes.SquareCommand;
import view.MainPanel;
import view.RightClickMenu;

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
    private int initialCursorX;
    private int initialCursorY;


    /**
     * Class constructor from an {@link MainPanel} object.
     */
    public AsciiPanelMouseListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * {@inheritDoc}
     * Triggers the specific action of the currently selected tool.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
        //Update initial mouse cursor position
        initialCursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        initialCursorY = mainPanel.getAsciiPanel().getMouseCursorY();

        mainPanel.setCurrentButtonPressed(e.getButton());

        Command currentCommand = mainPanel.getCommand();
        if (currentCommand instanceof FillCommand) mainPanel.setCommand(new FillCommand(mainPanel));
        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
        else if (currentCommand instanceof PaintCommand) mainPanel.setCommand(new PaintCommand(mainPanel));

        if ((currentCommand instanceof FillCommand || currentCommand instanceof PickCommand || currentCommand instanceof PaintCommand || currentCommand instanceof EraseCommand)) {
            mainPanel.executeCommand();
            mainPanel.getAsciiPanel().repaint();
        }
//        if (!(currentCommand instanceof MoveCommand)) //TODO guarda quale classi togliere da sta cosa, recentemente ho aggiunto anche SelectCommand ma non so se è giusto
//            mainPanel.beforeSelectionGrid = mainPanel.getAsciiPanel().getChars(); //TODO


    }


    @Override
    public void mouseReleased(MouseEvent e) {
        mainPanel.isDrag = false;
        if (e.isPopupTrigger())
            doPop(e);


//        Command currentCommand = mainPanel.getCommand();
//        //Update final mouse cursor position
//        finalCursorX = mainPanel.getAsciiPanel().getMouseCursorX();
//        finalCursorY = mainPanel.getAsciiPanel().getMouseCursorY();
//
//        int x1 = initialCursorX;
//        int x2 = finalCursorX;
//        int y1 = initialCursorY;
//        int y2 = finalCursorY;
//
//        int pointDistance = (int) Math.round(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
//        mainPanel.getAsciiPanel().setCursorX(initialCursorX);
//        mainPanel.getAsciiPanel().setCursorY(initialCursorY);
//        if (currentCommand instanceof CircleCommand)
//            mainPanel.setCommand(new CircleCommand(pointDistance));
//        else if (currentCommand instanceof SquareCommand)
//            mainPanel.setCommand(new SquareCommand((int) Math.round(pointDistance / Math.sqrt(2)) + 1));
//        else if (currentCommand instanceof SelectCommand)
//            mainPanel.setCommand(new SelectCommand(initialCursorX, initialCursorY, finalCursorX, finalCursorY));
//        else if (currentCommand instanceof RectCommand)
//            mainPanel.setCommand(new RectCommand(initialCursorX, initialCursorY, finalCursorX, finalCursorY));
//        else if (currentCommand instanceof MoveCommand)
//            mainPanel.setCommand(new MoveCommand(initialCursorX, initialCursorY, finalCursorX, finalCursorY));
//
//        mainPanel.getCommand().execute();
//        mainPanel.getAsciiPanel().repaint();
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }


    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void doPop(MouseEvent e) {
        RightClickMenu menu = RightClickMenu.getInstance();
        menu.show(e.getComponent(), e.getX(), e.getY());
        menu.setRightClickMouseCursorX(mainPanel.getAsciiPanel().getMouseCursorX());
        menu.setRightClickMouseCursorY(mainPanel.getAsciiPanel().getMouseCursorY());
    }

    public int getInitialCursorX() {
        return initialCursorX;
    }


    public int getInitialCursorY() {
        return initialCursorY;
    }


}
