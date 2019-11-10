package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.locationdata.ClosestCommonLocationData;
import seedu.address.model.display.scheduledisplay.GroupScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.timeslots.FreeTimeslot;
import seedu.address.model.person.exceptions.InvalidTimeslotException;

/**
 * Command to show popup of the locations suggested.
 */
public class SelectFreeTimeCommand extends Command {

    public static final String COMMAND_WORD = "selectfreetime";

    public static final String MESSAGE_SUCCESS = "Closest Location found! See the popup for information.";
    public static final String ERROR_NOTHING_TO_POPUP = "Nothing to show, please show a group first";
    public static final String INTERNAL_ERROR = "Internal Error. Please try again later.";
    public static final String MESSAGE_USER_ERROR = "We could not find a common "
            + "location because:\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_WEEK + "WEEK_NUMBER] "
            + PREFIX_ID + "FREETIMESLOTID" + "\n"
            + "WEEK_NUMBER: 1 - 4   (if not specified, current week will be selected)";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private int week;
    private int id;

    public SelectFreeTimeCommand(int week, int id) {
        this.week = week;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        ScheduleState status = model.getState();
        if (status == ScheduleState.GROUP) {
            try {
                GroupScheduleDisplay groupScheduleDisplay = (GroupScheduleDisplay) model.getScheduleDisplay();
                FreeTimeslot freeTimeslot = groupScheduleDisplay.getFreeTimeslot(week, id);
                ClosestCommonLocationData commonLocationData = freeTimeslot.getClosestCommonLocationData();

                if (!commonLocationData.isOk()) {
                    String errorResponse = "";

                    if (freeTimeslot.getVenues().size() == 0) {
                        errorResponse = "Everyone has not started their schedule yet. Feel free to meet up any time.";
                    } else if (commonLocationData.getErrorResponse().length() != 0) {
                        errorResponse = commonLocationData.getErrorResponse();
                    } else {
                        logger.warning("Unknown error for time slot: " + freeTimeslot.toString());
                        return new CommandResultBuilder(INTERNAL_ERROR).build();
                    }
                    return new CommandResultBuilder(MESSAGE_USER_ERROR + errorResponse).build();
                }

                return new CommandResultBuilder(MESSAGE_SUCCESS)
                        .setPopUp().setLocationData(freeTimeslot.getClosestCommonLocationData()).build();

            } catch (InvalidTimeslotException e) {
                return new CommandResultBuilder("Invalid time slot ID: " + id + ". Please enter a valid id as "
                        + "shown in the GUI.").build();
            }
        } else {
            return new CommandResultBuilder(ERROR_NOTHING_TO_POPUP).build();
        }


    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof SelectFreeTimeCommand);
    }
}
