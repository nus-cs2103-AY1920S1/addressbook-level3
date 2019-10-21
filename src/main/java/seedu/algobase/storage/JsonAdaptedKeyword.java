package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

public class JsonAdaptedKeyword {

    private final String keyword;

    @JsonCreator
    public JsonAdaptedKeyword(@JsonProperty("keyword") String keyword) {
        this.keyword = keyword;
    }

    public JsonAdaptedKeyword(Keyword keyword) {
        this.keyword = keyword.toString();
    }

    public Keyword toModelType() throws IllegalValueException {
        if (!Keyword.isValidKeyword(keyword)) {
            throw new IllegalValueException(Keyword.MESSAGE_CONSTRAINTS);
        }
        return new Keyword(keyword);
    }

}
