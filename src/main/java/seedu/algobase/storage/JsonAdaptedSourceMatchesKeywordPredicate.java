package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;

public class JsonAdaptedSourceMatchesKeywordPredicate {

    private final JsonAdaptedKeyword keyword;

    @JsonCreator
    public JsonAdaptedSourceMatchesKeywordPredicate(@JsonProperty("keyword") JsonAdaptedKeyword keyword) {
        this.keyword = keyword;
    }

    public JsonAdaptedSourceMatchesKeywordPredicate(SourceMatchesKeywordPredicate predicate) {
        this.keyword = new JsonAdaptedKeyword(predicate.getKeyword());
    }

    public SourceMatchesKeywordPredicate toModelType() throws IllegalValueException {
        return new SourceMatchesKeywordPredicate(keyword.toModelType());
    }

}
