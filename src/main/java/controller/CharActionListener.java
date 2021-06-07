package controller;

import view.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharActionListener implements ActionListener {
    /**
     * The character on which the action is performed
     */
    private int selectedChar;

    /**
     * Class constructor from a character.
     *
     * @param selectedChar the character.
     */
    public CharActionListener(int selectedChar) {
        this.selectedChar = selectedChar;
    }

    /**
     * {@inheritDoc}
     * Triggers the action that has to be done on the selected char i.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Selected char: " + (char) selectedChar);
        MainPanel.getInstance().setSelectedChar(selectedChar);
        MainPanel.getInstance().getSelectCharButton().setText(selectedChar + "");
//        MainPanel.getInstance().updatePreview();
    }
}
