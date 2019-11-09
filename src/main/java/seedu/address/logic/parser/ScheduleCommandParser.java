package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses inputs and creates a new ScheduleCommand Object.
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    @Override
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        ArrayList<String> personNamesString = new ArrayList<>(argMultimap.getAllValues(PREFIX_NAME));

        ArrayList<Name> personNames = new ArrayList<>();
        for (String s: personNamesString) {
            personNames.add(ParserUtil.parseName(s));
        }

        return new ScheduleCommand(personNames);
    }
}
