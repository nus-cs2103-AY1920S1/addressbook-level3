package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Author;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.genre.Genre;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String title} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Title parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedName = title.trim();
        if (!Title.isValidTitle(trimmedName)) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(trimmedName);
    }

    /**
     * Parses a {@code String serialNumber} into a {@code SerialNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code serialNumber} is invalid.
     */
    public static SerialNumber parseSerialNumber(String serialNumber) throws ParseException {
        requireNonNull(serialNumber);
        String trimmedSerialNumber = serialNumber.trim();
        if (!SerialNumber.isValidSerialNumber(trimmedSerialNumber)) {
            throw new ParseException(SerialNumber.MESSAGE_CONSTRAINTS);
        }
        return new SerialNumber(trimmedSerialNumber);
    }

    /**
     * Parses a {@code String author} into an {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return Author object from remark string
     */
    public static Author parseAuthor(String author) {
        requireNonNull(author);
        String trimmedAuthor = author.trim();
        return new Author(trimmedAuthor);
    }

    /**
     * Parses a {@code String genre} into a {@code Genre}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code genre} is invalid.
     */
    public static Genre parseGenre(String genre) throws ParseException {
        requireNonNull(genre);
        String trimmedTag = genre.trim();
        if (!Genre.isValidTagName(trimmedTag)) {
            throw new ParseException(Genre.MESSAGE_CONSTRAINTS);
        }
        return new Genre(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> genres} into a {@code Set<Genre>}.
     */
    public static Set<Genre> parseGenres(Collection<String> genres) throws ParseException {
        requireNonNull(genres);
        final Set<Genre> genreSet = new HashSet<>();
        for (String tagName : genres) {
            genreSet.add(parseGenre(tagName));
        }
        return genreSet;
    }
}
