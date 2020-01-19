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
    private final List<String> folderNames = new ArrayList<>();
    private final List<String> notFolderNames = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBookmarkPredicate} with the given predicate details.
     */
    @JsonCreator
    public JsonAdaptedBookmarkPredicate(@JsonProperty("nameKeywords") List<String> nameKeywords,
                                        @JsonProperty("notNameKeywords") List<String> notNameKeywords,
                                        @JsonProperty("urlKeywords") List<String> urlKeywords,
                                        @JsonProperty("notUrlKeywords") List<String> notUrlKeywords,
                                        @JsonProperty("folderNames") List<String> folderNames,
                                        @JsonProperty("notFolderNames") List<String> notFolderNames) {
        this.nameKeywords.addAll(nameKeywords);
        this.notNameKeywords.addAll(notNameKeywords);
        this.urlKeywords.addAll(urlKeywords);
        this.notUrlKeywords.addAll(notUrlKeywords);
        this.folderNames.addAll(folderNames);
        this.notFolderNames.addAll(notFolderNames);
    }

    /**
     * Converts a given {@code BookmarkPredicate} into this class for Jackson use.
     */
    public JsonAdaptedBookmarkPredicate(BookmarkPredicate source) {
        nameKeywords.addAll(source.getNameKeywords());
        notNameKeywords.addAll(source.getNotNameKeywords());
        urlKeywords.addAll(source.getUrlKeywords());
        notUrlKeywords.addAll(source.getNotUrlKeywords());
        folderNames.addAll(source.getFolderNames());
        notFolderNames.addAll(source.getNotFolderNames());
    }

    /**
     * Converts this Jackson-friendly adapted bookmark object into the model's
     * {@code BookmarkPredicate} object.
     */
    public BookmarkPredicate toModelType() {
        BookmarkPredicate predicate = new BookmarkPredicate();

        if (!nameKeywords.isEmpty()) {
            predicate = predicate.withNameKeywords(nameKeywords);
        }
        if (!notNameKeywords.isEmpty()) {
            predicate = predicate.withoutNameKeywords(notNameKeywords);
        }
        if (!urlKeywords.isEmpty()) {
            predicate = predicate.withUrlKeywords(urlKeywords);
        }
        if (!notUrlKeywords.isEmpty()) {
            predicate = predicate.withoutUrlKeywords(notUrlKeywords);
        }
        if (!folderNames.isEmpty()) {
            predicate = predicate.withFolder(folderNames);
        }
        if (!notFolderNames.isEmpty()) {
            predicate = predicate.withoutFolder(notFolderNames);
        }

        return predicate;
    }
}
