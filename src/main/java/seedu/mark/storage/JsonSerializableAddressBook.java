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
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate bookmark(s).";

    private final List<JsonAdaptedBookmark> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedBookmark> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyBookmarkManager} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyBookmarkManager source) {
        persons.addAll(source.getBookmarkList().stream().map(JsonAdaptedBookmark::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code BookmarkManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BookmarkManager toModelType() throws IllegalValueException {
        BookmarkManager addressBook = new BookmarkManager();
        for (JsonAdaptedBookmark jsonAdaptedBookmark : persons) {
            Bookmark bookmark = jsonAdaptedBookmark.toModelType();
            if (addressBook.hasBookmark(bookmark)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addBookmark(bookmark);
        }
        return addressBook;
    }

}
