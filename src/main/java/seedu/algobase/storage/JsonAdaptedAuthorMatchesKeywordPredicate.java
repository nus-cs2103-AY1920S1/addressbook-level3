package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

/**
 * Jackson-friendly version of {@link AuthorMatchesKeywordPredicate}.
 */
public class JsonAdaptedAuthorMatchesKeywordPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AuthorMatchesKeywordPredicate's %s field is missing!";
    private final JsonAdaptedKeyword keyword;

    /**
     * Constructs a {@code JsonAdaptedAuthorMatchesKeywordPredicate} with the given
     * AuthorMatchesKeywordPredicate details.
     */
    @JsonCreator
    public JsonAdaptedAuthorMatchesKeywordPredicate(@JsonProperty("keyword") JsonAdaptedKeyword keyword) {
        this.keyword = keyword;
    }

    /**
     * Converts a given {@code AuthorMatchesKeywordPredicate} into this class for Jackson use.
     */
    public JsonAdaptedAuthorMatchesKeywordPredicate(AuthorMatchesKeywordPredicate predicate) {
        this.keyword = new JsonAdaptedKeyword(predicate.getKeyword());
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code AuthorMatchesKeywordPredicate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * AuthorMatchesKeywordPredicate.
     */
    public AuthorMatchesKeywordPredicate toModelType() throws IllegalValueException {
        if (keyword == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Keyword.class.getSimpleName()));
        }

        return new AuthorMatchesKeywordPredicate(keyword.toModelType());
    }

}
