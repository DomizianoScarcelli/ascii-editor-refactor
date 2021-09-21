package controller.menubar;

import view.MainPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models the action of creating a new empty ascii panel.
 */
public class ImageNewActionNew implements ActionListener {
    /**
     * {@inheritDoc}
     * It creates a new empty AsciiPanel and overwrites the current one.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainPanel.getInstance().getAsciiPanel().clear();

    }
}
