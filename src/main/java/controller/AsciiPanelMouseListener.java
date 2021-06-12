package controller;


import controller.commands.FillCommand;
import controller.commands.PickCommand;
import controller.commands.PaintCommand;
import view.MainPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Models the actions performed on the panel by the mouse when it's not moving.
 */
public class AsciiPanelMouseListener implements MouseListener {
    /**
     * The {@link MainPanel} object that allows to edit the image on the ascii panel when an action is performed.
     */
    private MainPanel mainPanel;

    /**
     * Class constructor from an {@link MainPanel} object.
     */
    public AsciiPanelMouseListener(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * {@inheritDoc}
     * Triggers the specific action of the currently selected tool.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(mainPanel.getCurrentToolId());
        switch (mainPanel.getCurrentToolId()) {
            case 0 -> { //current tool is paint
                PaintCommand PaintCommand = new PaintCommand(mainPanel, e.getButton());
                PaintCommand.execute();
            }
            case 1 -> { //current tool is mainPanel.getPick()
                PickCommand pickCommand = new PickCommand(mainPanel, e.getButton());
                pickCommand.execute();
            }
            case 2 -> { //current tool is fill
                FillCommand fillCommand = new FillCommand(mainPanel, e.getButton());
                fillCommand.execute();
            }
        }
        ToolsPanelController.updateSelectedToolButtonBackground();

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
