package controller;

import controller.commands.Command;
import view.MainPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainPanelKeyListener implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Detect CTRL+Z and undoes the last command
        if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            Command command = MainPanel.getInstance().getCommandStack().pop();
            command.undo();
        }
        //TODO detect CTRL+SHIFT+Z and redoes the last command

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
