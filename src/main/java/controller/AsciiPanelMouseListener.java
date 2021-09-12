package controller;


import controller.commands.*;
import controller.commands.copycutpaste.MoveCommand;
import controller.commands.copycutpaste.PasteCommand;
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
        if (currentCommand instanceof FillCommand)
            mainPanel.setCommand(new FillCommand(mainPanel, mainPanel.getAsciiPanel().getMouseCursorX(), mainPanel.getAsciiPanel().getMouseCursorY()));
        else if (currentCommand instanceof PickCommand) mainPanel.setCommand(new PickCommand(mainPanel));
        else if (currentCommand instanceof PaintCommand)
            mainPanel.setCommand(new PaintCommand(mainPanel, mainPanel.getAsciiPanel().getMouseCursorX(), mainPanel.getAsciiPanel().getMouseCursorY()));
        else if (currentCommand instanceof PasteCommand) {
            int x = mainPanel.getAsciiPanel().getMouseCursorX();
            int y = mainPanel.getAsciiPanel().getMouseCursorY();
            mainPanel.setCommand(new PasteCommand(x, y));
        }

        if ((currentCommand instanceof FillCommand || currentCommand instanceof PickCommand || currentCommand instanceof PaintCommand || currentCommand instanceof EraseCommand || currentCommand instanceof PasteCommand)) {
            mainPanel.executeCommand();
            mainPanel.getAsciiPanel().repaint();
        }


    }


    @Override
    public void mouseReleased(MouseEvent e) {
        mainPanel.isDrag = false;
        if (e.isPopupTrigger())
            doPop(e);

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
