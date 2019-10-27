package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.DeleteDateMappingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDate;

/**
 * Parses input arguments and creates a new DeleteDateMappingCommand object.
 */
public class DeleteDateMappingCommandParser implements Parser<DeleteDateMappingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteDateMappingCommand
     * and returns an DeleteDateMappingCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDateMappingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_START_DATE);

        //Ensure fields are compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_START_DATE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteDateMappingCommand.MESSAGE_USAGE));
        }

        Index eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        EventDate targetDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());
        return new DeleteDateMappingCommand(eventIndex, targetDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given.
     * Note: Repeated across multiple classes, will refactor later
     * <p>
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
