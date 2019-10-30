package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FREETIMESLOT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.PopupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/**
 * Parser class to show closest location inforamtion.
 */
public class PopupCommandParser implements Parser<PopupCommand> {
    @Override
    public PopupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPNAME, PREFIX_FREETIMESLOT_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUPNAME, PREFIX_FREETIMESLOT_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
        }
        System.out.println(argMultimap.getValue(PREFIX_FREETIMESLOT_ID).get());
        return new PopupCommand(new GroupName(argMultimap.getValue(PREFIX_GROUPNAME).get()),
                Integer.parseInt(argMultimap.getValue(PREFIX_FREETIMESLOT_ID).get()));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
