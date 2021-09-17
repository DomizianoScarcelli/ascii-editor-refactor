package view;

import controller.commands.copycutpaste.ClearCommand;
import controller.commands.copycutpaste.CopyCommand;
import controller.commands.copycutpaste.CutCommand;
import controller.commands.copycutpaste.PasteCommand;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The menu that pops up when the mouse right click is clicked.
 */
public class RightClickMenu extends JPopupMenu {
    /**
     * The list of points that indicates the coordinates of the copied characters.
     */
    private ArrayList<int[]> copiedChars;
    /**
     * The mouse x coordinate when the right click is clicked
     */
    private int rightClickMouseCursorX;
    /**
     * The mouse y coordinate when the right click is clicked
     */
    private int rightClickMouseCursorY;
    /**
     * The unique instance of the class
     */
    private static RightClickMenu instance;

    /**
     * Gets the unique instance of the class if existing, creates it otherwise.
     *
     * @return
     */
    public static RightClickMenu getInstance() {
        if (instance == null) instance = new RightClickMenu();
        return instance;
    }

    private RightClickMenu() {
        JMenuItem deselectButton = new JMenuItem("Deselect");
        JMenuItem clearButton = new JMenuItem("Clear");
        JMenuItem copyButton = new JMenuItem("Copy");
        JMenuItem cutButton = new JMenuItem("Cut");
        JMenuItem pasteButton = new JMenuItem("Paste");

        add(deselectButton);
        add(clearButton);
        add(copyButton);
        add(cutButton);
        add(pasteButton);

        deselectButton.addActionListener(e -> {
            MainPanel.getInstance().getCurrentSelection().undo();
        });
        clearButton.addActionListener(e -> {
            new ClearCommand().execute();
            MainPanel.getInstance().getCurrentSelection().undo();
        });
        copyButton.addActionListener(e -> {
            new CopyCommand().execute();
            MainPanel.getInstance().getCurrentSelection().undo();
        });

        cutButton.addActionListener(e -> {
            new CutCommand().execute();
            MainPanel.getInstance().getCurrentSelection().undo();
        });

        pasteButton.addActionListener(e -> {
            int x = RightClickMenu.getInstance().getRightClickMouseCursorX();
            int y = RightClickMenu.getInstance().getRightClickMouseCursorY();
            new PasteCommand(x, y).execute();
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
