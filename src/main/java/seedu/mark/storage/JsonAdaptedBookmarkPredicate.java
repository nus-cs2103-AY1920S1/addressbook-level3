package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.model.predicates.BookmarkPredicate;

/**
 * Jackson-friendly version of {@link BookmarkPredicate}.
 */
class JsonAdaptedBookmarkPredicate {

    private final List<String> nameKeywords = new ArrayList<>();
    private final List<String> notNameKeywords = new ArrayList<>();
    private final List<String> urlKeywords = new ArrayList<>();
    private final List<String> notUrlKeywords = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBookmarkPredicate} with the given predicate details.
     */
    @JsonCreator
    public JsonAdaptedBookmarkPredicate(@JsonProperty("name") List<String> name,
                                        @JsonProperty("notName") List<String> notName,
                                        @JsonProperty("url") List<String> url,
                                        @JsonProperty("notUrl") List<String> notUrl) {
        this.nameKeywords.addAll(name);
        this.notNameKeywords.addAll(notName);
        this.urlKeywords.addAll(url);
        this.notUrlKeywords.addAll(notUrl);
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
