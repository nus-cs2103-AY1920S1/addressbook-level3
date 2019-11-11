package seedu.ezwatchlist.model.show;

import static java.util.Objects.isNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import seedu.ezwatchlist.api.model.ImageRetrieval;
import seedu.ezwatchlist.commons.core.LogsCenter;

/**
 * Represents a Show's poster in the watchlist.
 */
public class Poster {
    private static final Logger logger = LogsCenter.getLogger(Poster.class);
    private static final String PLACEHOLDER_IMAGE = "/images/poster-placeholder.png";
    private static final String ROOT_LOCATION = ImageRetrieval.IMAGE_CACHE_LOCATION + File.separator;
    private Image image;
    private String imagePath;
    private boolean isPlaceholder;

    /**
     * Constructs a Poster class which defaults to a placeholder image to be displayed.
     */
    public Poster() {
        isPlaceholder = true;
        imagePath = PLACEHOLDER_IMAGE;
    }

    /**
     * Constructs a {@code Poster} with the path of the image given.
     * @param path the path of the image in the save location.
     */
    public Poster(String path) {
        if (isNull(path)) {
            isPlaceholder = true;
            imagePath = PLACEHOLDER_IMAGE;
        } else {
            isPlaceholder = false;
            imagePath = path;
        }
    }

    /**
     * Returns the image path of the image.
     * @return string format of the image path.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Returns the image of the Poster.
     * @return the Image to be displayed in the application.
     */
    public Image getImage() {
        if (isPlaceholder) {
            return new Image(PLACEHOLDER_IMAGE);
        }

        try {
            if (isOfflineImage()) {
                return new Image(imagePath); //return offline image.
            }

            String url = ROOT_LOCATION + imagePath;
            File file = new File(url);
            image = SwingFXUtils.toFXImage(ImageIO.read(file), null);

            return image;
        } catch (IOException i) {
            logger.info("Cause: " + i + " in Poster class for imagePath " + imagePath);
            return new Image(PLACEHOLDER_IMAGE);
        } catch (NullPointerException e) {
            logger.info("Cause: " + e + " in Poster class for imagePath " + imagePath);
            return new Image(PLACEHOLDER_IMAGE);
        }
    }

    /**
     * Checks if the image path is a local image used for offline display.
     * @return true if the image is offline.
     */
    private boolean isOfflineImage() {
        try {
            Image offlineImage = new Image(imagePath);
            //checks if the image could be in the offline storage.
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
