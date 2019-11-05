package seedu.address.model.book;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.book.Author.isValidAuthor;
import static seedu.address.model.book.SerialNumber.isValidSerialNumber;
import static seedu.address.model.book.Title.isValidTitle;
import static seedu.address.model.genre.Genre.isValidGenreName;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Flag;
import seedu.address.model.genre.Genre;
import seedu.address.model.util.SampleDataUtil;

/**
 * Tests that a {@code Book} matches all of the conditions given.
 * Increases performance by returning whenever a condition is not met
 */
public class BookPredicate implements Predicate<Book> {
    private String title;
    private String author;
    private String serialNumber;
    private Set<Genre> genres;
    private Flag loanState;
    private int displayLimit = -1; // default for show all

    public BookPredicate() {
        this.title = null;
        this.author = null;
        this.serialNumber = null;
        this.genres = null;
        this.loanState = null;
    }

    public boolean isValid() {
        return title != null || author != null || serialNumber != null || genres != null
                || loanState != null || displayLimit != -1;
    }

    @Override
    public boolean test(Book book) {

        if (title != null
                && !Arrays.stream(title.split(" "))
                .allMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(book.getTitle().value, keyword))) {
            return false;
        }
        if (author != null
                && !Arrays.stream(author.split(" "))
                .allMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(book.getAuthor().value, keyword))) {
            return false;
        }
        if (serialNumber != null
                && !Stream.of(serialNumber)
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getSerialNumber().value, keyword))) {
            return false;
        }
        if (genres != null
                && !genres.stream()
                .allMatch(keyword -> book.getGenres().contains(keyword))) {
            return false;
        }
        if ((loanState == Flag.AVAILABLE && book.isCurrentlyLoanedOut())
                || (loanState == Flag.LOANED && !book.isCurrentlyLoanedOut())
                || (loanState == Flag.OVERDUE && !book.isOverdue())) {
            return false;
        }

        if (displayLimit > 0) {
            displayLimit--;
            return true;
        }
        return displayLimit == -1;
    }

    /**
     * add a title predicate to the book predicate
     *
     * @param title
     */
    public BookPredicate setTitle(String title) {
        checkArgument(isValidTitle(title), Title.MESSAGE_CONSTRAINTS);
        this.title = title;
        return this;
    }

    /**
     * add an author predicate to the book predicate
     *
     * @param author
     */
    public BookPredicate setAuthor(String author) {
        checkArgument(isValidAuthor(author), Author.MESSAGE_CONSTRAINTS);
        this.author = author;
        return this;
    }

    /**
     * add a serial number predicate to the book predicate
     *
     * @param serialNumber
     */
    public BookPredicate setSerialNumber(String serialNumber) {
        checkArgument(isValidSerialNumber(serialNumber), SerialNumber.MESSAGE_CONSTRAINTS);
        this.serialNumber = serialNumber;
        return this;
    }

    /**
     * add genre predicates to the book predicate
     *
     * @param genres in the form of varargs
     */
    public BookPredicate setGenres(String... genres) {

        for (String genre : genres) {
            String formattedGenre = genre.trim().toUpperCase();
            checkArgument(isValidGenreName(formattedGenre), Genre.MESSAGE_CONSTRAINTS);
        }
        this.genres = SampleDataUtil.getGenreSet(genres);
        return this;
    }

    /**
     * add genre predicate to the book predicate
     *
     * @param genres in the form of Collections.Set
     */
    public BookPredicate setGenres(Set<Genre> genres) {
        if (genres.isEmpty()) {
            return this;
        }
        this.genres = genres;
        return this;
    }

    /**
     * add loan state predicate to the book predicate
     *
     * @param loanState
     */
    public BookPredicate setLoanState(Flag loanState) {
        this.loanState = loanState;
        return this;
    }

    public BookPredicate setDisplayLimit(int displayLimit) {
        assert(displayLimit > 0);
        this.displayLimit = displayLimit;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookPredicate // instanceof handles nulls
                && (title == null || title.equals(((BookPredicate) other).title)) // state check
                && (author == null || author.equals(((BookPredicate) other).author)) // state check
                && (serialNumber == null || serialNumber.equals(((BookPredicate) other).serialNumber)) // state check
                && (genres == null || genres.equals(((BookPredicate) other).genres)) // state check
                && (loanState == null || loanState.equals(((BookPredicate) other).loanState))
                && displayLimit == ((BookPredicate) other).displayLimit);
    }
}
