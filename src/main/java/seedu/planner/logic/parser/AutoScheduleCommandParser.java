package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.schedulecommand.AutoScheduleCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.NameOrTagWithTime;

//@@author oscarsu97

/**
 * Parses input arguments and creates a new AutoScheduleCommand object
 */
public class AutoScheduleCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AutoScheduleCommand
     * and returns an AutoScheduleScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutoScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NAME,
                PREFIX_ADDRESS, PREFIX_DAY);
        if ((!isPrefixPresent(argMultimap, PREFIX_TAG) && !isPrefixPresent(argMultimap, PREFIX_NAME))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutoScheduleCommand.MESSAGE_USAGE));
        }

        Optional<Address> address = Optional.empty();
        List<Index> days = new ArrayList<>();
        //Contains either a Tag class or a Name class
        List<NameOrTagWithTime> schedulePlan;

        schedulePlan = getSchedulePlan(argMultimap, PREFIX_TAG, PREFIX_NAME);
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            days = ParserUtil.parseDaysToSchedule(argMultimap.getValue(PREFIX_DAY).get());
        }

        return new AutoScheduleCommand(schedulePlan, address, days, false);
    }

    private List<NameOrTagWithTime> getSchedulePlan(ArgumentMultimap argumentMultimap,
                                                     Prefix... prefixes) throws ParseException {
        List<PrefixArgument> filteredMultiMap = argumentMultimap.getFilteredArgMultiMap(prefixes);
        List<NameOrTagWithTime> schedulePlan = new ArrayList<>();

        for (PrefixArgument prefixArgument : filteredMultiMap) {
            Prefix prefix = prefixArgument.getPrefix();

            if (prefix.equals(PREFIX_TAG) || prefix.equals(PREFIX_NAME)) {
                schedulePlan.add(ParserUtil.parseNameOrTagWithTime(prefixArgument.getArgValue(), prefix));
            }
        }
        return schedulePlan;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
