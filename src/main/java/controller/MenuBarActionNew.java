package controller;

import view.ImageNewDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarActionNew implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageNewDialog.getInstance().setVisible(true);
    }
}
