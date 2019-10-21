package seedu.algobase.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonAdaptedNameContainsKeywordsPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT =
        "NameContainsKeywordsPredicate's %s field is missing!";

    private final List<String> keywords;

    @JsonCreator
    public JsonAdaptedNameContainsKeywordsPredicate(@JsonProperty("keywords") List<String> keywords) {
        this.keywords = keywords;
    }

    // public JsonAdaptedNameContainsKeywordsPredicate(NameContainsKeywordsPredicate predicate) {
    //     this.keywords = predicate.getKeywords();
    // }
    //
    // public NameContainsKeywordsPredicate toModelType() {
    //     return new NameContainsKeywordsPredicate(keywords);
    // }

}
