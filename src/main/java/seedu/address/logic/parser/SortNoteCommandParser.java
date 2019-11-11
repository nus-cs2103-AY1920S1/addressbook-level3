package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTBY;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortNoteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.note.MultipleSortByCond;


/**
 * Parses input arguments and creates a new EditCommand object
 */
public class SortNoteCommandParser implements Parser<SortNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditNoteCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortNoteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SORTBY);
        if (!arePrefixesPresent(argMultimap, PREFIX_SORTBY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortNoteCommand.MESSAGE_USAGE));
        }
        MultipleSortByCond sortByConds = ParserUtil.parseSortByCond(argMultimap.getValue(PREFIX_SORTBY).get());
        return new SortNoteCommand(sortByConds, args);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
