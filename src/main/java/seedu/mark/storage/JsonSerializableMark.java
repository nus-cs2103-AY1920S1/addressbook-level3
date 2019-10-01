package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.Mark;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.bookmark.Bookmark;

/**
 * An Immutable Mark that is serializable to JSON format.
 */
@JsonRootName(value = "mark")
class JsonSerializableMark {

    public static final String MESSAGE_DUPLICATE_BOOKMARK = "Bookmark list contains duplicate bookmark(s).";

    private final List<JsonAdaptedBookmark> bookmarks = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableMark} with the given bookmarks.
     */
    @JsonCreator
    public JsonSerializableMark(@JsonProperty("bookmarks") List<JsonAdaptedBookmark> bookmarks) {
        this.bookmarks.addAll(bookmarks);
    }

    /**
     * Converts a given {@code ReadOnlyMark} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableMark}.
     */
    public JsonSerializableMark(ReadOnlyMark source) {
        bookmarks.addAll(source.getBookmarkList().stream().map(JsonAdaptedBookmark::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Mark into the model's {@code Mark} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Mark toModelType() throws IllegalValueException {
        Mark mark = new Mark();
        for (JsonAdaptedBookmark jsonAdaptedBookmark : bookmarks) {
            Bookmark bookmark = jsonAdaptedBookmark.toModelType();
            if (mark.hasBookmark(bookmark)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOKMARK);
            }
            mark.addBookmark(bookmark);
        }
        return mark;
    }

}
