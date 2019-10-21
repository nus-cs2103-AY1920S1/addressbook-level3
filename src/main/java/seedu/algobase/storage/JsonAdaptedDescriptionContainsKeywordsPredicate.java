package seedu.algobase.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

public class JsonAdaptedDescriptionContainsKeywordsPredicate {

    private final List<JsonAdaptedKeyword> keywords;

    @JsonCreator
    public JsonAdaptedDescriptionContainsKeywordsPredicate(
        @JsonProperty("keywords") List<JsonAdaptedKeyword> keywords) {
        this.keywords = keywords;
    }

    public JsonAdaptedDescriptionContainsKeywordsPredicate(DescriptionContainsKeywordsPredicate predicate) {
        this.keywords = predicate.getKeywords().stream().
            map(JsonAdaptedKeyword::new).
            collect(Collectors.toList());
    }

    public DescriptionContainsKeywordsPredicate toModelType() throws IllegalValueException {
        final List<Keyword> predicateKeywords = new ArrayList<>();

        for (JsonAdaptedKeyword keyword: keywords) {
            predicateKeywords.add(keyword.toModelType());
        }

        return new DescriptionContainsKeywordsPredicate(predicateKeywords);
    }
}
