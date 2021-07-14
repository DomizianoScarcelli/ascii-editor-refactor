package controller;

import controller.commands.shapes.SquareCommand;
import view.MainPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ShapeSliderChangeListener implements ChangeListener {

    private MainPanel mainPanel;

    public ShapeSliderChangeListener(MainPanel mainpanel){
        this.mainPanel = mainpanel;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        mainPanel.setCommand(new SquareCommand(slider.getValue()));
        System.out.println(slider.getValue());

    }
}
