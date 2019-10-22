package seedu.ezwatchlist.model.show;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * Represents a Show's poster in the watchlist.
 */
public class Poster {
    private static final String PLACEHOLDER_IMAGE = "/images/poster-placeholder.png";
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
            URL url = getClass().getResource(imagePath);
            String s = url.toExternalForm();
            System.out.println(s);
            image = new Image(s);
            if (image == null) {
                throw new NullPointerException("image is null in poster");
            }
            return image;
        } catch (Exception e) {
            System.out.println("Cause: " + e.getCause() + "Message: " +e.getMessage() + " from Poster and imagePath "
                    );
            return new Image(PLACEHOLDER_IMAGE);
        }
    }
}
