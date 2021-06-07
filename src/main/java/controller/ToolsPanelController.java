package controller;

import model.AsciiPanel;
import view.MainPanel;

import java.awt.*;

public class ToolsPanelController {
    private static ToolsPanelController instance;

    private MainPanel mainPanel = MainPanel.getInstance();

    public static ToolsPanelController getInstance() {
        if (instance == null) instance = new ToolsPanelController();
        return instance;
    }

    public void updatePreview(){
        mainPanel.getCharPreviewPanel().clear();
        mainPanel.getCharPreviewPanel().write((char) mainPanel.getSelectedChar(), 1, 1, Color.WHITE);
        mainPanel.getCharPreviewPanel().repaint();
    }
}
