package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.stream.Stream;

import seedu.address.logic.commands.PopupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/**
 * Parser class to show closest location inforamtion.
 */
public class PopupCommandParser implements Parser<PopupCommand> {
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areMultiplePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    @Override
    public PopupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUPNAME, PREFIX_WEEK, PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_GROUPNAME, PREFIX_ID)
                || areMultiplePrefixesPresent(argMultimap, PREFIX_GROUPNAME, PREFIX_WEEK, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
        }

        GroupName groupName = null;
        if (argMultimap.getValue(PREFIX_GROUPNAME).isPresent()) {
            groupName = ParserUtil.parseGroupName(argMultimap.getValue(PREFIX_GROUPNAME).get());
        }

        int id;
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            id = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PopupCommand.MESSAGE_USAGE));
        }

        int week;
        if (argMultimap.getValue(PREFIX_WEEK).isPresent()) {
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        } else {
            week = 0;
        }

        return new PopupCommand(groupName, week, id);
    }


}
