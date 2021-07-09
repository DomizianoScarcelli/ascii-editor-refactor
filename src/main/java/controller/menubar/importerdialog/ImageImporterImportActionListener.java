package controller.menubar.importerdialog;


import view.MainPanel;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 * Models the action of importing an image from the local filesystem.
 */
public class ImageImporterImportActionListener implements ActionListener {


    /**
     * {@inheritDoc}
     * Converts the selected image from the filesystem into a {@link BufferedImage}, resize it and writes it on the current panel using
     * the {@link Graphics2D} class.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser("resources/");
        int returnVal = fileChooser.showOpenDialog(MainPanel.getInstance());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                MainPanel.getInstance().setImportedBufferedImage(ImageIO
                        .read(new File(fileChooser.getSelectedFile().getAbsolutePath())));
                System.out.println("Resizing...");
                int tx = MainPanel.getInstance().getAsciiPanel().getWidthInCharacters();
                int ty = MainPanel.getInstance().getAsciiPanel().getHeightInCharacters();

                BufferedImage resized = new BufferedImage(tx, ty, MainPanel.getInstance().getImportedBufferedImage().getType());
                Graphics2D g = resized.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(MainPanel.getInstance().getImportedBufferedImage(), 0, 0, tx, ty, 0, 0,
                        MainPanel.getInstance().getImportedBufferedImage().getWidth(), MainPanel.getInstance().getImportedBufferedImage().getHeight(),
                        null);
                g.dispose();
                MainPanel.getInstance().setImportedBufferedImage(resized);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

}
