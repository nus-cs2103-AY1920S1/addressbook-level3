package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.timetable.GenerateSlot;
import seedu.address.model.timetable.TimeRange;
import seedu.address.model.timetable.TimeTable;

import java.util.List;
import java.util.stream.Collectors;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class GenerateSlotCommand extends Command {

    public static final Prefix PREFIX_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_TIMERANGE = new Prefix("r/");
    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generate timeslot from requested meeting duration and time range\n"
            + "Parameters: DURATION (must be positive integer) "
            + "[r/TIMERANGE (DAY TIME DAY TIME)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DURATION + "2 "
            + PREFIX_TIMERANGE + "MONDAY 0800 MONDAY 1700";
    public static final String MESSAGE_NOT_CHECKED_OUT = "A project must be checked out first before running this command";
    public static final String MESSAGE_UNKNOWN_ERROR = "Unknown error occured in generation algorithm. Please contact the dev team to report on this bug";
    private int durationInHours;
    private TimeRange timeRange;


    public GenerateSlotCommand(int durationInHours, TimeRange timeRange) {
        requireAllNonNull(durationInHours, timeRange);

        this.durationInHours = durationInHours;
        this.timeRange = timeRange;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }
        if (model.getWorkingProject().isEmpty()) {
            throw new CommandException(MESSAGE_UNKNOWN_ERROR);
        }
        List<TimeTable> timeTables = model.getMembers().stream().map(Person::getTimeTable).collect(Collectors.toList());
        try {
            return new CommandResult(GenerateSlot.generate(timeTables, durationInHours, timeRange).toString(), COMMAND_WORD);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_UNKNOWN_ERROR);
        }
    }
}
