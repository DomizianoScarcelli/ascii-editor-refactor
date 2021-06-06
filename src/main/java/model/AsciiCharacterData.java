package model;

import java.awt.Color;

/**
 * Models the data, such as colors and characters, that will be displayed on the panel.
 */
public class AsciiCharacterData {
    private char character;
    private Color foregroundColor;
    private Color backgroundColor;

    /**
     * The actual character where the focus is on.
     */
    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    /**
     * The character color.
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    /**
     * The background color the square tile where the character is in.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
