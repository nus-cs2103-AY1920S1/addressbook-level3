package seedu.ezwatchlist.storage;

import seedu.ezwatchlist.commons.exceptions.IllegalValueException;
import seedu.ezwatchlist.model.show.Show;

/**
 * Jackson-friendly version of {@link Show}.
 */
abstract class JsonAdaptedShow {

    /**
     * Converts this Jackson-friendly adapted show object into the model's {@code Show} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted show.
     */
    public abstract Show toModelType() throws IllegalValueException;
}
