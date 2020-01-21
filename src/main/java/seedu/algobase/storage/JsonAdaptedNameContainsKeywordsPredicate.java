package seedu.algobase.storage;

import static seedu.algobase.storage.util.StorageUtil.getKeywordsFromJson;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;

/**
 * Jackson-friendly version of {@link NameContainsKeywordsPredicate}.
 */
public class JsonAdaptedNameContainsKeywordsPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "NameContainsKeywordsPredicate's %s field is missing!";
    private final List<JsonAdaptedKeyword> keywords;

    /**
     * Constructs a {@code JsonAdaptedNameContainsKeywordsPredicate} with the given
     * NameContainsKeywordsPredicate details.
     */
    @JsonCreator
    public JsonAdaptedNameContainsKeywordsPredicate(@JsonProperty("keywords") List<JsonAdaptedKeyword> keywords) {
        this.keywords = keywords;
    }

    /**
     * Converts a given {@code NameContainsKeywordsPredicate} into this class for Jackson use.
     */
    public JsonAdaptedNameContainsKeywordsPredicate(NameContainsKeywordsPredicate predicate) {
        this.keywords = predicate.getKeywords().stream()
            .map(JsonAdaptedKeyword::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code NameContainsKeywordsPredicate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * NameContainsKeywordsPredicate.
     */
    public NameContainsKeywordsPredicate toModelType() throws IllegalValueException {
        return new NameContainsKeywordsPredicate(getKeywordsFromJson(keywords, MISSING_FIELD_MESSAGE_FORMAT));
    }

}
