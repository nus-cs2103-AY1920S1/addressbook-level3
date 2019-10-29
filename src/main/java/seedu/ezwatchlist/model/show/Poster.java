package seedu.ezwatchlist.model.show;

import java.io.File;
import java.util.logging.Logger;

import javax.imageio.IIOException;
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

    /**
     * Constructs a {@code Poster}.
     */
    public Poster() {
        imagePath = PLACEHOLDER_IMAGE;
    }

    /**
     * Constructs a {@code Poster} with a path given.
     * @param path the path of the image in the save location.
     */
    public Poster(String path) {
        imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    /**
     * returns the image of the Poster.
     */
    public Image getImage() {
        try {
            String url = ROOT_LOCATION + imagePath;
            File file = new File(url);
            image = SwingFXUtils.toFXImage(ImageIO.read(file), null);

            if (image == null) {
                throw new NullPointerException("image is null in poster with url: " + url);
            }

            return image;
        } catch (IIOException i) {
            logger.info("Cause: " + i + " in Poster class for imagePath " + imagePath);
            return new Image(PLACEHOLDER_IMAGE);
        } catch (Exception e) {
            logger.info("Cause: " + e + " in Poster class for imagePath " + imagePath);
            return new Image(PLACEHOLDER_IMAGE);
        }
    }
}
