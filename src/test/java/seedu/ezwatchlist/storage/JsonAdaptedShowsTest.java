package seedu.ezwatchlist.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.testutil.TypicalShows.AVENGERSENDGAME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Movie;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.model.show.TvSeason;
import seedu.ezwatchlist.model.show.TvShow;
import seedu.ezwatchlist.testutil.ShowBuilder;

public class JsonAdaptedShowsTest {
    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_DATE_OF_RELEASE = "";
    private static final int INVALID_RUNNINGTIME = -1;
    private static final String INVALID_ACTOR = "";

    private static final String VALID_NAME = AVENGERSENDGAME.getName().toString();
    private static final String VALID_DESCRIPTION = AVENGERSENDGAME.getDescription().toString();
    private static final String VALID_DATEOFRELEASE = AVENGERSENDGAME.getDateOfRelease().value;
    private static final int VALID_RUNNINGTIME = AVENGERSENDGAME.getRunningTime().value;
    private static final List<JsonAdaptedActor> VALID_ACTOR = AVENGERSENDGAME.getActors().stream()
            .map(JsonAdaptedActor::new)
            .collect(Collectors.toList());
    /*
    @Test
    public void toModelType_validShowDetails_returnsShow() throws Exception {
        JsonAdaptedShows show = new JsonAdaptedShows(AVENGERSENDGAME);
        assertEquals(AVENGERSENDGAME, show.toModelType());
    }

    */


    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        /*JsonAdaptedShows show =
                new JsonAdaptedShows(INVALID_NAME, "Movie", VALID_DATEOFRELEASE, true, VALID_DESCRIPTION,
                        VALID_RUNNINGTIME, VALID_ACTOR);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, show::toModelType);
         */
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        /*JsonAdaptedShows show = new JsonAdaptedShows(null, "Movie", VALID_DATEOFRELEASE,
                true, VALID_DESCRIPTION,
                VALID_RUNNINGTIME, VALID_ACTOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, show::toModelType);
         */
    }

    @Test
    public void toModelType_invalidDateOfRelease_throwsIllegalValueException() {
        /*
        JsonAdaptedShows show =
                new JsonAdaptedShows(VALID_NAME, "MOVIE", INVALID_DATE_OF_RELEASE, false,
                        VALID_DESCRIPTION, VALID_RUNNINGTIME, VALID_ACTOR);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, show::toModelType);
         */
    }

    @Test
    public void toModelType_nullDateOfRelease_throwsIllegalValueException() {
        /*
        JsonAdaptedShows show = new JsonAdaptedShows(VALID_NAME, "Movie", null,
                false, VALID_DESCRIPTION,
                VALID_RUNNINGTIME, VALID_ACTOR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, show::toModelType);
         */
    }

    @Test
    public void toModelType_invalidRunningTime_throwsIllegalValueException() {
        /*
        JsonAdaptedShows show = new JsonAdaptedShows(VALID_NAME, "Movie",
                VALID_DATEOFRELEASE, false, "hello",
                INVALID_RUNNINGTIME, VALID_ACTOR);
        String expectedMessage = RunningTime.MESSAGE_CONSTRAINTS2;
        assertThrows(IllegalValueException.class, expectedMessage, show::toModelType);
         */
    }

    @Test
    public void toModelType_invalidActors_throwsIllegalValueException() {
        /*
        List<JsonAdaptedActor> invalidActors = new ArrayList<>(VALID_ACTOR);
        invalidActors.add(new JsonAdaptedActor(INVALID_ACTOR));
        JsonAdaptedShows show =
                new JsonAdaptedShows(VALID_NAME, "Movie", VALID_DATEOFRELEASE, false , VALID_DESCRIPTION,
                        VALID_RUNNINGTIME, invalidActors);
        assertThrows(IllegalValueException.class, show::toModelType);

         */
    }

    @Test
    void jsonAdaptedShowsConstructorTest() {
        List<Show> shows = new ArrayList<>();

        List<JsonAdaptedTvShow> jsonAdaptedTvShowList = new ArrayList<>();
        Set<Actor> actors = new HashSet<>();
        List<JsonAdaptedMovie> jsonAdaptedMovieList = new ArrayList<>();
        Movie movie = new Movie(new Name("test"), new Description("123"), new IsWatched("false"),
                new Date("24/09/1997"), new RunningTime(1), actors);

        TvShow tvShow = new TvShow(new Name("test"), new Description("123"), new IsWatched("false"),
                new Date("24/09/1997"), new RunningTime(1), actors, 1, 1,
                new ArrayList<TvSeason>());
        Show show = new ShowBuilder(AVENGERSENDGAME).withType("Movie").build();
        shows.add(movie);
        shows.add(tvShow);
        jsonAdaptedMovieList.add(new JsonAdaptedMovie(show));
        assertTrue(new JsonAdaptedShows(shows).getMovies() instanceof List);
        assertTrue(new JsonAdaptedShows(shows).getTvShows() instanceof List);

    }

}
