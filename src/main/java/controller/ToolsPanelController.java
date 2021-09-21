package controller;

import model.AsciiFont;
import model.AsciiPanel;
import view.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ToolsPanelController {
    /**
     * The unique instance of the class
     */
    private static ToolsPanelController instance;
    /**
     * The MainPanel instance
     */
    private static MainPanel mainPanel = MainPanel.getInstance();

    /**
     * Gets the unique instance of the class if existing, creates it otherwise
     *
     * @return the unique instance of the class
     */
    public static ToolsPanelController getInstance() {
        if (instance == null) instance = new ToolsPanelController();
        return instance;
    }

    private ToolsPanelController() {
    }

    /**
     * Clears the character preview panel and updates the component three UI
     */
    public void updatePreview() {
        mainPanel.getCharPreviewPanel().clear();
        mainPanel.getCharPreviewPanel().write((char) mainPanel.getSelectedChar(), 1, 1, mainPanel.getDefaultForegroundColor(), mainPanel.getDefaultBackgroundColor());
        SwingUtilities.updateComponentTreeUI(mainPanel); //Prevents a weird bug when a character was selected
    }

    /**
     * Changes the button's color to notify that it's selected
     */
    public static void selectButton(JButton button) {
        mainPanel.getToolButtonList().forEach(b -> b.setBackground(Color.WHITE));
        button.setBackground(Color.GRAY);
    }

    /**
     * Resets the ascii panel
     *
     * @param sx the new ascii panel x size
     * @param sy the new ascii panel y size
     */
    public static void reset(int sx, int sy) {
        mainPanel.getMainContainer().remove(mainPanel.getAsciiPanel());
        mainPanel.setAsciiPanel(new AsciiPanel(sx, sy, AsciiFont.CP437_16x16));
        mainPanel.getMainContainer().add((mainPanel.getAsciiPanel()), BorderLayout.CENTER);
        mainPanel.getAsciiPanel().clear();
        mainPanel.getAsciiPanel().setCursorX(0);
        mainPanel.getAsciiPanel().setCursorY(0);
        mainPanel.getAsciiPanel().write("Empty");
        mainPanel.getAsciiPanel().addMouseMotionListener(new AsciiPanelMouseMotionListener(mainPanel));
        SwingUtilities.updateComponentTreeUI(mainPanel); //Prevents a weird bug when a character was selected
    }

    /**
     * Clones the MainPanel's ascii panel's character grid
     *
     * @return the cloned char matrix
     */
    public static char[][] copyCharGrid() {
        return Arrays.stream(mainPanel.getAsciiPanel().getChars()).map(char[]::clone).toArray(char[][]::new);
    }

    /**
     * Clones the MainPanel's ascii panel's old background color grid
     *
     * @return the cloned color matrix
     */
    public static Color[][] copyBCGrid() {
        return Arrays.stream(mainPanel.getAsciiPanel().getOldBackgroundColors()).map(Color[]::clone).toArray(Color[][]::new);
    }

    /**
     * Clones the MainPanel's ascii panel's old foreground color grid
     *
     * @return the cloned color matrix
     */
    public static Color[][] copyFCGrid() {
        return Arrays.stream(mainPanel.getAsciiPanel().getOldForegroundColors()).map(Color[]::clone).toArray(Color[][]::new);
    }

}
