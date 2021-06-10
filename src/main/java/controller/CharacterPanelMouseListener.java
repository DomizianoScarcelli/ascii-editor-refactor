package controller;

import view.CharacterSelector;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CharacterPanelMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        CharacterSelector.getInstance().setVisible(true);
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
