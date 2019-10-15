package seedu.ezwatchlist.model.show;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * Represents a Show's name in the watchlist.
 */
public class Poster {
    private static final String PLACEHOLDER_IMAGE = "/images/poster-placeholder.png";
    public Image image;

    /**
     * Constructs a {@code Poster}.
     *
     * @param image A valid image.
     */
    public Poster() {
    }

    public void setImage(String imageUrl) {
        image = new Image(imageUrl);
    }
}
