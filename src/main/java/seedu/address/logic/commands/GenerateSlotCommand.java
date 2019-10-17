package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.timetable.GenerateSlot;
import seedu.address.model.timetable.TimeRange;
import seedu.address.model.timetable.TimeTable;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class GenerateSlotCommand extends Command {

    public static final Prefix PREFIX_TIMETABLE = new Prefix("t/");
    public static final Prefix PREFIX_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_TIMERANGE = new Prefix("r/");
    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generate timeslot from given timetable string, requested meeting duration and time range\n"
            + "Parameters: TimeTableString (STARTDAY ENDDAY STARTTIME ENDTIME)+ (Once or more)\n"
            + "Parameters: DURATION (Must be integer)\n"
            + "Parameters: TIMERANGE (DAY DAY TIME TIME)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TIMETABLE + "MONDAY MONDAY 1000 1300 TUESDAY TUESDAY 1100 1400 "
            + PREFIX_DURATION + "2 "
            + PREFIX_TIMERANGE + "MONDAY FRIDAY 0800 1700";
    public static final String MESSAGE_INVALID_TIMETABLE_FORMAT = PREFIX_TIMETABLE
            + " should be of the format STARTDAY ENDDAY STARTTIME ENDTTIME";
    private List<TimeTable> timeTables = new ArrayList<>();
    private int durationInHours;
    private TimeRange timeRange;


    public GenerateSlotCommand(List<TimeTable> timeTables, int durationInHours, TimeRange timeRange) {
        requireAllNonNull(timeTables, durationInHours, timeRange);

        this.timeTables = timeTables;
        this.durationInHours = durationInHours;
        this.timeRange = timeRange;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            return new CommandResult(GenerateSlot.generate(this.timeTables, durationInHours, timeRange).toString());
        } catch (IllegalValueException e) {
            throw new CommandException("Generation error. Unable to generate timeslot");
        }
    }
}
