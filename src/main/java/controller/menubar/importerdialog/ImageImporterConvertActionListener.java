package controller.menubar.importerdialog;


import java.awt.Color;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import model.AsciiPanel;
import view.ImageImporterDialog;
import view.MainPanel;

/**
 * Models the action of converting an image from pixels to ascii character.
 */
public class ImageImporterConvertActionListener implements ActionListener {
    /**
     * The panel where the ascii character are shown.
     */
    private AsciiPanel asciiPanel;
    /**
     * A text field component on the GUI that indicates the threshold value.
     */
    private JTextField threshold;
    /**
     * A toggle button component on the GUI that indicates whether to convert the image into a 16-bit color (checked) or a 4-bit color (not checked).
     */
    private JCheckBox convertColorOptions;

    /**
     * Class constructor. Construct the class from an {@link AsciiPanel}, a {@link JTextField} component and a {@link JToggleButton} component.
     *
     * @param asciiPanel the {@link AsciiPanel} where the action has to be done.
     * @param threshold     the {@link JTextField} containing the threshold value.
     * @param convertColorOptions     the {@link JCheckBox} that indicates whether the the image has to be converted to 16-bit or 4-bit colors.
     */
    public ImageImporterConvertActionListener(AsciiPanel asciiPanel, JTextField threshold, JCheckBox convertColorOptions) {
        this.asciiPanel = asciiPanel;
        this.threshold = threshold;
        this.convertColorOptions = convertColorOptions;
    }

    /**
     * Converts the source image to 4-bit colour using the default 16-colour
     * palette:
     * <ul>
     * <li>black</li>
     * <li>dark red</li>
     * <li>dark green</li>
     * <li>dark yellow</li>
     * <li>dark blue</li>
     * <li>dark magenta</li>
     * <li>dark cyan</li>
     * <li>dark grey</li>
     * <li>light grey</li>
     * <li>red</li>
     * <li>green</li>
     * <li>yellow</li>
     * <li>blue</li>
     * <li>magenta</li>
     * <li>cyan</li>
     * <li>white</li>
     * </ul>
     * No transparency.
     *
     * @param src the source image to convert
     * @return a copy of the source image with a 4-bit colour depth, with the
     * default colour pallette
     */
    public static BufferedImage convert4(BufferedImage src) {
        int[] cmap = new int[]{0x000000, 0x800000, 0x008000, 0x808000, 0x000080, 0x800080, 0x008080, 0x808080,
                0xC0C0C0, 0xFF0000, 0x00FF00, 0xFFFF00, 0x0000FF, 0xFF00FF, 0x00FFFF, 0xFFFFFF};
        return convert4(src, cmap);
    }

    /**
     * Converts the source image to 4-bit colour using the given colour map. No
     * transparency.
     *
     * @param src  the source image to convert
     * @param cmap the colour map, which should contain no more than 16 entries The
     *             entries are in the form RRGGBB (hex).
     * @return a copy of the source image with a 4-bit colour depth, with the custom
     * colour palette.
     */
    public static BufferedImage convert4(BufferedImage src, int[] cmap) {
        IndexColorModel icm = new IndexColorModel(4, cmap.length, cmap, 0, false, Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_BINARY, icm);
        ColorConvertOp cco = new ColorConvertOp(src.getColorModel().getColorSpace(),
                dest.getColorModel().getColorSpace(), null);
        cco.filter(src, dest);

        return dest;
    }

    /**
     * {@inheritDoc}
     * Converts the imported image as an {@link AsciiPanel} and imports it in the current panel.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        BufferedImage bimage = MainPanel.getInstance().getImportedBufferedImage();

        System.out.println("Convert Pressed..");
        if (bimage != null) {
            asciiPanel.clear();
            int thi = Integer.parseInt(threshold.getText());
            BufferedImage[] glyphs = asciiPanel.getGlyphs();

            Map<Integer, Integer> index2numpixels = new HashMap<Integer, Integer>();
            for (int i = 0; i < glyphs.length; i++) {
                BufferedImage bi = glyphs[i];
                if (bi == null)
                    continue;
                int tot = 0;
                for (int x = 0; x < bi.getWidth(); x++)
                    for (int y = 0; y < bi.getHeight(); y++) {
                        int c = bi.getRGB(x, y);
                        Color cc = new Color(c, false);
                        if (cc.getRed() > 0 || cc.getGreen() > 0 || cc.getBlue() > 0)
                            tot++;
                    }
                int key = (int) ((float) (tot) / (float) (bi.getWidth() * bi.getHeight()) * 255.f);
                if (!index2numpixels.containsKey(key))
                    index2numpixels.put(key, i);

            }

            int[][] buffer = new int[bimage.getWidth()][bimage.getHeight()];
            // BufferedImage bi=ImageIO.read(new
            // File("resources/bitmaps/80x50Stefano.png"));
            for (int x = 0; x < bimage.getWidth(); x++)
                for (int y = 0; y < bimage.getHeight(); y++) {
                    int c = bimage.getRGB(x, y);
                    Color cc = new Color(c);
                    int ri = Math.max(Math.max(cc.getRed(), cc.getGreen()), cc.getBlue());
                    // if (cc.getRed()>0||cc.getGreen()>0||cc.getBlue()>0)
                    buffer[x][y] = Math.min(ri, thi);
                }

            if (!convertColorOptions.isSelected()) {
                bimage = convert4(bimage);
            }
            for (int x = 0; x < Math.min(bimage.getWidth(), 80); x++)
                for (int y = 0; y < Math.min(bimage.getHeight(), 80); y++) {
                    int k = 255 - buffer[x][y];
                    // System.out.println("**"+k);
                    while (!index2numpixels.containsKey(k) && k > 0) {
                        k--;
                    }
                    asciiPanel.setCursorX(x);
                    asciiPanel.setCursorY(y);
                    int c = bimage.getRGB(x, y);
                    Color cc = new Color(c);
                    asciiPanel.write((char) k, cc);

                }
            asciiPanel.repaint();
            ImageImporterDialog.getInstance().setVisible(false);
            ImageImporterDialog.getInstance().close();
        }
    }
}
