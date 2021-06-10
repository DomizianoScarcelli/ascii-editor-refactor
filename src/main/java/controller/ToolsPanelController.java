package controller;

import view.MainPanel;

import java.awt.*;


public class ToolsPanelController {
    private static ToolsPanelController instance;

    private static MainPanel mainPanel = MainPanel.getInstance();

    public static ToolsPanelController getInstance() {
        if (instance == null) instance = new ToolsPanelController();
        return instance;
    }

    private ToolsPanelController(){}

//    public ToolsPanelController(MainPanel mainPanel){
//        this.mainPanel = mainPanel;
//    }

    public void updatePreview() {
        mainPanel.getCharPreviewPanel().clear();
        mainPanel.getCharPreviewPanel().write((char) mainPanel.getSelectedChar(), 1, 1, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        mainPanel.getCharPreviewPanel().repaint();
    }

    public static void updateSelectedToolButtonBackground() {
        switch (mainPanel.getCurrentToolId()) {
            case 0: //current tool is paint
                mainPanel.getPaint().setBackground(Color.GRAY);
                mainPanel.getPick().setBackground(Color.WHITE);
                mainPanel.getFill().setBackground(Color.WHITE);
                break;
            case 1: //current tool is mainPanel.getPick()
                mainPanel.getPaint().setBackground(Color.WHITE);
                mainPanel.getPick().setBackground(Color.GRAY);
                mainPanel.getFill().setBackground(Color.WHITE);
                break;
            case 2: //current tool is fill
                mainPanel.getPaint().setBackground(Color.WHITE);
                mainPanel.getPick().setBackground(Color.WHITE);
                mainPanel.getFill().setBackground(Color.GRAY);
                break;
        }
    }


}
