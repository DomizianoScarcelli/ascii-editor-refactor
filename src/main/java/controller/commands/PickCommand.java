package controller.commands;

import controller.ToolsPanelController;
import view.MainPanel;


/**
 * The action performed after the "Pick" button is pressed by the mouse and a character on the panel is selected.
 * When this happens the character, with his foreground and background colors, is set as the selected character
 * and the char preview panel is updated to show the selected character.
 */
public class PickCommand implements Command{
    MainPanel mainPanel;

    public PickCommand(MainPanel mainPanel, int button) {
        this.mainPanel = mainPanel;
    }

    @Override
    public void execute() {
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
        mainPanel.setCurrentToolId(0);
        toolsPanelController.updatePreview();
    }
}
