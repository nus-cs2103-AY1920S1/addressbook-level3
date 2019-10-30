package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.model.predicates.BookmarkPredicate;

/**
 * Jackson-friendly version of {@link BookmarkPredicate}.
 */
public class JsonAdaptedBookmarkPredicate {

    private final List<String> nameKeywords = new ArrayList<>();
    private final List<String> notNameKeywords = new ArrayList<>();
    private final List<String> urlKeywords = new ArrayList<>();
    private final List<String> notUrlKeywords = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBookmarkPredicate} with the given predicate details.
     */
    @JsonCreator
    public JsonAdaptedBookmarkPredicate(@JsonProperty("nameKeywords") List<String> nameKeywords,
                                        @JsonProperty("notNameKeywords") List<String> notNameKeywords,
                                        @JsonProperty("urlKeywords") List<String> urlKeywords,
                                        @JsonProperty("notUrlKeywords") List<String> notUrlKeywords) {
        this.nameKeywords.addAll(nameKeywords);
        this.notNameKeywords.addAll(notNameKeywords);
        this.urlKeywords.addAll(urlKeywords);
        this.notUrlKeywords.addAll(notUrlKeywords);
    }

    /**
     * Converts a given {@code BookmarkPredicate} into this class for Jackson use.
     */
    public JsonAdaptedBookmarkPredicate(BookmarkPredicate source) {
        nameKeywords.addAll(source.getNameKeywords());
        notNameKeywords.addAll(source.getNotNameKeywords());
        urlKeywords.addAll(source.getUrlKeywords());
        notUrlKeywords.addAll(source.getNotUrlKeywords());
    }

    /**
     * Converts this Jackson-friendly adapted bookmark object into the model's
     * {@code BookmarkPredicate} object.
     */
    public BookmarkPredicate toModelType() {
        return new BookmarkPredicate().withNameKeywords(nameKeywords).withoutNameKeywords(notNameKeywords)
                .withUrlKeywords(urlKeywords).withoutUrlKeywords(notUrlKeywords);
    }
}
