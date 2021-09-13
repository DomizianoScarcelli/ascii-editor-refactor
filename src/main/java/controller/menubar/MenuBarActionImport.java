package controller.menubar;

import view.ImageImporterDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action listener used to trigger an action when the user clicks on the import menu
 */
public class MenuBarActionImport implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageImporterDialog.getInstance().setVisible(true);
    }
}
