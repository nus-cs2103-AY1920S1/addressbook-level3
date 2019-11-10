package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Episode;

class JsonAdaptedEpisodeTest {

    @Test
    void getEpisodeName() {
        JsonAdaptedEpisode jsonAdaptedEpisode = new JsonAdaptedEpisode("Episode 1" , 1);
        Episode episode = new Episode("Episode 2", 2);
        JsonAdaptedEpisode jsonAdaptedEpisode2 = new JsonAdaptedEpisode("Episode 2" , -1);
        assertEquals(jsonAdaptedEpisode.getEpisodeName(), "Episode 1");
        assertEquals(new JsonAdaptedEpisode(episode).getEpisodeNum(), 2);
    }

    @Test
    void getEpisodeNum() {
        JsonAdaptedEpisode jsonAdaptedEpisode = new JsonAdaptedEpisode("Episode 1" , 1);
        JsonAdaptedEpisode jsonAdaptedEpisode2 = new JsonAdaptedEpisode("Episode 2" , -1);
        assertEquals(jsonAdaptedEpisode.getEpisodeNum(), 1);
    }

    @Test
    void toModelType() throws IllegalValueException {
        JsonAdaptedEpisode jsonAdaptedEpisode = new JsonAdaptedEpisode("Episode 1" , 1);
        JsonAdaptedEpisode jsonAdaptedEpisode2 = new JsonAdaptedEpisode("Episode 2" , -1);
        assertThrows(IllegalValueException.class, () -> jsonAdaptedEpisode2.toModelType());
        assertTrue(jsonAdaptedEpisode.toModelType() instanceof Episode);
    }
}
