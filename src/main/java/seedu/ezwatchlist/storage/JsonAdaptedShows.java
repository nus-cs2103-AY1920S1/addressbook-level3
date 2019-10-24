package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Show;

/**
 * Jackson-friendly version of list of shows.
 */
class JsonAdaptedShows {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Show's %s field is missing!";

    private final List<JsonAdaptedTvShow> tvShows = new ArrayList<>();
    private final List<JsonAdaptedMovie> movies = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedShows} with the given show details.
     */
    @JsonCreator
    public JsonAdaptedShows(@JsonProperty("tvShows") List<JsonAdaptedTvShow> tvShows,
                            @JsonProperty("movies") List<JsonAdaptedMovie> movies) {
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
    public JsonAdaptedShows(List<Show> source) {
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
