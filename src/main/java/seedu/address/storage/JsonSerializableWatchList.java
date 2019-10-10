package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.WatchList;
import seedu.address.model.ReadOnlyWatchList;
import seedu.address.model.show.Show;

/**
 * An Immutable WatchList that is serializable to JSON format.
 */
@JsonRootName(value = "watchlist")
class JsonSerializableWatchList {

    public static final String MESSAGE_DUPLICATE_SHOW = "Show list contains duplicate show(s).";

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
        shows.addAll(source.getShowList().stream().map(JsonAdaptedShow::new).collect(Collectors.toList()));
    }

    /**
     * Converts this watchlist into the model's {@code WatchList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public WatchList toModelType() throws IllegalValueException {
        WatchList watchList = new WatchList();
        for (JsonAdaptedShow jsonAdaptedShow : shows) {
            Show show = jsonAdaptedShow.toModelType();
            if (watchList.hasShow(show)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SHOW);
            }
            watchList.addShow(show);
        }
        return watchList;
    }

}

