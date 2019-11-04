package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EpisodeTest {

    @Test
    public void isValidEpisodeName() {
        // invalid Episode name
        assertFalse(Episode.isValidEpisodeName(""));
        assertFalse(Episode.isValidEpisodeName(" "));

        // valid Episode name
        assertTrue(Episode.isValidEpisodeName("Episode 1"));
    }

    @Test
    public void isValidEpisodeNumber() {
        // negative episode number
        assertFalse(Episode.isValidEpisodeNum(-1));

        // 0 episode number
        assertFalse(Episode.isValidEpisodeNum(0));

        // valid Episode number
        assertTrue(Episode.isValidEpisodeNum(1));
    }

}
