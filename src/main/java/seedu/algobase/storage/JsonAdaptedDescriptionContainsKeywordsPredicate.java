package seedu.algobase.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonAdaptedDescriptionContainsKeywordsPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT =
        "DescriptionContainsKeywordsPredicate's %s field is missing!";

    private final List<String> keywords;

    @JsonCreator
    public JsonAdaptedDescriptionContainsKeywordsPredicate(@JsonProperty("keywords") List<String> keywords) {
        this.keywords = keywords;
    }

    // public JsonAdaptedDescriptionContainsKeywordsPredicate(DescriptionContainsKeywordsPredicate predicate) {
    //     this.keywords = predicate.getKeywords();
    // }
    //
    // public DescriptionContainsKeywordsPredicate toModelType() {
    //     return new DescriptionContainsKeywordsPredicate(keywords);
    // }
}
