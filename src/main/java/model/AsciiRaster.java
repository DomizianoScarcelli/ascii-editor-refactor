package model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Models the rasterized ascii image.
 */
public class AsciiRaster {
    private char[][] chars;
    private Color[][] forecolors;
    private Color[][] backcolors;
    private int rasterHeight;
    private int rasterWidth;

    /**
     * Class constructor.
     * Creates the AsciiRaster image from the height and the width.
     *
     * @param rasterHeight the raster height.
     * @param rasterWidth the raster width.
     */
    public AsciiRaster(int rasterHeight, int rasterWidth) {
        setRasterHeight(rasterHeight);
        setRasterWidth(rasterWidth);
        setChars(new char[rasterHeight][rasterWidth]);
        setForecolors(new Color[rasterHeight][rasterWidth]);
        setBackcolors(new Color[rasterHeight][rasterWidth]);
    }

    /**
     * Creates an AsciiRaster image from a file.
     * The file must be created by saving a previous panel.
     *
     * @param filename the path of the file.
     * @return the AsciiRaster image of the file.
     */
    public static AsciiRaster fromFile(String filename) {
        BufferedReader br;
        AsciiRaster res = null;
        try {
            br = new BufferedReader(new FileReader(filename));

            int height = Integer.parseInt(br.readLine());
            int width = Integer.parseInt(br.readLine());
            res = new AsciiRaster(height, width);
            int x = 0;
            int y = 0;
            while (br.ready()) {
                //System.out.println("Loading x:"+x+"/sx:"+sx+" y:"+y+"/ sy:"+sy);
                String line = br.readLine();
                String[] lines = line.split("\t");
                Color fg = new Color(Integer.parseInt(lines[1]));
                Color bg = new Color(Integer.parseInt(lines[2]));
                char ch = (char) Integer.parseInt(lines[0]);
                res.getChars()[y][x] = ch;
                res.getForecolors()[y][x] = fg;
                res.getBackcolors()[y][x] = bg;
                x++;
                if (x == width) {
                    x = 0;
                    y++;
                    if (y == height) break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Return the height of the AsciiRaster image.
     * @return the height in characters.
     */
    public int getRasterHeight() {
        return rasterHeight;
    }

    /**
     * Sets the height of the AsciiRaster image.
     * @param rasterHeight the height in characters.
     */
    public void setRasterHeight(int rasterHeight) {
        this.rasterHeight = rasterHeight;
    }

    /**
     * Return the width of the AsciiRaster image.
     * @return the width in characters.
     */
    public int getRasterWidth() {
        return rasterWidth;
    }

    /**
     * Sets the width of the AsciiRaster image.
     * @param rasterWidth the width in characters.
     */
    public void setRasterWidth(int rasterWidth) {
        this.rasterWidth = rasterWidth;
    }

    /**
     * Return the char matrix representation of the image.
     * @return the char matrix as a two dimensions array.
     */
    public char[][] getChars() {
		/*if (chars==null)
		{

			System.out.println("raster chars null!");
			System.exit(1);
		}*/

        return chars;
    }

    /**
     * Sets the char matrix representation of the image.
     * @param chars the char matrix as a two dimensions array.
     */
    private void setChars(char[][] chars) {
        this.chars = chars;
    }

    /**
     * Return the matrix that maps each character of the char matrix with his foreground color.
     * In the i,j position there is the color of the char in the i,j position of the char matrix.
     * @return the foreground color matrix.
     */
    public Color[][] getForecolors() {
        return forecolors;
    }

    /**
     * Sets the color matrix that maps each character of the car matrix with his foreground color.
     * @param forecolors the color matrix.
     */
    private void setForecolors(Color[][] forecolors) {
        this.forecolors = forecolors;
    }

    /**
     * Return the matrix that maps each character of the char matrix with his background color.
     * In the i,j position there is the color of the char in the i,j position of the char matrix.
     * @return the background color matrix.
     */
    public Color[][] getBackcolors() {
        return backcolors;
    }

    /**
     * Sets the color matrix that maps each character of the car matrix with his background color.
     * @param backcolors the color matrix.
     */
    private void setBackcolors(Color[][] backcolors) {
        this.backcolors = backcolors;
    }
}
