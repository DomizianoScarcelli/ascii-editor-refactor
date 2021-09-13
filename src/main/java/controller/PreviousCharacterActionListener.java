package controller;

import view.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action listener used to trigger an action when the user clicks on the previous character button
 */
public class PreviousCharacterActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedChar = MainPanel.getInstance().getSelectedChar();
        if (selectedChar > 0) {
            selectedChar--;
            MainPanel.getInstance().setSelectedChar((selectedChar));
            ToolsPanelController.getInstance().updatePreview();
        }

    }
}
