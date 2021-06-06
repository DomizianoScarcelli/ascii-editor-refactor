package controller;



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
     *
     * @param mainPanel the {@link MainPanel} object.
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
        switch(mainPanel.getCurrentToolId()){
            case 0:
                this.onPaint(e.getButton());
                break;
            case 1:
                this.onPick(e.getButton());
                break;
            case 2:
                this.onFill(e.getButton());
                break;
            default:
                break;
        }

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

    public void onPaint(int button){
        System.out.println("Painted");
    }
    public void onPick(int button){
        System.out.println("Picked");
    }
    public void onFill(int button){
        System.out.println("Filled");

    }

}
