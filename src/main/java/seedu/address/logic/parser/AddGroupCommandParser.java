package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescriptor;

/**
 * Parses input arguments and creates a new AddGroupCommand object.
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {
    @Override
    public AddGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPNAME, PREFIX_DESCRIPTION, PREFIX_ROLE);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_GROUPNAME)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_GROUPNAME,
                PREFIX_DESCRIPTION, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
        }


        GroupDescriptor groupDescriptor = new GroupDescriptor();
        groupDescriptor.setGroupName(ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUPNAME).get()));

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            groupDescriptor.setGroupDescription(
                    ParserUtil.parseGroupDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            groupDescriptor.setUserRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }

        return new AddGroupCommand(groupDescriptor);
    }
}
