package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeletePersonCommand object.
 */
public class DeletePersonCommandParser implements Parser<DeletePersonCommand> {
    @Override
    public DeletePersonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_NAME)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE));
        }

        Name name = new Name(argMultimap.getValue(PREFIX_NAME).get());
        return new DeletePersonCommand(name);
    }
}
