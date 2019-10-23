package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeAppCommand;
import seedu.address.logic.commands.common.ReversibleActionPairCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.events.Appointment;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;


/**
 * Parses input arguments and creates a new ChangeAppCommand object
 */
public class ChangeAppCommandTimingParser implements Parser<ReversibleActionPairCommand> {
    private Model model;
    private List<Event> lastShownList;

    public ChangeAppCommandTimingParser(Model model) {
        this.lastShownList = model.getFilteredEventList();
        this.model = model;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ReversibleActionPairCommand
     * and returns a ReversibleActionPairCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReversibleActionPairCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START, PREFIX_END);

        if (!model.isPatientList()) {
            throw new ParseException(Messages.MESSAGE_NOT_PATIENTLIST);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_START, PREFIX_END) || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAppCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
            int idx = index.getZeroBased();
            if (idx >= lastShownList.size()) {
                throw new ParseException(Messages.MESSAGE_INVALID_INDEX);
            }
            String startString = argMultimap.getValue(PREFIX_START).get();
            String endString = argMultimap.getValue(PREFIX_END).get();
            Timing timing = ParserUtil.parseTiming(startString, endString);

            Event source = lastShownList.get(idx);
            Event dest = new Appointment(source.getPersonId(), timing, new Status());

            return new ReversibleActionPairCommand(new ChangeAppCommand(source, dest),
                    new ChangeAppCommand(dest, source));

        } catch (ParseException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeAppCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
