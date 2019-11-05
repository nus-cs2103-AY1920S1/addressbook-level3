package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteEventCommand object.
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {
    @Override
    public DeleteEventCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EVENTNAME);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_EVENTNAME)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EVENTNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
        }

        Name name = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = new Name(argMultimap.getValue(PREFIX_NAME).get());
        }

        String eventName = ParserUtil.parserEventName(argMultimap.getValue(PREFIX_EVENTNAME).get());

        return new DeleteEventCommand(name, eventName);
    }
}
