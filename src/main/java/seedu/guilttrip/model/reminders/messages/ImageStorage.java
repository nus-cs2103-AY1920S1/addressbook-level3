package seedu.guilttrip.model.reminders.messages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Used to store image for future use in messages.
 */
public class ImageStorage {
    private static HashMap<String, String> imageLinks = new HashMap<>();
    /**
     * Saves image to internal storage
     */
    public static void saveImage(String imageName, Image image) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        String imageLink = "/images/" + imageName + ".jpg";
        File outputFile = new File(imageLink);
        outputFile.createNewFile();
        outputFile.getParentFile().mkdirs();
        ImageIO.write(bImage, "jpg", outputFile);
        imageLinks.put(imageName, imageLink);
    }

    /**
     * Loads image from storage;
     * @param imageName
     * @return
     * @throws FileNotFoundException
     */
    public static Image loadImage(String imageName) throws FileNotFoundException {
        if (!imageLinks.containsKey(imageName)) {
            throw new FileNotFoundException("Image not stored");
        }
        String imageLink = imageLinks.get(imageName);
        try {
            FileInputStream inputStream = new FileInputStream(imageLink);
            Image image = new Image(inputStream);
            return image;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Image stored but cannot read from file");
        }
    }
}
