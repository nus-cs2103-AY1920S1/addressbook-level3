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
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.folderstructure.FolderStructure;

/**
 * An Immutable Mark that is serializable to JSON format.
 */
@JsonRootName(value = "mark")
class JsonSerializableMark {

    public static final String MESSAGE_DUPLICATE_BOOKMARK = "Bookmark list contains duplicate bookmark(s).";
    public static final String MESSAGE_DUPLICATE_FOLDER = "There are duplicate folder(s).";
    public static final String MESSAGE_NO_ROOT_FOLDER = "The root folder is missing.";

    private final List<JsonAdaptedBookmark> bookmarks = new ArrayList<>();
    private final JsonAdaptedFolderStructure folderStructure;

    /**
     * Constructs a {@code JsonSerializableMark} with the given bookmarks.
     */
    @JsonCreator
    public JsonSerializableMark(@JsonProperty("bookmarks") List<JsonAdaptedBookmark> bookmarks,
        @JsonProperty("folderStructure") JsonAdaptedFolderStructure folderStructure) {
        this.bookmarks.addAll(bookmarks);
        this.folderStructure = folderStructure;
    }

    /**
     * Converts a given {@code ReadOnlyMark} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code
     *               JsonSerializableMark}.
     */
    public JsonSerializableMark(ReadOnlyMark source) {
        bookmarks.addAll(source.getBookmarkList().stream().map(JsonAdaptedBookmark::new)
            .collect(Collectors.toList()));
        folderStructure = new JsonAdaptedFolderStructure(source.getFolderStructure());
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
        FolderStructure modelFolderStructure = folderStructure.toModelType();
        if (!modelFolderStructure.getName().equals(Folder.DEFAULT_FOLDER_NAME)) {
            throw new IllegalValueException(MESSAGE_NO_ROOT_FOLDER);
        }
        if (!FolderStructure.isValidFolderStructure(modelFolderStructure)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_FOLDER);
        }
        mark.setFolderStructure(modelFolderStructure);
        return mark;
    }

}
