package seedu.algobase.storage;

import static seedu.algobase.storage.util.StorageUtil.getKeywordsFromJson;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;

/**
 * Jackson-friendly version of {@link TagIncludesKeywordsPredicate}.
 */
public class JsonAdaptedTagIncludesKeywordsPredicate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TagIncludesKeywordsPredicate's %s field is missing!";
    private final List<JsonAdaptedKeyword> keywords;

    /**
     * Constructs a {@code JsonAdaptedTagIncludesKeywordsPredicate} with the given
     * TagIncludesKeywordsPredicate details.
     */
    @JsonCreator
    public JsonAdaptedTagIncludesKeywordsPredicate(@JsonProperty("keywords") List<JsonAdaptedKeyword> keywords) {
        this.keywords = keywords;
    }

    /**
     * Converts a given {@code TagIncludesKeywordsPredicate} into this class for Jackson use.
     */
    public JsonAdaptedTagIncludesKeywordsPredicate(TagIncludesKeywordsPredicate predicate) {
        this.keywords = predicate.getKeywords().stream().map(JsonAdaptedKeyword::new).collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's
     * {@code TagIncludesKeywordsPredicate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * TagIncludesKeywordsPredicate.
     */
    public TagIncludesKeywordsPredicate toModelType() throws IllegalValueException {
        return new TagIncludesKeywordsPredicate(getKeywordsFromJson(keywords, MISSING_FIELD_MESSAGE_FORMAT));
    }

}
