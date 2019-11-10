package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.LookAtGroupMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parser to parse filtering of group members in a group.
 */
public class LookAtGroupMemberCommandParser implements Parser<LookAtGroupMemberCommand> {
    @Override
    public LookAtGroupMemberCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUPNAME, PREFIX_EVENTNAME);

        if (argMultimap.getAllValues(PREFIX_GROUPNAME).size() != 0
                || argMultimap.getAllValues(PREFIX_EVENTNAME).size() != 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    LookAtGroupMemberCommand.MESSAGE_USAGE));
        }

        List<String> namesInString = argMultimap.getAllValues(PREFIX_NAME);
        List<Name> names = namesInString.stream().filter(string -> !string.trim().equals(""))
                .map(name -> new Name(name)).collect(Collectors.toCollection(ArrayList::new));

        return new LookAtGroupMemberCommand(names);
    }
}
