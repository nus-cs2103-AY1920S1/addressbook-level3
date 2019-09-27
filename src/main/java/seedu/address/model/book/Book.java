package seedu.address.model.book;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.genre.Genre;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Book {

    // Identity fields
    private final Title title;
    private final SerialNumber serialNumber;
    private final Author author;
    private final Set<Genre> genres = new HashSet<>();


    /**
     * Constructor when loading the file from history or when loading sample data/tests.
     */
    public Book(Title title, SerialNumber serialNumber, Author author, Set<Genre> genres) {
        requireAllNonNull(title, serialNumber, author, genres);
        this.title = title;
        this.serialNumber = serialNumber;
        this.author = author;
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
    /**
     * Returns an immutable genre set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameBook(Book otherBook) {
        if (otherBook == this) {
            return true;
        }

        return otherBook != null
                && otherBook.getTitle().equals(getTitle())
                && (otherBook.getSerialNumber().equals(getSerialNumber()) || otherBook.getAuthor().equals(getAuthor()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
        return otherBook.getTitle().equals(getTitle())
                && otherBook.getSerialNumber().equals(getSerialNumber())
                && otherBook.getAuthor().equals(getAuthor())
                && otherBook.getGenres().equals(getGenres());
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
                .append(" SerialNumber: ")
                .append(getSerialNumber())
                .append(" Author: ")
                .append(getAuthor())
                .append(" Tags: ");
        getGenres().forEach(builder::append);
        return builder.toString();
    }

}
