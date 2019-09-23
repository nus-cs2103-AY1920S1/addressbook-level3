package seedu.address.storage.catalogue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Catalogue;
import seedu.address.model.ReadOnlyCatalogue;
import seedu.address.model.book.Book;

/**
 * An Immutable Catalogue that is serializable to JSON format.
 */
@JsonRootName(value = "Catalogue")
class JsonSerializableCatalogue {

    public static final String MESSAGE_DUPLICATE_book = "books list contains duplicate book(s).";

    private final List<JsonAdaptedBook> books = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCatalogue} with the given books.
     */
    @JsonCreator
    public JsonSerializableCatalogue(@JsonProperty("books") List<JsonAdaptedBook> books) {
        this.books.addAll(books);
    }

    /**
     * Converts a given {@code ReadOnlyCatalogue} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCatalogue}.
     */
    public JsonSerializableCatalogue(ReadOnlyCatalogue source) {
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Catalogue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Catalogue toModelType() throws IllegalValueException {
        Catalogue Catalogue = new Catalogue();
        for (JsonAdaptedBook jsonAdaptedBook : books) {
            Book book = jsonAdaptedBook.toModelType();
            if (Catalogue.hasBook(book)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_book);
            }
            Catalogue.addBook(book);
        }
        return Catalogue;
    }

}
