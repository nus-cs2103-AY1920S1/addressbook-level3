package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.util.List;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    @Override
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EVENTNAME, PREFIX_TIMING);

        if (!Parser.arePrefixesPresent(argMultimap, PREFIX_EVENTNAME, PREFIX_TIMING)
                || Parser.areMultiplePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EVENTNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        Name name = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            name = ParserUtil.parseName((argMultimap.getValue(PREFIX_NAME).get()));
        }

        String eventName = ParserUtil.parserEventName(argMultimap.getValue(PREFIX_EVENTNAME).get());

        List<String> timings = argMultimap.getAllValues(PREFIX_TIMING);

        int i;
        Event event = new Event(eventName);
        try {
            for (i = 0; i < timings.size(); i++) {
                Timeslot timeslot = ParserUtil.parseTimeslot(timings.get(i));
                if (timeslot != null) {
                    event.addTimeslot(timeslot);
                } else {
                    return new AddEventCommand(name, null);
                }
            }
        } catch (Exception e) {
            return new AddEventCommand(name, null);
        }
        return new AddEventCommand(name, event);
    }
}
