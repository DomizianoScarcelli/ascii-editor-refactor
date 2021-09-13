package controller;

import view.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CharacterSelectorActionListener implements MouseListener {
    /**
     * The character on which the action is performed
     */
    private int selectedChar;

    /**
     * Class constructor from a character.
     *
     * @param selectedChar the character.
     */
    public CharacterSelectorActionListener(int selectedChar) {
        this.selectedChar = selectedChar;
    }

    /**
     * {@inheritDoc}
     * Sets the selected character as the current one and update the selected character preview.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Selected char: " + (char) selectedChar);
        MainPanel.getInstance().setSelectedChar(selectedChar);
        ToolsPanelController.getInstance().updatePreview();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
