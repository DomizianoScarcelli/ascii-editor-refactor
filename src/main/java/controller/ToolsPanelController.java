package controller;

import view.MainPanel;



public class ToolsPanelController {
    private static ToolsPanelController instance;

    private MainPanel mainPanel = MainPanel.getInstance();

    public static ToolsPanelController getInstance() {
        if (instance == null) instance = new ToolsPanelController();
        return instance;
    }

    public void updatePreview(){
        mainPanel.getCharPreviewPanel().clear();
//        mainPanel.getCharPreviewPanel().setDefaultBackgroundColor(mainPanel.getDefaultBackgroundColor());
        mainPanel.getCharPreviewPanel().write((char) mainPanel.getSelectedChar(), 1, 1, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        mainPanel.getCharPreviewPanel().repaint();
    }
}
