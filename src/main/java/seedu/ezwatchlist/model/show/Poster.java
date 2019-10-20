package seedu.ezwatchlist.model.show;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.InputStream;
import java.net.URL;

/**
 * Represents a Show's poster in the watchlist.
 */
public class Poster {
    private static final String PLACEHOLDER_IMAGE = "/images/poster-placeholder.png";
    private Image image;
    private String imagePath;
    private boolean isImageOnline;

    /**
     * Constructs a {@code Poster}.
     */
    public Poster() {
        imagePath = PLACEHOLDER_IMAGE;
        isImageOnline = false;
    }

    public Poster(String path, boolean isOnline) {
        imagePath = path;
        isImageOnline = isOnline;
    }

    /**
     * returns the image of the Poster.
     */
    public Image getImage() {
        try {
            if (!isImageOnline) {
                image = new Image(imagePath);
            } else {
                URL url = new URL(imagePath);
                image = SwingFXUtils.toFXImage(ImageIO.read(url), null);
                if (image == null) {
                    throw new NullPointerException("image is null in poster");
                }
            }
            return image;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            return new Image(PLACEHOLDER_IMAGE);
        }
    }
}
