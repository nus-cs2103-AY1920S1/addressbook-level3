package seedu.address.storage.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.book.Book;

/**
 * An Immutable Catalog that is serializable to JSON format.
 */
@JsonRootName(value = "Catalog")
public class JsonSerializableCatalog {

    public static final String MESSAGE_DUPLICATE_BOOK = "books list contains duplicate book(s).";

    private final List<JsonAdaptedBook> books = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCatalog} with the given books.
     */
    @JsonCreator
    public JsonSerializableCatalog(@JsonProperty("books") List<JsonAdaptedBook> books) {
        this.books.addAll(books);
    }

    /**
     * Converts a given {@code ReadOnlyCatalog} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCatalog}.
     */
    public JsonSerializableCatalog(ReadOnlyCatalog source) {
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this catalog into the model's {@code Catalog} object.
     * Uses an empty LoanRecords.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Catalog toModelType() throws IllegalValueException {
        return toModelType(new LoanRecords());
    }

    /**
     * Converts this catalog into the model's {@code Catalog} object.
     * Loan objects of the Books are taken from the initialLoanRecords.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Catalog toModelType(ReadOnlyLoanRecords initialLoanRecords) throws IllegalValueException {
        Catalog catalog = new Catalog();
        for (JsonAdaptedBook jsonAdaptedBook : books) {
            Book book = jsonAdaptedBook.toModelType(initialLoanRecords);
            if (catalog.hasBook(book)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOK);
            }
            catalog.addBook(book);
        }
        return catalog;
    }

}
