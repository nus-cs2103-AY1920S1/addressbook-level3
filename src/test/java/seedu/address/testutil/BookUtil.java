package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.book.Book;
import seedu.address.model.genre.Genre;

/**
 * A utility class for Person.
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
            s -> sb.append(PREFIX_GENRE + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditBookDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.value).append(" "));
        descriptor.getSerialNumber().ifPresent(serialNumber -> sb.append(PREFIX_SERIAL_NUMBER)
                .append(serialNumber.value).append(" "));
        descriptor.getAuthor().ifPresent(author -> sb.append(PREFIX_AUTHOR).append(author.value).append(" "));
        if (descriptor.getGenres().isPresent()) {
            Set<Genre> genres = descriptor.getGenres().get();
            if (genres.isEmpty()) {
                sb.append(PREFIX_GENRE);
            } else {
                genres.forEach(s -> sb.append(PREFIX_GENRE).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
