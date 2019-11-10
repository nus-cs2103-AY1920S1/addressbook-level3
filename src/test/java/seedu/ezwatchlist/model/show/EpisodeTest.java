package seedu.ezwatchlist.model.show;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EpisodeTest {

    private static Episode validEpisode = new Episode("Episode 1", 1);

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

    @Test
    public void episodeConstructorTest() {
        assertEquals(new Episode("Episode name", 3).getEpisodeName(), "Episode name");
        assertEquals(new Episode("Episode name", 3).getEpisodeNum(), 3);
    }

}
