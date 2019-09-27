package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import seedu.address.logic.commands.EditGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new EditGroupCommand object.
 */
public class EditGroupCommandParser implements Parser<EditGroupCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public EditGroupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EDIT, PREFIX_GROUPNAME, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_EDIT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGroupCommand.MESSAGE_USAGE));
        }

        GroupName groupName = new GroupName(argMultimap.getValue(PREFIX_EDIT).get());
        GroupDescriptor groupDescriptor = new GroupDescriptor();

        if (argMultimap.getValue(PREFIX_GROUPNAME).isPresent()) {
            groupDescriptor.setGroupName(ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUPNAME).get()));
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            groupDescriptor.setGroupRemark(ParserUtil.parseGroupRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        return new EditGroupCommand(groupName, groupDescriptor);
    }
}
