package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.Episode;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.TvSeason;
import seedu.ezwatchlist.model.show.TvShow;

class JsonAdaptedTvShowTest {
    @Test
    void toModelType() throws IllegalValueException {
        List<JsonAdaptedTvShow> jsonAdaptedTvShowList = new ArrayList<>();
        List<JsonAdaptedActor> actorss = new ArrayList<>();
        List<JsonAdaptedTvSeason> tvSeasons = new ArrayList<>();
        List<JsonAdaptedGenre> genres = new ArrayList<>();
        Set<Actor> actors = new HashSet<>();
        actors.add(new Actor("Test"));
        Episode episode = new Episode("Test", 1);
        ArrayList<Episode> episodeArrayList = new ArrayList<>();
        episodeArrayList.add(episode);
        ArrayList<TvSeason> arrayList = new ArrayList<>();
        TvSeason tvSeason = new TvSeason(1, 1, episodeArrayList);
        arrayList.add(tvSeason);
        TvShow tvShow = new TvShow(new Name("test"), new Description("123"), new IsWatched("false"),
            new Date(null), new RunningTime(1), actors, 1, 1, arrayList);

        assertEquals(new JsonAdaptedTvShow(tvShow).toModelType(), tvShow);
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("", "tvshow", "", "true",
                "123", 1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("q", "tvshow", null, "true",
                "123", 1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("q", "tvshow", "2", "",
                "123", 1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("q", "tvshow", "24", "true",
                null, 1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("q", "tvshow", "24", "true",
                "123", -1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("q", "tvshow", "", "true",
                "123", 1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());
        assertThrows(IllegalValueException.class, () -> new JsonAdaptedTvShow("q", "tvshow", "24", "true",
                "", 1, actorss, "poster", 1 , 1, tvSeasons, genres).toModelType());

    }
}
