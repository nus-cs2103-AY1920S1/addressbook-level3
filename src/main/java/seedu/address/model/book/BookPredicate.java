package seedu.address.model.book;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.book.SerialNumber.isValidSerialNumber;
import static seedu.address.model.book.Title.isValidTitle;
import static seedu.address.model.genre.Genre.isValidGenreName;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
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

    public BookPredicate() {
        this.title = null;
        this.author = null;
        this.serialNumber = null;
        this.genres = null;
    }

    public boolean isValid() {
        return title != null || author != null || serialNumber != null || genres != null;
    }

    @Override
    public boolean test(Book book) {

        if (title != null
                && !Arrays.stream(title.split(" "))
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getTitle().value, keyword))) {
            return false;
        }
        if (author != null
                && !Arrays.stream(author.split(" "))
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getAuthor().value, keyword))) {
            return false;
        }
        if (serialNumber != null
                && !Stream.of(serialNumber)
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(book.getSerialNumber().value, keyword))) {
            return false;
        }
        if (genres != null
                && !genres.stream() // TODO make this caps-insensitive
                .allMatch(keyword -> book.getGenres().contains(keyword))) {
            return false;
        }
        return true;
    }

    /**
     * add a title predicate to the book predicate
     *
     * @param title
     */
    public BookPredicate addTitle(String title) {
        checkArgument(isValidTitle(title), Title.MESSAGE_CONSTRAINTS);
        this.title = title;
        return this;
    }

    /**
     * add an author predicate to the book predicate
     *
     * @param author
     */
    public BookPredicate addAuthor(String author) {
        this.author = author;
        return this;
    }

    /**
     * add a serial number predicate to the book predicate
     *
     * @param serialNumber
     */
    public BookPredicate addSerialNumber(String serialNumber) {
        checkArgument(isValidSerialNumber(serialNumber), SerialNumber.MESSAGE_CONSTRAINTS);
        this.serialNumber = serialNumber;
        return this;
    }

    /**
     * add genre predicates to the book predicate
     *
     * @param genres in the form of varargs
     */
    public BookPredicate addGenres(String... genres) {
        for (String genre : genres) {
            checkArgument(isValidGenreName(genre), Genre.MESSAGE_CONSTRAINTS);
        }
        this.genres = SampleDataUtil.getGenreSet(genres);
        return this;
    }

    /**
     * add genre predicate to the book predicate
     *
     * @param genres in the form of Collections.Set
     */
    public BookPredicate addGenres(Set<Genre> genres) {
        this.genres = genres;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookPredicate // instanceof handles nulls
                && title.equals(((BookPredicate) other).title) // state check
                && author.equals(((BookPredicate) other).author) // state check
                && serialNumber.equals(((BookPredicate) other).serialNumber) // state check
                && genres.equals(((BookPredicate) other).genres)); // state check
    }
}
