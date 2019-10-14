package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddToGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddToGroupCommand object.
 */
public class AddToGroupCommandParser implements Parser<AddToGroupCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddToGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUPNAME, PREFIX_REMARK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUPNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToGroupCommand.MESSAGE_USAGE));
        }

        Name name = new Name(argMultimap.getValue(PREFIX_NAME).get());
        GroupName groupName = new GroupName(argMultimap.getValue(PREFIX_GROUPNAME).get());
        Role role = Role.emptyRole();

        if(argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_REMARK).get());
        }

        return new AddToGroupCommand(name, groupName, role);
    }
}
