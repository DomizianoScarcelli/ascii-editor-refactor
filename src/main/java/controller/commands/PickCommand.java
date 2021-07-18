package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;

import java.awt.*;


/**
 * The action performed after the "Pick" button is pressed by the mouse and a character on the panel is selected.
 * When this happens the character, with his foreground and background colors, is set as the selected character
 * and the char preview panel is updated to show the selected character.
 */
public class PickCommand implements Command {
    private MainPanel mainPanel;

    private int oldSelectedChar;
    private Color oldBackground, oldForeground;

    public PickCommand(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void execute() {
        oldSelectedChar = mainPanel.getSelectedChar();
        oldBackground = mainPanel.getAsciiPanel().getDefaultBackgroundColor();
        oldForeground = mainPanel.getAsciiPanel().getDefaultForegroundColor();
        int cursorX = mainPanel.getAsciiPanel().getMouseCursorX();
        int cursorY = mainPanel.getAsciiPanel().getMouseCursorY();
        mainPanel.setSelectedChar(mainPanel.getAsciiPanel().pickChar(cursorX, cursorY));
        mainPanel.setDefaultForegroundColor(mainPanel.getAsciiPanel().pickFC(cursorX, cursorY));
        mainPanel.setDefaultBackgroundColor(mainPanel.getAsciiPanel().pickBC(cursorX, cursorY));
        mainPanel.getBackgroundColorPanel().setBackground(mainPanel.getDefaultBackgroundColor());
        mainPanel.getForegroundColorPanel().setBackground(mainPanel.getDefaultForegroundColor());
        mainPanel.getForegroundColorPanel().repaint();
        mainPanel.getBackgroundColorPanel().repaint();
        ToolsPanelController toolsPanelController = ToolsPanelController.getInstance();
        mainPanel.setCommand(new PaintCommand(mainPanel));
        toolsPanelController.updatePreview();

        mainPanel.getCommandStack().push(this);
        ToolsPanelController.selectPickButton();
    }

    @Override
    public void undo() {
        mainPanel.setSelectedChar(oldSelectedChar);
        mainPanel.setDefaultForegroundColor(oldForeground);
        mainPanel.setDefaultBackgroundColor(oldBackground);
        ToolsPanelController toolsPanelController = ToolsPanelController.getInstance();
        toolsPanelController.updatePreview();
    }
}
