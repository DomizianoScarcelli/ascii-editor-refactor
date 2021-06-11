package controller;

import view.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviousCharacterActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedChar = MainPanel.getInstance().getSelectedChar();
        if (selectedChar > 0){
            selectedChar--;
            MainPanel.getInstance().setSelectedChar((selectedChar));
            ToolsPanelController.getInstance().updatePreview();
        }

    }
}
