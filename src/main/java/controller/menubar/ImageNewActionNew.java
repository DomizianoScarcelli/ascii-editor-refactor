package controller.menubar;

import com.sun.tools.javac.Main;
import controller.AsciiPanelMouseMotionListener;
import controller.ToolsPanelController;
import model.AsciiFont;
import model.AsciiPanel;
import model.CommandStack;
import view.ImageNewDialog;
import view.MainPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        MainPanel.getInstance().getAsciiPanel().clear();

//        MainPanel mainPanel = MainPanel.getInstance();
//        ImageNewDialog imageNew = ImageNewDialog.getInstance();
//        try {
//            int width = Integer.parseInt(imageNew.getWidthSetter().getText());
//            int height = Integer.parseInt(imageNew.getHeightSetter().getText());
//            ToolsPanelController.reset(width, height);
//        mainPanel.repaint();
//        imageNew.setVisible(false);
//            mainPanel.setCommandStack(new CommandStack());
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
    }
}
