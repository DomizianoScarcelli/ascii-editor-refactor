package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Models the two dimension array where the ascii characters will appear.
 * The panel is represented as a character matrix.
 */
public class AsciiPanel extends JPanel {
    private static final long serialVersionUID = -4167851861147593092L;
    /**
     * The color black (pure black).
     */
    public static final Color BLACK = new Color(0, 0, 0);
    /**
     * The color red.
     */
    public static final Color RED = new Color(128, 0, 0);
    /**
     * The color green.
     */
    public static final Color GREEN = new Color(0, 128, 0);
    /**
     * The color yellow.
     */
    public static final Color YELLOW = new Color(128, 128, 0);
    /**
     * The color blue.
     */
    public static final Color BLUE = new Color(0, 0, 128);
    /**
     * The color magenta.
     */
    public static final Color MAGENTA = new Color(128, 0, 128);
    /**
     * The color cyan.
     */
    public static final Color CYAN = new Color(0, 128, 128);
    /**
     * The color white (light gray).
     */
    public static final Color WHITE = new Color(192, 192, 192);
    /**
     * A brighter black (dark gray).
     */
    public static final Color BRIGHT_BLACK = new Color(128, 128, 128);
    /**
     * A brighter red.
     */
    public static final Color BRIGHT_RED = new Color(255, 0, 0);
    /**
     * A brighter green.
     */
    public static final Color BRIGHT_GREEN = new Color(0, 255, 0);
    /**
     * A brighter yellow.
     */
    public static final Color BRIGHT_YELLOW = new Color(255, 255, 0);
    /**
     * A brighter blue.
     */
    public static final Color BRIGHT_BLUE = new Color(0, 0, 255);
    /**
     * A brighter magenta.
     */
    public static final Color BRIGHT_MAGENTA = new Color(255, 0, 255);
    /**
     * A brighter cyan.
     */
    public static final Color BRIGHT_CYAN = new Color(0, 255, 255);
    /**
     * A brighter white (pure white).
     */
    public static final Color BRIGHT_WHITE = new Color(255, 255, 255);
    private Image offscreenBuffer;
    private Graphics offscreenGraphics;
    /**
     * {@link #getWidthInCharacters()}
     */
    private int widthInCharacters;
    /**
     * {@link #getHeightInCharacters()}
     */
    private int heightInCharacters;
    /**
     * {@link #getCharWidth()}
     */
    private int charWidth = 9;
    /**
     * {@link #getCharHeight()}
     */
    private int charHeight = 16;
    private String terminalFontFile = "cp437_9x16.png";
    /**
     * {@link #getDefaultBackgroundColor()}
     */
    private Color defaultBackgroundColor;
    /**
     * {@link #getDefaultForegroundColor()}
     */
    private Color defaultForegroundColor;
    /**
     * {@link #getCursorX()}
     */
    private int cursorX;
    /**
     * {@link #getCursorY()}
     */
    private int cursorY;
    private BufferedImage glyphSprite;
    /**
     * {@link #getGlyphs()}
     */
    private BufferedImage[] glyphs;
    /**
     * {@link #getChars()}
     */
    private char[][] chars;
    /**
     * {@link #getBackground()}
     */
    private Color[][] backgroundColors;
    /**
     * {@link #getForeground()}
     */
    private Color[][] foregroundColors;
    private char[][] oldChars;
    private Color[][] oldBackgroundColors;
    private Color[][] oldForegroundColors;
    private AsciiFont asciiFont;
    /**
     * {@link #getMouseCursorX()}
     * {@link #getMouseCursorY()}
     */
    private int mcursorx, mcursory;
    private int cursorID = 4;

    /**
     * Class constructor.
     * Default size is 80x24 characters.
     */
    public AsciiPanel() {
        this(80, 24);
    }

    /**
     * Class constructor specifying the width and height in characters.
     *
     * @param width  the width in characters.
     * @param height the height in characters.
     */
    public AsciiPanel(int width, int height) {
        this(width, height, null);
    }

    /**
     * Class constructor specifying the width and height in characters and the AsciiFont object that represent the used font.
     *
     * @param width  the width in characters.
     * @param height the height in characters.
     * @param font   the font. If passing null, standard font CP437_9x16 will be used
     */
    public AsciiPanel(int width, int height, AsciiFont font) {
        super();

        if (width < 1) {
            throw new IllegalArgumentException("width " + width + " must be greater than 0.");
        }

        if (height < 1) {
            throw new IllegalArgumentException("height " + height + " must be greater than 0.");
        }

        widthInCharacters = width;
        heightInCharacters = height;

        defaultBackgroundColor = BLACK;
        defaultForegroundColor = Color.white;

        setChars(new char[widthInCharacters][heightInCharacters]);
        backgroundColors = new Color[widthInCharacters][heightInCharacters];
        foregroundColors = new Color[widthInCharacters][heightInCharacters];

        oldBackgroundColors = new Color[widthInCharacters][heightInCharacters];
        oldForegroundColors = new Color[widthInCharacters][heightInCharacters];

        if (font == null) {
            font = AsciiFont.CP437_9x16;
        }
        setAsciiFont(font);
    }

    /**
     * A glyph is the buffered image representation of a character.
     *
     * @return the glyphs as a buffered image array.
     */
    public BufferedImage[] getGlyphs() {
        return glyphs;
    }

    /**
     * Gets the height, in pixels, of a character.
     *
     * @return height in pixels.
     */
    public int getCharHeight() {
        return charHeight;
    }

    /**
     * Gets the width, in pixels, of a character.
     *
     * @return width in pixels.
     */
    public int getCharWidth() {
        return charWidth;
    }

    /**
     * Gets the height in characters.
     * A standard terminal is 24 characters high.
     *
     * @return height in characters.
     */
    public int getHeightInCharacters() {
        return heightInCharacters;
    }

    /**
     * Gets the width in characters.
     * A standard terminal is 80 characters wide.
     *
     * @return width in characters.
     */
    public int getWidthInCharacters() {
        return widthInCharacters;
    }

    /**
     * Gets the distance from the left new text will be written to.
     *
     * @return the distance in characters.
     */
    public int getCursorX() {
        return cursorX;
    }

    /**
     * Sets the distance from the left new text will be written to.
     * This should be equal to or greater than 0 and less than the the width in characters.
     *
     * @param cursorX the distance from the left new text should be written to
     */
    public void setCursorX(int cursorX) {
        if (cursorX >= 0 && cursorX < widthInCharacters)
            //      throw new IllegalArgumentException("cursorX " + cursorX + " must be within range [0," + widthInCharacters + ")." );

            this.cursorX = cursorX;
    }

    /**
     * Gets the distance from the top new text will be written to.
     *
     * @return the distance in characters.
     */
    public int getCursorY() {
        return cursorY;
    }

    /**
     * Sets the distance from the top new text will be written to.
     * This should be equal to or greater than 0 and less than the the height in characters.
     *
     * @param cursorY the distance from the top new text should be written to
     */
    public void setCursorY(int cursorY) {
        if (cursorY >= 0 && cursorY < heightInCharacters)

            this.cursorY = cursorY;
    }

    /**
     * Sets the x and y position of where new text will be written to. The origin (0,0) is the upper left corner.
     * The x should be equal to or greater than 0 and less than the the width in characters.
     * The y should be equal to or greater than 0 and less than the the height in characters.
     *
     * @param x the distance from the left new text should be written to
     * @param y the distance from the top new text should be written to
     */
    public void setCursorPosition(int x, int y) {
        setCursorX(x);
        setCursorY(y);
    }

    /**
     * Gets the default background color that is used when writing new text.
     *
     * @return the default background color.
     */
    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    /**
     * Sets the default background color that is used when writing new text.
     *
     * @param defaultBackgroundColor the new background color.
     */
    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        if (defaultBackgroundColor == null)
            throw new NullPointerException("defaultBackgroundColor must not be null.");

        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    /**
     * Gets the default foreground color (the character color) that is used when writing new text.
     *
     * @return the default foreground color.
     */
    public Color getDefaultForegroundColor() {
        return defaultForegroundColor;
    }

    /**
     * Sets the default foreground color that is used when writing new text.
     *
     * @param defaultForegroundColor the new default color.
     */
    public void setDefaultForegroundColor(Color defaultForegroundColor) {
        if (defaultForegroundColor == null)
            throw new NullPointerException("defaultForegroundColor must not be null.");

        this.defaultForegroundColor = defaultForegroundColor;
    }

    /**
     * Gets the currently selected font as an AsciiFont object.
     *
     * @return the currently selected font.
     */
    public AsciiFont getAsciiFont() {
        return asciiFont;
    }

    /**
     * Sets the used font. It is advisable to make sure the parent component is properly sized after setting the font
     * as the panel dimensions will most likely change
     *
     * @param font the font as an AsciiFont object.
     */
    public void setAsciiFont(AsciiFont font) {
        if (this.asciiFont == font) {
            return;
        }
        this.asciiFont = font;

        this.charHeight = font.getHeight();
        this.charWidth = font.getWidth();
        this.terminalFontFile = font.getFontFilename();

        Dimension panelSize = new Dimension(charWidth * widthInCharacters, charHeight * heightInCharacters);
        setPreferredSize(panelSize);

        glyphs = new BufferedImage[256];

        offscreenBuffer = new BufferedImage(panelSize.width, panelSize.height, BufferedImage.TYPE_INT_RGB);
        offscreenGraphics = offscreenBuffer.getGraphics();

        loadGlyphs();

        oldChars = new char[widthInCharacters][heightInCharacters];
    }

    /**
     * {@inheritDoc}
     * When the panel has to be updated, it triggers the {@link #paint(Graphics g)} method.
     *
     * @param g {@inheritDoc}
     */
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * {@inheritDoc}
     * Draws the characters on the current panel starting from the top-left point of the panel.
     *
     * @param g {@inheritDoc}
     */
    @Override
    public void paint(Graphics g) {
        if (g == null)
            throw new NullPointerException();

        for (int x = 0; x < widthInCharacters; x++) {
            for (int y = 0; y < heightInCharacters; y++) {
                if (oldBackgroundColors[x][y] == backgroundColors[x][y]
                        && oldForegroundColors[x][y] == foregroundColors[x][y]
                        && oldChars[x][y] == getChars()[x][y])
                    continue;

                Color bg = backgroundColors[x][y];
                Color fg = foregroundColors[x][y];
                if (bg == null) bg = Color.BLACK;
                if (fg == null) fg = Color.BLACK;

                LookupOp op = setColors(bg, fg);
                BufferedImage img = op.filter(glyphs[getChars()[x][y]], null);
                offscreenGraphics.drawImage(img, x * charWidth, y * charHeight, null);

                oldBackgroundColors[x][y] = backgroundColors[x][y];
                oldForegroundColors[x][y] = foregroundColors[x][y];
                oldChars[x][y] = getChars()[x][y];
            }
        }
        g.drawImage(offscreenBuffer, 0, 0, this);
    }

    /**
     * Saved the panel into a specified file.
     *
     * @param filename the absolute path of the file where the panel will be saved.
     */
    public void save(String filename) {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(filename));
            bw.write(widthInCharacters + "\n");
            bw.write(heightInCharacters + "\n");

            for (int x = 0; x < widthInCharacters; x++) {
                for (int y = 0; y < heightInCharacters; y++) {
                    Color fg = foregroundColors[x][y];
                    Color bg = backgroundColors[x][y];

                    if (fg == null) fg = Color.black;
                    if (bg == null) bg = Color.black;

                    bw.write((int) getChars()[x][y]
                            + "\t" + fg.getRGB()
                            + "\t" + bg.getRGB() + "\n");
                }
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving:");
            System.exit(1);
        }

    }

    /**
     * Load a previously saved panel from the local filesystem.
     *
     * @param filename the path of the file that has to be loaded.
     */
    public void load(String filename) {
        AsciiRaster img = AsciiRaster.fromFile(filename);
        this.paintRaster(img, 0, 0, false);
        repaint();
    }

    /**
     * Load the glyphs, that is the picture containing all the Code Page 347 characters, and puts the characters inside a glyphs array.
     */
    private void loadGlyphs() {
        try {
            glyphSprite = ImageIO.read(AsciiPanel.class.getClassLoader().getResource(terminalFontFile));
        } catch (IOException e) {
            System.err.println("loadGlyphs(): " + e.getMessage());
        }

        for (int i = 0; i < 256; i++) {
            int sx = (i % 16) * charWidth;
            int sy = (i / 16) * charHeight;

            glyphs[i] = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
            glyphs[i].getGraphics().drawImage(glyphSprite, 0, 0, charWidth, charHeight, sx, sy, sx + charWidth, sy + charHeight, null);
        }
    }

    /**
     * Create a <code>LookupOp</code> object (lookup table) mapping the original
     * pixels to the background and foreground colors, respectively.
     *
     * @param bgColor the background color
     * @param fgColor the foreground color
     * @return the <code>LookupOp</code> object (lookup table)
     */
    private LookupOp setColors(Color bgColor, Color fgColor) {
        short[] a = new short[256];
        short[] r = new short[256];
        short[] g = new short[256];
        short[] b = new short[256];

        byte bga = (byte) (bgColor.getAlpha());
        byte bgr = (byte) (bgColor.getRed());
        byte bgg = (byte) (bgColor.getGreen());
        byte bgb = (byte) (bgColor.getBlue());

        byte fga = (byte) (fgColor.getAlpha());
        byte fgr = (byte) (fgColor.getRed());
        byte fgg = (byte) (fgColor.getGreen());
        byte fgb = (byte) (fgColor.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = bga;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = fga;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        short[][] table = {r, g, b, a};
        return new LookupOp(new ShortLookupTable(0, table), null);
    }

    /**
     * Clear the entire screen with the default background color.
     *
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear() {
        return clear(' ', 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the entire screen with the specified character and whatever the default foreground and background colors are.
     *
     * @param character the character to write
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        return clear(character, 0, 0, widthInCharacters, heightInCharacters, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the entire screen with the specified character and whatever the specified foreground and background colors are.
     *
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, Color foreground, Color background) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        return clear(character, 0, 0, widthInCharacters, heightInCharacters, foreground, background);
    }

    /**
     * Clear the section of the screen with the specified character and whatever the default foreground and background colors are.
     * The cursor position will not be modified.
     *
     * @param character the character to write
     * @param x         the distance from the left to begin writing from
     * @param y         the distance from the top to begin writing from
     * @param width     the height of the section to clear
     * @param height    the width of the section to clear
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, int x, int y, int width, int height) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ").");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ").");

        if (width < 1)
            throw new IllegalArgumentException("width " + width + " must be greater than 0.");

        if (height < 1)
            throw new IllegalArgumentException("height " + height + " must be greater than 0.");

        if (x + width > widthInCharacters)
            throw new IllegalArgumentException("x + width " + (x + width) + " must be less than " + (widthInCharacters + 1) + ".");

        if (y + height > heightInCharacters)
            throw new IllegalArgumentException("y + height " + (y + height) + " must be less than " + (heightInCharacters + 1) + ".");

        return clear(character, x, y, width, height, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Clear the section of the screen with the specified character and whatever the specified foreground and background colors are.
     *
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param width      the height of the section to clear
     * @param height     the width of the section to clear
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel clear(char character, int x, int y, int width, int height, Color foreground, Color background) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        if (width < 1)
            throw new IllegalArgumentException("width " + width + " must be greater than 0.");

        if (height < 1)
            throw new IllegalArgumentException("height " + height + " must be greater than 0.");

        if (x + width > widthInCharacters)
            throw new IllegalArgumentException("x + width " + (x + width) + " must be less than " + (widthInCharacters + 1) + ".");

        if (y + height > heightInCharacters)
            throw new IllegalArgumentException("y + height " + (y + height) + " must be less than " + (heightInCharacters + 1) + ".");

        int originalCursorX = cursorX;
        int originalCursorY = cursorY;
        for (int xo = x; xo < x + width; xo++) {
            for (int yo = y; yo < y + height; yo++) {
                write(character, xo, yo, foreground, background);
            }
        }
        cursorX = originalCursorX;
        cursorY = originalCursorY;

        return this;
    }

    /**
     * Write a character to the cursor's position.
     * This updates the cursor's position.
     *
     * @param character the character to write
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character) {
        if (character < 0 || character > glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        return write(character, cursorX, cursorY, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a character to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, Color foreground) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        return write(character, cursorX, cursorY, foreground, defaultBackgroundColor);
    }

    /**
     * Write a character to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     *
     * @param character  the character to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, Color foreground, Color background) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        return write(character, cursorX, cursorY, foreground, background);
    }

    /**
     * Write a character to the specified position.
     * This updates the cursor's position.
     *
     * @param character the character to write
     * @param x         the distance from the left to begin writing from
     * @param y         the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        return write(character, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a character to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y, Color foreground) {
        if (character < 0 || character >= glyphs.length)
            throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "].");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        return write(character, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a character to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     *
     * @param character  the character to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(char character, int x, int y, Color foreground, Color background) {

        if (foreground == null) foreground = defaultForegroundColor;
        if (background == null) background = defaultBackgroundColor;

        if (character >= 0 && character < glyphs.length)
            //      throw new IllegalArgumentException("character " + character + " must be within range [0," + glyphs.length + "]." );

            if (x >= 0 && x < widthInCharacters)
                //    throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")" );

                if (y >= 0 && y < heightInCharacters)
                //  throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")" );
                {

                    chars[x][y] = character;
                    foregroundColors[x][y] = foreground;
                    backgroundColors[x][y] = background;
                    cursorX = x + 1;
                    cursorY = y;
                }
        return this;
    }

    /**
     * Fills the panel starting from position (x,y) with the character in input.
     * If the position is inside a closed drawing, it will fill the drawing. If not, it will fill the entire panel
     *
     * @param character the character that will fill the panel.
     * @param x         the starting x position.
     * @param y         the starting y position.
     * @param fc        the character color.
     * @param bc        the background color.
     * @return the new panel, filled with the character.
     */
    public AsciiPanel fill(char character, int x, int y, Color fc, Color bc) {
        char oldchar = getChars()[x][y];
        if (oldchar != character) {
            getChars()[x][y] = character;
            foregroundColors[x][y] = fc;
            backgroundColors[x][y] = bc;
            int diff = 0;
            if (x < widthInCharacters - 1) {
                diff = Math.abs(getChars()[x + 1][y] - oldchar);
                if (diff >= 0 && diff <= 3)
                    fill(character, x + 1, y, fc, bc);
            }
            if (x > 0) {
                diff = Math.abs(getChars()[x - 1][y] - oldchar);
                if (diff >= 0 && diff <= 3)
                    fill(character, x - 1, y, fc, bc);
            }
            if (y > 0) {
                diff = Math.abs(getChars()[x][y - 1] - oldchar);
                if (diff >= 0 && diff <= 3)
                    fill(character, x, y - 1, fc, bc);
            }
            if (y < heightInCharacters - 1) {
                diff = Math.abs(getChars()[x][y + 1] - oldchar);
                if (diff >= 0 && diff <= 3)
                    fill(character, x, y + 1, fc, bc);
            }
        }
        return this;
    }

    /**
     * Write a string to the cursor's position.
     * This updates the cursor's position.
     *
     * @param string the string to write
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + ".");

        return write(string, cursorX, cursorY, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the cursor's position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + ".");

        return write(string, cursorX, cursorY, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the cursor's position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     *
     * @param string     the string to write
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (cursorX + string.length() > widthInCharacters)
            throw new IllegalArgumentException("cursorX + string.length() " + (cursorX + string.length()) + " must be less than " + widthInCharacters + ".");

        return write(string, cursorX, cursorY, foreground, background);
    }

    /**
     * Write a string to the specified position.
     * This updates the cursor's position.
     *
     * @param string the string to write
     * @param x      the distance from the left to begin writing from
     * @param y      the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, int x, int y) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + ".");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the specified position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, int x, int y, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + ".");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ")");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        return write(string, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the specified position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     *
     * @param string     the string to write
     * @param x          the distance from the left to begin writing from
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel write(String string, int x, int y, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null.");

        if (x + string.length() > widthInCharacters)
            throw new IllegalArgumentException("x + string.length() " + (x + string.length()) + " must be less than " + widthInCharacters + ".");

        if (x < 0 || x >= widthInCharacters)
            throw new IllegalArgumentException("x " + x + " must be within range [0," + widthInCharacters + ").");

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ").");

        if (foreground == null)
            foreground = defaultForegroundColor;

        if (background == null)
            background = defaultBackgroundColor;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foreground, background);
        }
        return this;
    }

    /**
     * Write a string to the center of the panel at the specified y position.
     * This updates the cursor's position.
     *
     * @param string the string to write
     * @param y      the distance from the top to begin writing from
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel writeCenter(String string, int y) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + ".");

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        return write(string, x, y, defaultForegroundColor, defaultBackgroundColor);
    }

    /**
     * Write a string to the center of the panel at the specified y position with the specified foreground color.
     * This updates the cursor's position but not the default foreground color.
     *
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel writeCenter(String string, int y, Color foreground) {
        if (string == null)
            throw new NullPointerException("string must not be null");

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + ".");

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ")");

        return write(string, x, y, foreground, defaultBackgroundColor);
    }

    /**
     * Write a string to the center of the panel at the specified y position with the specified foreground and background colors.
     * This updates the cursor's position but not the default foreground or background colors.
     *
     * @param string     the string to write
     * @param y          the distance from the top to begin writing from
     * @param foreground the foreground color or null to use the default
     * @param background the background color or null to use the default
     * @return this for convenient chaining of method calls
     */
    public AsciiPanel writeCenter(String string, int y, Color foreground, Color background) {
        if (string == null)
            throw new NullPointerException("string must not be null.");

        if (string.length() > widthInCharacters)
            throw new IllegalArgumentException("string.length() " + string.length() + " must be less than " + widthInCharacters + ".");

        int x = (widthInCharacters - string.length()) / 2;

        if (y < 0 || y >= heightInCharacters)
            throw new IllegalArgumentException("y " + y + " must be within range [0," + heightInCharacters + ").");

        if (foreground == null)
            foreground = defaultForegroundColor;

        if (background == null)
            background = defaultBackgroundColor;

        for (int i = 0; i < string.length(); i++) {
            write(string.charAt(i), x + i, y, foreground, background);
        }
        return this;
    }

    /**
     * Applies the transformation of each tile in the entire panel, defined by the TileTransformer object.
     *
     * @param transformer the TileTransformer object.
     */
    public void withEachTile(TileTransformer transformer) {
        withEachTile(0, 0, widthInCharacters, heightInCharacters, transformer);
    }

    /**
     * Applies the transformation of each tile in the panel, defined by the TileTransformer object.
     * The transformations is only applied within the sub-panel defined by a starting top-left point, a height and a width.
     * The starting point is defined as a top offset and a left offset.
     *
     * @param left        the pixel offset that define the left spacing of the starting point.
     * @param top         the pixel offset that define the top spacing of the starting point.
     * @param width       the width of the sub-ascii panel that starts from the top-left point.
     * @param height      the height of the sub-ascii panel that starts from the top-left point.
     * @param transformer the TileTransformer object.
     */
    public void withEachTile(int left, int top, int width, int height, TileTransformer transformer) {
        AsciiCharacterData data = new AsciiCharacterData();
        System.out.println("WithEachTile:" + left + top + width + height + transformer.toString());
        for (int x0 = 0; x0 < width; x0++)
            for (int y0 = 0; y0 < height; y0++) {
                int x = left + x0;
                int y = top + y0;

                if (x < 0 || y < 0 || x >= widthInCharacters || y >= heightInCharacters)
                    continue;

                data.setCharacter(getChars()[x][y]);
                data.setForegroundColor(foregroundColors[x][y]);
                data.setBackgroundColor(backgroundColors[x][y]);

                transformer.transformTile(x, y, data);

                getChars()[x][y] = data.getCharacter();
                foregroundColors[x][y] = data.getForegroundColor();
                backgroundColors[x][y] = data.getBackgroundColor();
            }
    }

    /**
     * Gets the current x position of the mouse cursor.
     *
     * @return the current x position.
     */
    public int getMouseCursorX() {
        return mcursorx;
    }

    /**
     * Sets the mouse cursor x position to a specified position.
     *
     * @param x the x position to set.
     */
    public void setMouseCursorX(int x) {
        mcursorx = x;
    }

    /**
     * Gets the current y position of the mouse cursor.
     *
     * @return the current y position.
     */
    public int getMouseCursorY() {
        return mcursory;
    }

    /**
     * Sets the mouse cursor y position to a specified position.
     *
     * @param y the y position to set.
     */
    public void setMouseCursorY(int y) {
        mcursory = y;
    }

    /**
     * Allows to pick an existing character on the panel at the coordinates (px, py).
     *
     * @param px the x position of the char to be picked.
     * @param py the y position of the char to be picked.
     * @return the ascii int value of the picked char.
     */
    public Integer pickChar(int px, int py) {
        return (int) getChars()[px][py];
    }

    /**
     * Picks the foreground color of the char at the coordinates (px, py).
     *
     * @param px the char x position.
     * @param py the char y position.
     * @return the char foreground color.
     */
    public Color pickFC(int px, int py) {
        return foregroundColors[px][py];
    }

    /**
     * Picks the background color of the char at position (px, py).
     *
     * @param px the char x position.
     * @param py the char y position.
     * @return the char background color.
     */
    public Color pickBC(int px, int py) {
        return backgroundColors[px][py];
    }

//    public Color pickInverseFC(int px, int py){
//        int red = foregroundColors[px][py].getRed();
//        int green = foregroundColors[px][py].getGreen();
//        int blue = foregroundColors[px][py].getBlue();
//        return new Color(255-red, 255-green, 255-blue);
//    }

    /**
     * Fills up the current {@link AsciiPanel} with the {@link AsciiRaster} in input, starting from a certain position (x,y).
     * The starting point correspond to the top left corner of the image.
     *
     * @param raster      the image that has to be written on the panel.
     * @param x           the starting x position.
     * @param y           the starting y position.
     * @param transparent if true, the black characters will be transparent and show the content underneath.
     * @return the new AsciiPanel filled with the raster image.
     */
    public AsciiPanel paintRaster(AsciiRaster raster, int x, int y, boolean transparent) {
        System.out.println("Paint raster " + transparent);
		/*if (raster==null)
		{
			System.out.println("Raster null!");
			System.exit(1);

		}*/
        int dx = 0;
        int dy = 0;
        if (x < 0) dx = -x;
        if (y < 0) dy = -y;
        int sx = dx;
        int sy = dy;
        for (int xi = x + dx; xi < widthInCharacters; xi++) {
            sy = dy;
            for (int yi = y + dy; yi < heightInCharacters; yi++) {

                char nc = raster.getChars()[sx][sy];

                if (nc != 0 || !transparent) {
                    getChars()[xi][yi] = nc;
                    foregroundColors[xi][yi] = raster.getForecolors()[sx][sy];
                    backgroundColors[xi][yi] = raster.getBackcolors()[sx][sy];
                }
                sy++;
                if (sy == raster.getRasterWidth()) break;
            }
            sx++;
            if (sx == raster.getRasterHeight()) break;

        }
        return this;
    }

    /**
     * Gets the two dimensional array of the panel characters.
     *
     * @return the chars matrix.
     */
    public char[][] getChars() {
        return chars;
    }

    /**
     * Sets the panel characters matrix.
     *
     * @param chars the characters matrix.
     */
    public void setChars(char[][] chars) {
        this.chars = chars;
    }

    public Color[][] getOldBackgroundColors() {
        return oldBackgroundColors;
    }

    public Color[][] getOldForegroundColors() {
        return oldForegroundColors;
    }

    public void setBackgroundColors(Color[][] backgroundColors) {
        this.backgroundColors = backgroundColors;
    }

    public void setForegroundColors(Color[][] foregroundColors) {
        this.foregroundColors = foregroundColors;
    }
}
