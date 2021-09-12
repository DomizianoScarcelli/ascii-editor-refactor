package controller;

import com.sun.tools.javac.Main;
import model.AsciiFont;
import model.AsciiPanel;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;


public class ToolsPanelController {
    private static ToolsPanelController instance;

    private static MainPanel mainPanel = MainPanel.getInstance();

    public static ToolsPanelController getInstance() {
        if (instance == null) instance = new ToolsPanelController();
        return instance;
    }

    private ToolsPanelController() {
    }

    public void updatePreview() {
        mainPanel.getCharPreviewPanel().clear();
        mainPanel.getCharPreviewPanel().write((char) mainPanel.getSelectedChar(), 1, 1, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        SwingUtilities.updateComponentTreeUI(mainPanel); //Prevents a weird bug when a character was selected
    }


    public static void selectButton(JButton button) {
        mainPanel.toolButtonList.forEach(b -> b.setBackground(Color.WHITE));
        button.setBackground(Color.GRAY);
    }

    public static void reset(int sx, int sy) {
        mainPanel.getMainContainer().remove(mainPanel.getAsciiPanel());
        mainPanel.setAsciiPanel(new AsciiPanel(sx, sy, AsciiFont.CP437_16x16));
        mainPanel.getMainContainer().add((mainPanel.getAsciiPanel()), BorderLayout.CENTER);
        mainPanel.getAsciiPanel().clear();
        mainPanel.getAsciiPanel().setCursorX(0);
        mainPanel.getAsciiPanel().setCursorY(0);
        mainPanel.getAsciiPanel().write("Empty");
        mainPanel.getAsciiPanel().addMouseMotionListener(new AsciiPanelMouseMotionListener(mainPanel));
        //TODO add all other things
        SwingUtilities.updateComponentTreeUI(mainPanel); //Prevents a weird bug when a character was selected
    }


    public static char[][] copyCharGrid() {
        char[][] oldCharGrid;
        char[][] currentChars = mainPanel.getAsciiPanel().getChars();
        int width = mainPanel.getAsciiPanel().getWidthInCharacters();
        int height = mainPanel.getAsciiPanel().getHeightInCharacters();
        oldCharGrid = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                oldCharGrid[x][y] = currentChars[x][y];
            }
        }
        return oldCharGrid;
    }

    public static Color[][] copyBCGrid() {
        Color[][] oldColorGrid;
        Color[][] currentColorGrid = mainPanel.getAsciiPanel().getOldBackgroundColors();
        int width = mainPanel.getAsciiPanel().getWidthInCharacters();
        int height = mainPanel.getAsciiPanel().getHeightInCharacters();
        oldColorGrid = new Color[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                oldColorGrid[x][y] = currentColorGrid[x][y];
            }
        }
        return oldColorGrid;
    }

    public static Color[][] copyFCGrid() {
        Color[][] oldColorGrid;
        Color[][] currentColorGrid = mainPanel.getAsciiPanel().getOldForegroundColors();
        int width = mainPanel.getAsciiPanel().getWidthInCharacters();
        int height = mainPanel.getAsciiPanel().getHeightInCharacters();
        oldColorGrid = new Color[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                oldColorGrid[x][y] = currentColorGrid[x][y];
            }
        }
        return oldColorGrid;
    }


}
