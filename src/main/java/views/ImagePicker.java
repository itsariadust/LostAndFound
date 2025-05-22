package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class ImagePicker {
    public static String selectImageFile(Component parent, JLabel imagePreviewLabel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes()));

        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                // Read the image properly using ImageIO
                Image originalImage = ImageIO.read(fileChooser.getSelectedFile());
                if (originalImage != null) {
                    Image scaledImage = originalImage.getScaledInstance(
                            400, 400, Image.SCALE_SMOOTH);
                    imagePreviewLabel.setIcon(new ImageIcon(scaledImage));
                    imagePreviewLabel.revalidate();
                    imagePreviewLabel.repaint();
                    return fileChooser.getSelectedFile().getPath();
                } else {
                    throw new Exception("Invalid image file");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent,
                        "Could not load image: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                imagePreviewLabel.setIcon(null);
            }
        }
        return null;
    }
}
