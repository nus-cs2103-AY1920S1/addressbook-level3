package seedu.address.storage.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.genre.Genre;

/**
 * Jackson-friendly version of {@link Genre}.
 */
public class JsonAdaptedGenre {

    private final String genreName;

    /**
     * Constructs a {@code JsonAdaptedGenre} with the given {@code genreName}.
     */
    @JsonCreator
    public JsonAdaptedGenre(String genreName) {
        this.genreName = genreName;
    }

    /**
     * Converts a given {@code Genre} into this class for Jackson use.
     */
    public JsonAdaptedGenre(Genre source) {
        genreName = source.genreName;
    }

    @JsonValue
    public String getGenreName() {
        return genreName;
    }

    /**
     * Converts this Jackson-friendly adapted genre object into the model's {@code Genre} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted genre.
     */
    public Genre toModelType() throws IllegalValueException {
        if (!Genre.isValidGenreName(genreName)) {
            throw new IllegalValueException(Genre.MESSAGE_CONSTRAINTS);
        }
        return new Genre(genreName);
    }

}
