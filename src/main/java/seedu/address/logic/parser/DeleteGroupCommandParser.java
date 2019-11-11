package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new DeleteGroupCommand object.
 */
public class DeleteGroupCommandParser implements Parser<DeleteGroupCommand> {
    @Override
    public DeleteGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPNAME);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_GROUPNAME)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_GROUPNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
        }

        GroupName groupName = new GroupName(argMultimap.getValue(PREFIX_GROUPNAME).get());
        return new DeleteGroupCommand(groupName);
    }
}
