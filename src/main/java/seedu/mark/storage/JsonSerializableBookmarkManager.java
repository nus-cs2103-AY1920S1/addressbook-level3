package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.BookmarkManager;
import seedu.mark.model.ReadOnlyBookmarkManager;
import seedu.mark.model.bookmark.Bookmark;

/**
 * An Immutable BookmarkManager that is serializable to JSON format.
 */
@JsonRootName(value = "mark")
class JsonSerializableBookmarkManager {

    public static final String MESSAGE_DUPLICATE_BOOKMARK = "Bookmarks list contains duplicate bookmark(s).";

    private final List<JsonAdaptedBookmark> bookmarks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBookmarkManager} with the given bookmarks.
     */
    @JsonCreator
    public JsonSerializableBookmarkManager(@JsonProperty("bookmarks") List<JsonAdaptedBookmark> bookmarks) {
        this.bookmarks.addAll(bookmarks);
    }

    /**
     * Converts a given {@code ReadOnlyBookmarkManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBookmarkManager}.
     */
    public JsonSerializableBookmarkManager(ReadOnlyBookmarkManager source) {
        bookmarks.addAll(source.getBookmarkList().stream().map(JsonAdaptedBookmark::new).collect(Collectors.toList()));
    }

    /**
     * Converts this bookmark manager into the model's {@code BookmarkManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BookmarkManager toModelType() throws IllegalValueException {
        BookmarkManager bookmarkManager = new BookmarkManager();
        for (JsonAdaptedBookmark jsonAdaptedBookmark : bookmarks) {
            Bookmark bookmark = jsonAdaptedBookmark.toModelType();
            if (bookmarkManager.hasBookmark(bookmark)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOKMARK);
            }
            bookmarkManager.addBookmark(bookmark);
        }
        return bookmarkManager;
    }

}
