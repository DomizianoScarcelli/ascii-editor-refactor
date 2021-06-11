package model;


/**
 * This class provides all available Fonts for the AsciiPanel.
 * Some graphics are from the Dwarf Fortress Tileset Wiki Page
 *
 * @author zn80
 */
public class AsciiFont {

    /**
     * 8x8 pixels-per-character Code page 437 font
     */
    public static final AsciiFont CP437_8x8 = new AsciiFont("cp437_8x8.png", 8, 8);
    /**
     * 10x10 pixels-per-character Code page 437 font
     */
    public static final AsciiFont CP437_10x10 = new AsciiFont("cp437_10x10.png", 10, 10);
    /**
     * 12x12 pixels-per-character Code page 437 font
     */
    public static final AsciiFont CP437_12x12 = new AsciiFont("cp437_12x12.png", 12, 12);
    /**
     * 16x16 pixels-per-character Code page 437 font
     */
    public static final AsciiFont CP437_16x16 = new AsciiFont("cp437_16x16.png", 16, 16);
    /**
     * 9x16 pixels-per-character Code page 437 font
     */
    public static final AsciiFont CP437_9x16 = new AsciiFont("cp437_9x16.png", 9, 16);
    /**
     * 10x10 pixels-per-character Drake font
     */
    public static final AsciiFont DRAKE_10x10 = new AsciiFont("drake_10x10.png", 10, 10);
    /**
     * 8x8 pixels-per-character Taffer 437 font
     */
    public static final AsciiFont TAFFER_10x10 = new AsciiFont("taffer_10x10.png", 10, 10);
    /**
     * 8x8 pixels-per-character QBICFEET font
     */
    public static final AsciiFont QBICFEET_10x10 = new AsciiFont("qbicfeet_10x10.png", 10, 10);
    /**
     * 8x8 pixels-per-character TALRYTH font
     */
    public static final AsciiFont TALRYTH_15_15 = new AsciiFont("talryth_square_15x15.png", 15, 15);

    /**
     * {@link #getFontFilename()}
     */
    private String fontFilename;
    /**
     * {@link #getWidth()}
     */
    private int width;
    /**
     * {@link #getHeight()}
     */
    private int height;

    /**
     * Class constructor. Construct an AsciiFont object from the pathname of the image containing the characters,
     * the width and the height of the image.
     *
     * @param filename the pathname of the image that displays the font.
     * @param width    the width of the image in filename.
     * @param height   the height of the image in filename.
     */
    public AsciiFont(String filename, int width, int height) {
        this.fontFilename = filename;
        this.width = width;
        this.height = height;
    }


    /**
     * Gets the name of the image that displays the font and the characters.
     *
     * @return the name as a string.
     */
    public String getFontFilename() {
        return fontFilename;
    }

    /**
     * Gets the width in pixels-per-character of the image containing the font.
     *
     * @return the integer that represent the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height in pixels-per-character of the image containing the font.
     *
     * @return the integer that represent the height.
     */
    public int getHeight() {
        return height;
    }
}

