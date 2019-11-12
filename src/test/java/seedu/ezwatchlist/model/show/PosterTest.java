package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PosterTest {

    @Test
    void constructor_anyParam_willWork() {
        assertAll(() -> new Poster(null), () ->
                new Poster("-123"), () -> new Poster(""));
    }

    @Test
    void getImagePathPlaceholder() {
        Poster placeholderImage = new Poster();
        Poster invalidImage = new Poster("-123");
        assertTrue(new Poster(null).getImagePath().equals(placeholderImage.getImagePath()));
        assertFalse(invalidImage.getImagePath().equals(placeholderImage.getImagePath()));
    }
}
