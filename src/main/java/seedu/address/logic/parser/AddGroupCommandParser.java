package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;

/**
 * Parses input arguments and creates a new AddGroupCommand object.
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areMultiplePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    @Override
    public AddGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPNAME, PREFIX_DESCRIPTION, PREFIX_ROLE);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUPNAME)
                || areMultiplePrefixesPresent(argMultimap, PREFIX_GROUPNAME,
                PREFIX_DESCRIPTION, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
        }


        GroupDescriptor groupDescriptor = new GroupDescriptor();
        groupDescriptor.setGroupName(new GroupName(argMultimap.getValue(PREFIX_GROUPNAME).get()));

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            groupDescriptor.setGroupDescription(new GroupDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get().trim()));
        }

        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            groupDescriptor.setUserRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }

        return new AddGroupCommand(groupDescriptor);
    }
}
