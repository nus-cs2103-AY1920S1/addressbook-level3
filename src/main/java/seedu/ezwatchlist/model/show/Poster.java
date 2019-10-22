package seedu.ezwatchlist.model.show;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.ezwatchlist.api.ImageRetrieval;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a Show's poster in the watchlist.
 */
public class Poster {
    private static final String PLACEHOLDER_IMAGE = "/images/poster-placeholder.png";
    private String IMAGE_CACHE_LOCATION;
    private Image image;
    private String imagePath;

    /**
     * Constructs a {@code Poster}.
     */
    public Poster() {
        imagePath = PLACEHOLDER_IMAGE;
    }

    public Poster(String path) {
        imagePath = path;
    }

    /**
     * returns the image of the Poster.
     */
    public Image getImage() {
        try {
//            URL url = getClass().getResource(imagePath);
//            String s = url.toExternalForm();
            String ss = ImageRetrieval.IMAGE_CACHE_LOCATION + File.separator + imagePath;
            File file = new File(ss.toString());
            System.out.println("File path in Poster is :" + ss.toString());
            image = SwingFXUtils.toFXImage(ImageIO.read(file), null);

            if (image == null) {
                throw new NullPointerException("image is null in poster");
            }
            return image;
        } catch (IIOException i) {
            System.err.print(i.getMessage() + " in Poster");
            return new Image(PLACEHOLDER_IMAGE);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Cause: " + e.getCause() + "Message: " +e.getMessage() + " from Poster and imagePath ");
            return new Image(PLACEHOLDER_IMAGE);
        }
    }
}
