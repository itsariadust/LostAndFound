package utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PathToImageIcon {
    public ImageIcon createImageIcon(String filePath) {
        try {
            // Read the image file
            BufferedImage originalImage = ImageIO.read(new File(filePath));

            // Scale the image to 400x400
            Image scaledImage = originalImage.getScaledInstance(400, 400, Image.SCALE_SMOOTH);

            // Create and return the ImageIcon
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            return null;
        }
    }
}
