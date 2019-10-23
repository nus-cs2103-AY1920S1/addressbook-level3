package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Description;
import seedu.ezwatchlist.model.show.IsWatched;
import seedu.ezwatchlist.model.show.Name;
import seedu.ezwatchlist.model.show.Poster;
import seedu.ezwatchlist.model.show.RunningTime;
import seedu.ezwatchlist.model.show.Show;

/**
 * Jackson-friendly version of {@link Show}.
 */
class JsonAdaptedShow {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final List<JsonAdaptedTvShow> tvShows = new ArrayList<>();
    private final List<JsonAdaptedMovie> movies = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedShow} with the given show details.
     */
    @JsonCreator
    public JsonAdaptedShow(@JsonProperty("tvShows")  List<JsonAdaptedTvShow> tvShows,
                           @JsonProperty("tvShows")  List<JsonAdaptedMovie> movies) {
        if (tvShows != null) {
            this.tvShows.addAll(tvShows);
        }
        if (movies != null) {
            this.movies.addAll(movies);
        }
    }

    /**
     * Converts a given {@code Show} into this class for Jackson use.
     */
    public JsonAdaptedShow(List<Show> source) {
        for (Show show : source) {
            if (show.getType().equals("Movie")) {
                movies.add(new JsonAdaptedMovie(show));
            } else {
                tvShows.add(new JsonAdaptedTvShow(show));
            }
        }
    }

    public List<JsonAdaptedTvShow> getTvShows() {
        return tvShows;
    }

    public List<JsonAdaptedMovie> getMovies() {
        return movies;
    }

    /**
     * Converts this Jackson-friendly adapted show object into the model's {@code Show} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted show.
     */
    public List<Show> toModelType() throws IllegalValueException {
        final List<Show> shows = new ArrayList<>();
        for (JsonAdaptedTvShow tvShow : tvShows) {
            shows.add(tvShow.toModelType());
        }
        for (JsonAdaptedMovie movie : movies) {
            shows.add(movie.toModelType());
        }
        return shows;
    }
}
