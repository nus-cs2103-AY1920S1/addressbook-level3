package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;

/**
 * Jackson-friendly version of {@link SourceMatchesKeywordPredicate}.
 */
public class JsonAdaptedSourceMatchesKeywordPredicate {

    private final JsonAdaptedKeyword keyword;

    /**
     * Constructs a {@code JsonAdaptedSourceMatchesKeywordPredicate} with the given
     * SourceMatchesKeywordPredicate details.
     */
    @JsonCreator
    public JsonAdaptedSourceMatchesKeywordPredicate(@JsonProperty("keyword") JsonAdaptedKeyword keyword) {
        this.keyword = keyword;
    }

    /**
     * Converts a given {@code SourceMatchesKeywordPredicate} into this class for Jackson use.
     */
    public JsonAdaptedSourceMatchesKeywordPredicate(SourceMatchesKeywordPredicate predicate) {
        this.keyword = new JsonAdaptedKeyword(predicate.getKeyword());
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code SourceMatchesKeywordPredicate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * SourceMatchesKeywordPredicate.
     */
    public SourceMatchesKeywordPredicate toModelType() throws IllegalValueException {
        return new SourceMatchesKeywordPredicate(keyword.toModelType());
    }

}
