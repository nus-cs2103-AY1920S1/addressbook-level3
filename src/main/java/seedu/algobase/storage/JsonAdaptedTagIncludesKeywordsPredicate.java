package seedu.algobase.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;

public class JsonAdaptedTagIncludesKeywordsPredicate {

    private final List<JsonAdaptedKeyword> keywords;

    @JsonCreator
    public JsonAdaptedTagIncludesKeywordsPredicate(@JsonProperty("keywords") List<JsonAdaptedKeyword> keywords) {
        this.keywords = keywords;
    }

    public JsonAdaptedTagIncludesKeywordsPredicate(TagIncludesKeywordsPredicate predicate) {
        this.keywords = predicate.getKeywords().stream().map(JsonAdaptedKeyword::new).collect(Collectors.toList());
    }

    public TagIncludesKeywordsPredicate toModelType() throws IllegalValueException {
        final List<Keyword> predicateKeywords = new ArrayList<>();

        for (JsonAdaptedKeyword keyword: keywords) {
            predicateKeywords.add(keyword.toModelType());
        }

        return new TagIncludesKeywordsPredicate(predicateKeywords);
    }

}
