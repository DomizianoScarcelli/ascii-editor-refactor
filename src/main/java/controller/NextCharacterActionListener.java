package controller;

import view.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action listener used to trigger an action when the user clicks on the next character button
 */
public class NextCharacterActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedChar = MainPanel.getInstance().getSelectedChar();
        if (selectedChar < 255) {
            selectedChar++;
            MainPanel.getInstance().setSelectedChar((selectedChar));
            ToolsPanelController.getInstance().updatePreview();
        }

    }
}
