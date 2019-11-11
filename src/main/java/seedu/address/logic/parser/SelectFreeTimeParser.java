package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.SelectFreeTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser class to show closest location inforamtion.
 */
public class SelectFreeTimeParser implements Parser<SelectFreeTimeCommand> {
    @Override
    public SelectFreeTimeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEEK, PREFIX_ID);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_ID)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_GROUPNAME, PREFIX_WEEK, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SelectFreeTimeCommand.MESSAGE_USAGE));
        }

        int id;
        if (argMultimap.getValue(PREFIX_ID).isPresent()) {
            id = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SelectFreeTimeCommand.MESSAGE_USAGE));
        }

        int week;
        if (argMultimap.getValue(PREFIX_WEEK).isPresent()) {
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        } else {
            week = 0;
        }

        return new SelectFreeTimeCommand(week, id);
    }


}
