package seedu.ezwatchlist.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.Show;

/**
 * An Immutable WatchList that is serializable to JSON format.
 */
@JsonRootName(value = "watchlist")
class JsonSerializableWatchList {

    public static final String MESSAGE_DUPLICATE_SHOW = "Show list contains duplicate show(s).";
    public static final String MESSAGE_INVALID_SHOW_TYPE = "Show list contains shows of unacceptable type.";

    private final List<JsonAdaptedShow> shows = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableWatchList} with the given shows.
     */
    @JsonCreator
    public JsonSerializableWatchList(@JsonProperty("shows") List<JsonAdaptedShow> shows) {
        this.shows.addAll(shows);
    }

    /**
     * Converts a given {@code ReadOnlyWatchList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableWatchList}.
     */
    public JsonSerializableWatchList(ReadOnlyWatchList source) {
        ObservableList<Show> showList = source.getShowList();
        List<JsonAdaptedMovie> movies = new ArrayList<>();
        List<JsonAdaptedTvShow> tvShows = new ArrayList<>();
        for (Show show : showList) {
            if (show.getType().equals("Movie")) {
                movies.add(new JsonAdaptedMovie(show));
            } else if (show.getType().equals("Tv Show")) {
                tvShows.add(new JsonAdaptedTvShow(show));
            }
        }
        shows.set(0, new JsonAdaptedShow(tvShows, movies));
    }

    /**
     * Converts this watchlist into the model's {@code WatchList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WatchList toModelType() throws IllegalValueException {
        WatchList watchList = new WatchList();
        for (JsonAdaptedShow jsonAdaptedShow : shows) {
            List<Show> list = jsonAdaptedShow.toModelType();
            for(Show show : list) {
                if (watchList.hasShow(show)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_SHOW);
                }
                watchList.addShow(show);
            }
        }
        return watchList;
    }

}

