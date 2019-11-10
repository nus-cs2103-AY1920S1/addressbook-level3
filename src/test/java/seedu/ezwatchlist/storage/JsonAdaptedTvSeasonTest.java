package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.TvSeason;



class JsonAdaptedTvSeasonTest {

    @Test
    void getSeasonNumber() {
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("Test"));
        Episode episode = new Episode("Test", 1);
        ArrayList<Episode> episodeArrayList = new ArrayList<>();
        episodeArrayList.add(episode);
        TvSeason tvSeason = new TvSeason(1, 1, episodeArrayList);
        assertEquals(new JsonAdaptedTvSeason(tvSeason).getSeasonNumber(), 1);
    }

    @Test
    void getTotalNumOfEpisodes() {
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("Test"));
        Episode episode = new Episode("Test", 1);
        ArrayList<Episode> episodeArrayList = new ArrayList<>();
        episodeArrayList.add(episode);
        TvSeason tvSeason = new TvSeason(1, 1, episodeArrayList);
        assertEquals(new JsonAdaptedTvSeason(tvSeason).getTotalNumOfEpisodes(), 1);
    }

    @Test
    void getEpisodes() {
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("Test"));
        Episode episode = new Episode("Test", 1);
        ArrayList<Episode> episodeArrayList = new ArrayList<>();
        episodeArrayList.add(episode);
        TvSeason tvSeason = new TvSeason(1, 1, episodeArrayList);
        JsonAdaptedEpisode jsonAdaptedEpisode = new JsonAdaptedEpisode("ep", 1);
        ArrayList<JsonAdaptedEpisode> adaptedEpisodes = new ArrayList<>();
        adaptedEpisodes.add(jsonAdaptedEpisode);
        JsonAdaptedTvSeason jsonAdaptedTvSeason = new JsonAdaptedTvSeason(1, 1, adaptedEpisodes);
        assertEquals(jsonAdaptedTvSeason.getEpisodes(), adaptedEpisodes);
    }

    @Test
    void toModelType() {
        JsonAdaptedEpisode jsonAdaptedEpisode = new JsonAdaptedEpisode("ep", 1);
        ArrayList<JsonAdaptedEpisode> adaptedEpisodes = new ArrayList<>();
        adaptedEpisodes.add(jsonAdaptedEpisode);
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvSeason(-1, 1, adaptedEpisodes).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvSeason(1, -1, adaptedEpisodes).toModelType());

    }
}
