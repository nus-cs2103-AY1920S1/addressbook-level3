package seedu.algobase.storage;

import static seedu.algobase.storage.util.StorageUtil.getKeywordsFromJson;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;

/**
 * Jackson-friendly version of {@link DescriptionContainsKeywordsPredicate}.
 */
public class JsonAdaptedDescriptionContainsKeywordsPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT =
        "DescriptionContainsKeywordsPredicate's %s field is missing!";
    private final List<JsonAdaptedKeyword> keywords;

    /**
     * Constructs a {@code JsonAdaptedDescriptionContainsKeywordsPredicate} with the given
     * DescriptionContainsKeywordsPredicate details.
     */
    @JsonCreator
    public JsonAdaptedDescriptionContainsKeywordsPredicate(
        @JsonProperty("keywords") List<JsonAdaptedKeyword> keywords) {
        this.keywords = keywords;
    }

    /**
     * Converts a given {@code DescriptionContainsKeywordsPredicate} into this class for Jackson use.
     */
    public JsonAdaptedDescriptionContainsKeywordsPredicate(DescriptionContainsKeywordsPredicate predicate) {
        this.keywords = predicate.getKeywords().stream()
            .map(JsonAdaptedKeyword::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code DescriptionContainsKeywordsPredicate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * DescriptionContainsKeywordsPredicate.
     */
    public DescriptionContainsKeywordsPredicate toModelType() throws IllegalValueException {
        return new DescriptionContainsKeywordsPredicate(getKeywordsFromJson(keywords, MISSING_FIELD_MESSAGE_FORMAT));
    }
}
