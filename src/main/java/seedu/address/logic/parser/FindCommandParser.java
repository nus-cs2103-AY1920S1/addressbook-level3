package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookPredicate;
import seedu.address.model.genre.Genre;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = " 0 " + args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_TITLE, PREFIX_SERIAL_NUMBER, PREFIX_AUTHOR,
                        PREFIX_GENRE);

        BookPredicate predicate = new BookPredicate();

        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            predicate.addTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()).value);
        }

        if (argMultimap.getValue(PREFIX_SERIAL_NUMBER).isPresent()) {
            predicate.addSerialNumber(ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUMBER)
                    .get()).value);
        }

        if (argMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            predicate.addAuthor(ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get()).value);
        }

        parseGenresForFind(argMultimap.getAllValues(PREFIX_GENRE)).ifPresent(predicate::addGenres);

        if (!predicate.isValid()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

    /**
     * Parses {@code Collection<String> genres} into a {@code Set<Genre>} if {@code genres} is non-empty.
     * If {@code genres} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Genre>} containing zero genres.
     */
    private Optional<Set<Genre>> parseGenresForFind(Collection<String> genres) throws ParseException {
        assert genres != null;

        if (genres.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> genreSet = genres.size() == 1 && genres.contains("") ? Collections.emptySet() : genres;
        return Optional.of(ParserUtil.parseGenres(genreSet));
    }

}
