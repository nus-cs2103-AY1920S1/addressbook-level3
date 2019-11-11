package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DISPLAY_LIMIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.Flag.FIND_FLAGS_MESSAGE_CONSTRAINTS;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.BookPredicate;
import seedu.address.model.genre.Genre;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final int MAX_LOAN_STATE_FLAGS = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        int displayLimit;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_SERIAL_NUMBER, PREFIX_AUTHOR,
                        PREFIX_GENRE, PREFIX_FLAG);

        BookPredicate predicate = new BookPredicate();

        if (!argMultimap.getPreamble().equals("")) {
            try {
                displayLimit = Integer.parseInt(argMultimap.getPreamble());
                if (displayLimit < 1) {
                    throw new NumberFormatException();
                }
                predicate.setDisplayLimit(displayLimit);
            } catch (NumberFormatException nfe) {
                throw new ParseException(MESSAGE_INVALID_DISPLAY_LIMIT, nfe);
            }
        }


        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            predicate.setTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get()).value);
        }

        if (argMultimap.getValue(PREFIX_SERIAL_NUMBER).isPresent()) {
            predicate.setSerialNumber(ParserUtil.parseSerialNumber(argMultimap.getValue(PREFIX_SERIAL_NUMBER)
                    .get()).value);
        }

        if (argMultimap.getValue(PREFIX_AUTHOR).isPresent()) {
            predicate.setAuthor(ParserUtil.parseAuthor(argMultimap.getValue(PREFIX_AUTHOR).get()).value);
        }

        parseGenresForFind(argMultimap.getAllValues(PREFIX_GENRE)).ifPresent(predicate::setGenres);

        parseLoanStateForFind(argMultimap.getAllValues(PREFIX_FLAG)).ifPresent(predicate::setLoanState);

        if (!predicate.isValid()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(predicate);
    }

    /**
     * Parses {@code Collection<String> genres} into a {@code Set<Genre>} if {@code genres} is non-empty.
     * Automatically converts lowercase {@code genreNames} to UPPERCASE
     * If {@code genres} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Genre>} containing zero genres.
     */
    private Optional<Set<Genre>> parseGenresForFind(Collection<String> genres) throws ParseException {
        requireNonNull(genres);

        if (genres.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> genreSet = genres.size() == 1 && genres.contains("") ? Collections.emptySet() : genres;
        return Optional.of(ParserUtil.parseGenres(genreSet));
    }

    /**
     * Parses {@code Collection<String> flags} into a {@code Set<Flag>} if {@code flags} is non-empty.
     * Automatically converts lowercase {@code flagNames} to UPPERCASE
     * If {@code flags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Flag>} containing zero flags.
     */
    private Optional<Flag> parseLoanStateForFind(Collection<String> flags) throws ParseException {
        requireNonNull(flags);

        if (flags.isEmpty()) {
            return Optional.empty();
        }

        Collection<Flag> flagSet;
        try {
            flagSet = flags.size() == 1 && flags.contains("") ? Collections.emptySet()
                    : ParserUtil.parseFlags(flags);
        } catch (ParseException pe) {
            throw new ParseException(FIND_FLAGS_MESSAGE_CONSTRAINTS);
        }

        if (flagSet.isEmpty() || flagSet.contains(Flag.ALL)) {
            throw new ParseException(FIND_FLAGS_MESSAGE_CONSTRAINTS);
        }

        List<Flag> loanStates = flagSet.stream()
                .filter(flag -> flag == Flag.AVAILABLE || flag == Flag.OVERDUE || flag == Flag.LOANED)
                .collect(Collectors.toList());
        if (loanStates.size() > MAX_LOAN_STATE_FLAGS) {
            throw new ParseException(Messages.MESSAGE_LOAN_STATE_CONSTRAINTS);
        } else {
            return Optional.of(loanStates.get(0));
        }
    }

}
