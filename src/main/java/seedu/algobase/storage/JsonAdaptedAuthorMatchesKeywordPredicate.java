package seedu.algobase.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;

/**
 * Jackson-friendly version of {@link AuthorMatchesKeywordPredicate}.
 */
public class JsonAdaptedAuthorMatchesKeywordPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "AuthorMatchesKeywordPredicate's %s field is missing!";

    private final JsonAdaptedKeyword keyword;

    @JsonCreator
    public JsonAdaptedAuthorMatchesKeywordPredicate(@JsonProperty("keyword") JsonAdaptedKeyword keyword) {
        this.keyword = keyword;
    }

    public JsonAdaptedAuthorMatchesKeywordPredicate(AuthorMatchesKeywordPredicate predicate) {
        this.keyword = new JsonAdaptedKeyword(predicate.getKeyword());
    }

    public AuthorMatchesKeywordPredicate toModelType() throws IllegalValueException {
        return new AuthorMatchesKeywordPredicate(keyword.toModelType());
    }

}
