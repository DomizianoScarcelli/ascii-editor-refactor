package controller.menubar;

import controller.ToolsPanelController;
import model.AsciiPanel;
import model.AsciiRaster;
import view.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action of loading a previously saved ascii panel from the filesystem.
 */
public class MenuBarActionLoad implements ActionListener {
    /**
     * The panel where the ascii character are shown.
     */
    private AsciiPanel asciiPanel = new AsciiPanel();

    /**
     * {@inheritDoc}
     * It loads a previously saved {@link AsciiPanel} from the filesystem into the current {@link AsciiPanel}.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("resources/");
        int returnVal = fileChooser.showOpenDialog(asciiPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            AsciiRaster asciiRaster = AsciiRaster.fromFile(fileChooser.getSelectedFile().getAbsolutePath());
            ToolsPanelController.reset(asciiRaster.getRasterHeight(), asciiRaster.getRasterWidth());
            MainPanel.getInstance().getAsciiPanel().paintRaster(asciiRaster, 0, 0, true);
        }
    }
}
