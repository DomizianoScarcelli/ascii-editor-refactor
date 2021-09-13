package controller;

import view.ImageNewDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action listener used to trigger an action when the user clicks on the "New" menu
 */
public class MenuBarActionNew implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageNewDialog.getInstance().setVisible(true);
    }
}
