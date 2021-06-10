package controller.menubar;

import view.ImageImporterDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBarActionImport implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageImporterDialog.getInstance().setVisible(true);
    }
}
