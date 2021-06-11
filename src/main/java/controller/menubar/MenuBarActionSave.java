package controller.menubar;

import view.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action of saving the current ascii panel on the local filesystem.
 */
public class MenuBarActionSave implements ActionListener {
    /**
     * {@inheritDoc}
     * It saves the current AsciiPanel to a selected location of the local filesystem.
     * The default location is "/resources"
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("resources/");
        int returnVal = fileChooser.showSaveDialog(MainPanel.getInstance());
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            MainPanel.getInstance().getAsciiPanel().save(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }
}