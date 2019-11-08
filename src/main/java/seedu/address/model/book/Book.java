package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.genre.Genre;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanList;

/**
 * Represents a Book in the Catalog.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book implements Comparable<Book> {
    private static final Loan NULL_LOAN = null;

    // Identity fields
    private final Title title;
    private final SerialNumber serialNumber;
    private final Author author;
    private final Set<Genre> genres = new HashSet<>();
    private final Optional<Loan> loan;
    private final LoanList loanHistory;

    public Book(Title title, SerialNumber serialNumber, Author author, Loan loan, Set<Genre> genres) {
        this(title, serialNumber, author, loan, genres, new LoanList());
    }

    /**
     * Constructor when loading the file from history or when loading sample data/tests.
     */
    public Book(Title title, SerialNumber serialNumber, Author author,
                Loan loan, Set<Genre> genres, LoanList loanHistory) {
        requireAllNonNull(title, serialNumber, author, genres);
        this.title = title;
        this.serialNumber = serialNumber;
        this.author = author;
        this.genres.addAll(genres);
        this.loan = Optional.ofNullable(loan);
        this.loanHistory = loanHistory;
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

    public LoanList getLoanHistory() {
        return loanHistory;
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
     * Check if a book is currently overdue.
     *
     * @return true if book is on loan and overdue.
     */
    public boolean isOverdue() {
        if (this.loan.isEmpty()) {
            //a book without loan cannot be overdue
            return false;
        } else {
            return this.loan.get().getDueDate().compareTo(DateUtil.getTodayDate()) < 0;
        }
    }

    /**
     * Marks a book as loaned out with the given {@code Loan}.
     *
     * @param loan {@code Loan} object associated with the loan.
     * @return a new {@code Book} object marked as loaned out.
     */
    public Book loanOut(Loan loan) {
        assert !this.isCurrentlyLoanedOut() : "Book is already on loan.";
        return new Book(
                this.getTitle(),
                this.getSerialNumber(),
                this.getAuthor(),
                loan,
                this.getGenres(),
                this.getLoanHistory());
    }

    /**
     * Marks a book as returned.
     *
     * @return a new {@code Book} object marked as returned.
     */
    public Book returnBook() {
        assert this.isCurrentlyLoanedOut() : "Book is not on loan.";
        return new Book(
                this.getTitle(),
                this.getSerialNumber(),
                this.getAuthor(),
                NULL_LOAN,
                this.getGenres(),
                this.getLoanHistory());
    }

    /**
     * Renews a book.
     * @param renewedLoan renewed loan to be updated in the book's loan history.
     * @return a new renewed book.
     */
    public Book renewBook(Loan renewedLoan) {
        assert this.isCurrentlyLoanedOut() : "Book is not on loan.";
        Book holdingBook = new Book(
                this.getTitle(),
                this.getSerialNumber(),
                this.getAuthor(),
                renewedLoan,
                this.getGenres(),
                this.getLoanHistory());
        return holdingBook
                .deleteFromLoanHistory(this.getLoan().get())
                .addToLoanHistory(renewedLoan);
    }

    /**
     * Adds to the loan history of a book.
     *
     * @param loan {@code Loan} to be added into the history.
     * @return a new {@code Book} with the added loan history.
     */
    public Book addToLoanHistory(Loan loan) {
        LoanList newHistory = new LoanList();
        newHistory.add(loan);
        this.getLoanHistory().forEach(newHistory::add);
        return new Book(
                this.getTitle(),
                this.getSerialNumber(),
                this.getAuthor(),
                this.getLoan().orElse(NULL_LOAN),
                this.getGenres(),
                newHistory);
    }

    /**
     * Adds to the loan history of a book
     *
     * @param loanList {@code LoanList} to be added into the history
     */
    public Book addToLoanHistory(LoanList loanList) {
        this.getLoanHistory().forEach(loanList::add);
        return new Book(
                this.getTitle(),
                this.getSerialNumber(),
                this.getAuthor(),
                this.getLoan().orElse(NULL_LOAN),
                this.getGenres(),
                loanList);
    }

    /**
     * Deletes from the loan history of a book.
     *
     * @param loan {@code Loan} to be added into the history.
     * @return a new {@code Book} with the added loan history.
     */
    public Book deleteFromLoanHistory(Loan loan) {
        assert getLoanHistory().contains(loan) : "Loan history does not contain loan to be deleted.";
        LoanList newHistory = new LoanList();
        this.getLoanHistory().forEach(currentLoan -> {
            if (!currentLoan.equals(loan)) {
                newHistory.add(currentLoan);
            }
        });
        return new Book(
                this.getTitle(),
                this.getSerialNumber(),
                this.getAuthor(),
                this.getLoan().orElse(NULL_LOAN),
                this.getGenres(),
                newHistory);
    }


    public int compareTo(Book b) {
        return this.getSerialNumber().compareTo(b.getSerialNumber());
    }

    /**
     * Checks if a book is same, regardless of serial number.
     *
     * @param b Book to be checked against
     * @return true if both books are the same, even if they are different copies
     */
    public boolean isSameBook(Object b) {
        if (b == this) {
            return true;
        }

        if (!(b instanceof Book)) {
            return false;
        }
        Book otherBook = (Book) b;
        //same book different copy
        return otherBook.getTitle().equals(getTitle())
                && otherBook.getAuthor().equals(getAuthor())
                && otherBook.getGenres().equals(getGenres());
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
        //strictly same book, ignoring loan
        return otherBook.getSerialNumber().equals(getSerialNumber())
                && otherBook.getTitle().equals(getTitle())
                && otherBook.getAuthor().equals(getAuthor())
                && otherBook.getGenres().equals(getGenres());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, serialNumber, author, genres);
    }

    /**
     * Returns display string of Book.
     *
     * @return display string of book.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[" + getSerialNumber() + "] ")
                .append("\"" + getTitle() + "\"")
                .append(" by ")
                .append(getAuthor());
        return builder.toString();
    }

}
