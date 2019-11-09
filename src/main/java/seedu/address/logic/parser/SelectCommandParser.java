package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses arguments and creates a SelectCommand object.
 */
public class SelectCommandParser implements Parser<SelectCommand> {
    @Override
    public SelectCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_WEEK, PREFIX_ID);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_ID)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_WEEK, PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        Name name;
        int week;
        int id;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } else {
            name = Name.emptyName();
        }

        if (argMultimap.getValue(PREFIX_WEEK).isPresent()) {
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).get());
        } else {
            week = 0;
        }

        id = ParserUtil.parseId(argMultimap.getValue(PREFIX_ID).get());

        return new SelectCommand(week, name, id);
    }
}
