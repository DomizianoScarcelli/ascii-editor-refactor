package view;

import javax.swing.*;
import java.util.Observable;

//TODO sta roba servivebbe per cambiare i colori dei bottoni in maniera più intelligente ed efficiente
public class ToolButtonView extends Observable {

    private JButton button;
    private boolean isSelected;

    public ToolButtonView(JButton button) {
        this.button = button;
        this.isSelected = false;
    }

    public void setSelected() {
        isSelected = true;
    }

    public JButton getButton() {
        return button;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
