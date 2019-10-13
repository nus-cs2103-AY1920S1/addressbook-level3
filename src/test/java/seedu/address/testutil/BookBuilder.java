package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.genre.Genre;
import seedu.address.model.loan.Loan;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class BookBuilder {

    public static final String DEFAULT_TITLE = "Harry Botter";
    public static final String DEFAULT_SERIAL_NUMBER = "B00001";
    public static final String DEFAULT_AUTHOR = "J K Rowling";

    private Title title;
    private SerialNumber serialNumber;
    private Author author;
    private Optional<Loan> loan;
    private Set<Genre> genres;

    public BookBuilder() {
        title = new Title(DEFAULT_TITLE);
        serialNumber = new SerialNumber(DEFAULT_SERIAL_NUMBER);
        author = new Author(DEFAULT_AUTHOR);
        loan = Optional.ofNullable(null);
        genres = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        title = bookToCopy.getTitle();
        serialNumber = bookToCopy.getSerialNumber();
        author = bookToCopy.getAuthor();
        loan = bookToCopy.getLoan();
        genres = new HashSet<>(bookToCopy.getGenres());
    }

    /**
     * Sets the {@code Title} of the {@code Book} that we are building.
     */
    public BookBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code genres} into a {@code Set<Genre>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withGenres(String ... tags) {
        this.genres = SampleDataUtil.getGenreSet(tags);
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code Book} that we are building.
     */
    public BookBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = new SerialNumber(serialNumber);
        return this;
    }

    /**
     * Sets the {@code Loan} of the {@code Book} that we are building.
     */
    public BookBuilder withLoan(Loan loan) {
        this.loan = Optional.of(loan);
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code Book} that we are building.
     */
    public BookBuilder withAuthor(String author) {
        this.author = new Author(author);
        return this;
    }

    /**
     * Returns a Book object based on specified fields.
     * @return Book object.
     */
    public Book build() {
        Loan loanValue;
        if (!loan.isPresent()) {
            loanValue = null;
        } else {
            loanValue = loan.get();
        }
        return new Book(title, serialNumber, author, loanValue, genres);
    }

}
