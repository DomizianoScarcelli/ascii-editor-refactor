package controller.menubar;

import controller.ToolsPanelController;
import view.ImageNewDialog;
import view.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO risolvi bug altezza massima nuovo pannello

/**
 * Models the action of creating a new empty ascii panel.
 */
public class ImageNewActionNew implements ActionListener {
    /**
     * {@inheritDoc}
     * It creates a new empty AsciiPanel and overwrites the current one.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        MainPanel mainPanel = MainPanel.getInstance();
        ImageNewDialog imageNew = ImageNewDialog.getInstance();
        try {
            int width = Integer.parseInt(imageNew.getWidthSetter().getText());
            int height = Integer.parseInt(imageNew.getHeightSetter().getText());
            ToolsPanelController.reset(width, height);
            mainPanel.repaint();
            imageNew.setVisible(false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
