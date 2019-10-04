package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.genre.Genre;
import seedu.address.model.loan.Loan;

/**
 * Represents a Book in the Catalog.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    // Identity fields
    private final Title title;
    private final SerialNumber serialNumber;
    private final Author author;
    private final Set<Genre> genres = new HashSet<>();

    private Optional<Loan> loan;

    /**
     * Constructor when loading the file from history or when loading sample data/tests.
     */
    public Book(Title title, SerialNumber serialNumber, Author author, Loan loan, Set<Genre> genres) {
        requireAllNonNull(title, serialNumber, author, genres);
        this.title = title;
        this.serialNumber = serialNumber;
        this.author = author;
        this.loan = Optional.ofNullable(loan);
        this.genres.addAll(genres);
    }

    public Title getTitle() {
        return title;
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public Author getAuthor() {
        return author;
    }

    /**
     * Returns an immutable genre set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    /**
     * Returns an optional of Loan object. If book is not loaned, Optional will be null.
     * @return Optional of Loan object.
     */
    public Optional<Loan> getLoan() {
        return loan;
    }

    /**
     * Loans book out.
     *
     * @param borrowerId Id of Borrower.
     * @param startDate Date when the book is loaned out.
     * @param dueDate Date when the book is due to return.
     */
    public void loanTo(BorrowerId borrowerId, LocalDate startDate, LocalDate dueDate) {
        assert (!this.loan.isPresent()) : "Book not availble for loan";
        Loan currentLoan = new Loan(serialNumber, borrowerId, startDate, dueDate);
        this.loan = Optional.of(currentLoan);
    }

    /**
     * Returns true if book is currently on loan.
     *
     * @return true if book is currently on loan.
     */
    public boolean isCurrentlyLoanedOut() {
        return this.loan.isPresent();
    }

    /**
     * Returns true if both books have the same identity and data fields.
     * This defines a stronger notion of equality between two books.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;
        // allow for same books but different copies
        return otherBook.getSerialNumber().equals(getSerialNumber());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, serialNumber, author, genres);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Serial Number: ")
                .append(getSerialNumber())
                .append(" Author: ")
                .append(getAuthor());
        if (!getGenres().isEmpty()) {
            builder.append(" Genres: ");
            getGenres().forEach(genre -> builder.append(genre + " "));
        }
        return builder.toString();
    }

}
