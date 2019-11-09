package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

/**
 * Jackson-friendly version of {@link Keyword}.
 */
public class JsonAdaptedKeyword {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Keyword's %s field is missing!";
    private final String keyword;

    /**
     * Constructs a {@code JsonAdaptedKeyword} with the given Keyword details.
     */
    @JsonCreator
    public JsonAdaptedKeyword(@JsonProperty("keyword") String keyword) {
        this.keyword = keyword;
    }

    /**
     * Converts a given {@code Keyword} into this class for Jackson use.
     */
    public JsonAdaptedKeyword(Keyword keyword) {
        this.keyword = keyword.toString();
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code Keyword} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * Keyword.
     */
    public Keyword toModelType() throws IllegalValueException {
        if (keyword == null) {
            throw new
                IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Keyword.class.getSimpleName()));
        }
        if (!Keyword.isValidKeyword(keyword)) {
            throw new IllegalValueException(Keyword.MESSAGE_CONSTRAINTS);
        }
        return new Keyword(keyword);
    }

}
