package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.book.Book;

/**
 * A utility class for Book.
 */
public class BookUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Book book) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(book);
    }

    /**
     * Returns an find command string for finding the {@code person}.
     */
    public static String getFindCommand(Book book) {
        return FindCommand.COMMAND_WORD + " " + getPersonDetails(book);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + book.getTitle().value + " ");
        sb.append(PREFIX_SERIAL_NUMBER + book.getSerialNumber().value + " ");
        sb.append(PREFIX_AUTHOR + book.getAuthor().value + " ");
        book.getGenres().stream().forEach(
            s -> sb.append(PREFIX_GENRE + s.genreName + " ")
        );
        return sb.toString();
    }

}
