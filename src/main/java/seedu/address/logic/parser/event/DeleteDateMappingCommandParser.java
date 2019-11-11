package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DATE_START_AFTER_END;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.Prefix.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.DeleteDateMappingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
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
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE);

        boolean endDateStated = argMultimap.getValue(PREFIX_EVENT_END_DATE).isPresent();

        //Ensure fields are compulsory
        if (!arePrefixesPresent(argMultimap, PREFIX_EVENT_START_DATE) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteDateMappingCommand.MESSAGE_USAGE));
        }

        Index eventIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        EventDate targetDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_START_DATE).get());

        if (endDateStated) {
            EventDate endDateRange = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_EVENT_END_DATE).get());
            if (targetDate.isAfter(endDateRange)) {
                throw new ParseException(String.format(MESSAGE_DATE_START_AFTER_END, targetDate, endDateRange));
            }
            return new DeleteDateMappingCommand(eventIndex, targetDate, endDateRange);
        } else {
            return new DeleteDateMappingCommand(eventIndex, targetDate);
        }

    }

}
