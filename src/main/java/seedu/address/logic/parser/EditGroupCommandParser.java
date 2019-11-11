package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new EditGroupCommand object.
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {
    @Override
    public EditGroupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EDIT, PREFIX_GROUPNAME,
                        PREFIX_DESCRIPTION, PREFIX_ROLE);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_EDIT)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_EDIT, PREFIX_GROUPNAME,
                PREFIX_DESCRIPTION, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE));
        }

        GroupName groupName = new GroupName(argMultimap.getValue(PREFIX_EDIT).get());
        GroupDescriptor groupDescriptor = new GroupDescriptor();

        if (argMultimap.getValue(PREFIX_GROUPNAME).isPresent()) {
            groupDescriptor.setGroupName(ParserUtil.parseGroupName(
                    argMultimap.getValue(PREFIX_GROUPNAME).get()));
        }

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            groupDescriptor.setGroupDescription(ParserUtil.parseGroupDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            groupDescriptor.setUserRole(ParserUtil.parseRole(
                    argMultimap.getValue(PREFIX_ROLE).get()));
        }

        return new EditGroupCommand(groupName, groupDescriptor);
    }
}
