package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Author;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.Title;
import seedu.address.model.genre.Genre;

public class ParserUtilTest {
    private static final String INVALID_TITLE = "R@chel";
    private static final String INVALID_SERIAL_NUMBER = "+651234";
    private static final String INVALID_GENRE = "#friend";

    private static final String VALID_TITLE = "Rachel Walker";
    private static final String VALID_SERIAL_NUMBER = "B03456";
    private static final String VALID_AUTHOR = "rachel@example.com";
    private static final String VALID_GENRE_1 = "friend";
    private static final String VALID_GENRE_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_BOOK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_BOOK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTitle(INVALID_TITLE));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_TITLE + WHITESPACE;
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(nameWithWhitespace));
    }

    @Test
    public void parseSerialNumber_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSerialNumber((String) null));
    }

    @Test
    public void parseSerialNumber_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSerialNumber(INVALID_SERIAL_NUMBER));
    }

    @Test
    public void parseSerialNumber_validValueWithoutWhitespace_returnsSerialNumber() throws Exception {
        SerialNumber expectedSerialNumber = new SerialNumber(VALID_SERIAL_NUMBER);
        assertEquals(expectedSerialNumber, ParserUtil.parseSerialNumber(VALID_SERIAL_NUMBER));
    }

    @Test
    public void parseSerialNumber_validValueWithWhitespace_returnsTrimmedSerialNumber() throws Exception {
        String serialNumberWithWhitespace = WHITESPACE + VALID_SERIAL_NUMBER + WHITESPACE;
        SerialNumber expectedSerialNumber = new SerialNumber(VALID_SERIAL_NUMBER);
        assertEquals(expectedSerialNumber, ParserUtil.parseSerialNumber(serialNumberWithWhitespace));
    }

    @Test
    public void parseAuthor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAuthor((String) null));
    }

    @Test
    public void parseAuthor_validValueWithoutWhitespace_returnsAuthor() throws Exception {
        Author expectedAuthor = new Author(VALID_AUTHOR);
        assertEquals(expectedAuthor, ParserUtil.parseAuthor(VALID_AUTHOR));
    }

    @Test
    public void parseAuthor_validValueWithWhitespace_returnsTrimmedAuthor() throws Exception {
        String authorWithWhitespace = WHITESPACE + VALID_AUTHOR + WHITESPACE;
        Author expectedAuthor = new Author(VALID_AUTHOR);
        assertEquals(expectedAuthor, ParserUtil.parseAuthor(authorWithWhitespace));
    }

    @Test
    public void parseGenre_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGenre(null));
    }

    @Test
    public void parseGenre_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGenre(INVALID_GENRE));
    }

    @Test
    public void parseGenre_validValueWithoutWhitespace_returnsGenre() throws Exception {
        Genre expectedGenre = new Genre(VALID_GENRE_1);
        assertEquals(expectedGenre, ParserUtil.parseGenre(VALID_GENRE_1));
    }

    @Test
    public void parseGenre_validValueWithWhitespace_returnsTrimmedGenre() throws Exception {
        String genreWithWhitespace = WHITESPACE + VALID_GENRE_1 + WHITESPACE;
        Genre expectedGenre = new Genre(VALID_GENRE_1);
        assertEquals(expectedGenre, ParserUtil.parseGenre(genreWithWhitespace));
    }

    @Test
    public void parseGenres_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseGenres(null));
    }

    @Test
    public void parseGenres_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseGenres(Arrays.asList(VALID_GENRE_1, INVALID_GENRE)));
    }

    @Test
    public void parseGenres_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseGenres(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseGenres_collectionWithValidTags_returnsGenreSet() throws Exception {
        Set<Genre> actualGenreSet = ParserUtil.parseGenres(Arrays.asList(VALID_GENRE_1, VALID_GENRE_2));
        Set<Genre> expectedGenreSet = new HashSet<Genre>(Arrays.asList(new Genre(VALID_GENRE_1),
                new Genre(VALID_GENRE_2)));

        assertEquals(expectedGenreSet, actualGenreSet);
    }
}
