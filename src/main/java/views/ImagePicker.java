package views;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ImagePicker {
    public static String selectImageFile(Component parent, JLabel imagePreviewLabel) {
        // Create a native file dialog
        FileDialog fileDialog = new FileDialog((Frame) SwingUtilities.getWindowAncestor(parent),
                "Select Image", FileDialog.LOAD);

        // Set file filter (note: native dialogs might handle this differently per OS)
        fileDialog.setFilenameFilter((dir, name) -> {
            String lowerName = name.toLowerCase();
            return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") ||
                    lowerName.endsWith(".png") || lowerName.endsWith(".gif") ||
                    lowerName.endsWith(".bmp");
        });

        fileDialog.setVisible(true);

        // Get the selected file
        String file = fileDialog.getFile();
        String directory = fileDialog.getDirectory();

        try {
            File selectedFile = new File(directory, file);
            Image originalImage = ImageIO.read(selectedFile);
            if (originalImage != null) {
                Image scaledImage = originalImage.getScaledInstance(
                        400, 400, Image.SCALE_SMOOTH);
                imagePreviewLabel.setIcon(new ImageIcon(scaledImage));
                imagePreviewLabel.revalidate();
                imagePreviewLabel.repaint();
                return selectedFile.getAbsolutePath();
            } else {
                throw new Exception("Invalid image file");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent,
                    "Could not load image: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            imagePreviewLabel.setIcon(null);
            return null;
        }
    }
}