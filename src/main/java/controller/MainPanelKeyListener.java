package controller;

import controller.commands.Command;
import controller.commands.copycutpaste.CopyCommand;
import controller.commands.copycutpaste.PasteCommand;
import view.MainPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainPanelKeyListener implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Marcoligno ");
        //Detect CTRL+Z and undoes the last command
        if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            Command command = MainPanel.getInstance().getCommandStack().pop();
            command.undo();
        }
        if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            new CopyCommand().execute();
        }
        if ((e.getKeyCode() == KeyEvent.VK_P) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            new PasteCommand(MainPanel.getInstance().getAsciiPanel().getMouseCursorX(), MainPanel.getInstance().getAsciiPanel().getMouseCursorY()).execute();
        }
        //TODO detect CTRL+SHIFT+Z and redo the last command

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
