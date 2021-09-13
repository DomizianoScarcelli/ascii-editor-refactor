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
        //Detect CTRL+SHIFT+Z and redoes the last command
        int down = KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;
        if ((e.getModifiersEx() & down) == down && (e.getKeyCode() == KeyEvent.VK_Z)) {
            System.out.printf("Redo");
            Command command = MainPanel.getInstance().getRedoCommandStack().pop();
            command.execute();
            MainPanel.getInstance().getCommandStack().push(command);
        }
        //Detect CTRL+Z and undoes the last command
        else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            System.out.println("Undo");
            Command command = MainPanel.getInstance().getCommandStack().pop();
            command.undo();
            MainPanel.getInstance().getRedoCommandStack().push(command);
        }

        if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            System.out.println("Copiato");
            new CopyCommand().execute();
        }
        if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
            System.out.println("Incollato");
            new PasteCommand(MainPanel.getInstance().getAsciiPanel().getMouseCursorX(), MainPanel.getInstance().getAsciiPanel().getMouseCursorY()).execute();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
