package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteFromGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteFromGroupCommand object.
 */
public class DeleteFromGroupCommandParser implements Parser<DeleteFromGroupCommand> {

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public DeleteFromGroupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUPNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_GROUPNAME)
                || !argMultimap.getPreamble().isEmpty()) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteFromGroupCommand.MESSAGE_USAGE));
        }

        Name name = new Name(argMultimap.getValue(PREFIX_NAME).get().trim());
        GroupName groupName = new GroupName(argMultimap.getValue(PREFIX_GROUPNAME).get().trim());

        return new DeleteFromGroupCommand(name, groupName);
    }
}
