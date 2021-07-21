package view;

import controller.commands.copycutpaste.CopyCommand;
import controller.commands.copycutpaste.CutCommand;
import controller.commands.copycutpaste.PasteCommand;

import javax.swing.*;
import java.util.ArrayList;


public class RightClickMenu extends JPopupMenu {
    private ArrayList<int[]> copiedChars;
    private int rightClickMouseCursorX;
    private int rightClickMouseCursorY;

    private static RightClickMenu instance;

    public static RightClickMenu getInstance() {
        if (instance == null) instance = new RightClickMenu();
        return instance;
    }

    private RightClickMenu() {
        JMenuItem copyButton = new JMenuItem("Copy");
        JMenuItem cutButton = new JMenuItem("Cut");
        JMenuItem pasteButton = new JMenuItem("Paste");
        add(copyButton);
        add(cutButton);
        add(pasteButton);

        copyButton.addActionListener(e -> {
            CopyCommand copyCommand = new CopyCommand();
            copyCommand.execute();

        });

        cutButton.addActionListener(e -> {
            CutCommand cutCommand = new CutCommand();
            cutCommand.execute();
        });

        pasteButton.addActionListener(e -> {
            PasteCommand pasteCommand = new PasteCommand();
            pasteCommand.execute();
        });
    }


    public ArrayList<int[]> getCopiedChars() {
        return copiedChars;
    }

    public void setCopiedChars(ArrayList<int[]> copiedChars) {
        this.copiedChars = copiedChars;
    }

    public int getRightClickMouseCursorX() {
        return rightClickMouseCursorX;
    }

    public void setRightClickMouseCursorX(int rightClickMouseCursorX) {
        this.rightClickMouseCursorX = rightClickMouseCursorX;
    }

    public int getRightClickMouseCursorY() {
        return rightClickMouseCursorY;
    }

    public void setRightClickMouseCursorY(int rightClickMouseCursorY) {
        this.rightClickMouseCursorY = rightClickMouseCursorY;
    }
}
