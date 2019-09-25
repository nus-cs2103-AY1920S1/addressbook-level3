package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.GroupRemark;

/**
 * Parses input arguments and creates a new AddGroupCommand object.
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPNAME, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUPNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
        }

        GroupDescriptor groupDescriptor = new GroupDescriptor();
        groupDescriptor.setGroupName(new GroupName(argMultimap.getValue(PREFIX_GROUPNAME).get()));

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            groupDescriptor.setGroupRemark(new GroupRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }

        return new AddGroupCommand(groupDescriptor);
    }
}
