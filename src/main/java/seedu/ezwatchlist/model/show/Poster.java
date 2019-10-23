package seedu.ezwatchlist.model.show;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Represents a Show's poster in the watchlist.
 */
public class Poster {
    private static final String PLACEHOLDER_IMAGE = "poster-placeholder.png";
    private String imageCacheLocation;
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
            //URL url = getClass().getResource(imagePath);
            //String s = url.toExternalForm();
            Path root = FileSystems.getDefault().getPath("").toAbsolutePath();
            Path ss = Paths.get(root.toString(), "src", "main", "resources",
                    "images", "posters", imagePath);
            File file = new File(ss.toString());
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
            System.out.println("Cause: " + e.getCause() + "Message: " + e.getMessage() + " from Poster and imagePath ");
            return new Image(PLACEHOLDER_IMAGE);
        }
    }
}
