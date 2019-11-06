package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.ClosestCommonLocationData;
import seedu.address.model.display.schedulewindow.FreeTimeslot;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.exceptions.InvalidTimeslotException;

/**
 * Command to show popup of the locations suggested.
 */
public class PopupCommand extends Command {

    public static final String COMMAND_WORD = "popup";
    public static final String MESSAGE_SUCCESS = "Closest Location found! See the popup for information.";
    public static final String ERROR_CANNOT_FIND_GROUP = "Cannot recognise GROUP_NAME. Make sure you entered"
            + " the correct value.";
    public static final String INTERNAL_ERROR = "Internal Error. Please try again later.";
    public static final String MESSAGE_USER_ERROR = "We could not find a common "
            + "location because:\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME"
            + PREFIX_ID + " FREETIMESLOTID";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private GroupName groupName;
    private int week;
    private int id;

    public PopupCommand(GroupName groupName, int week, int id) {
        this.groupName = groupName;
        this.week = week;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            Group group = model.findGroup(groupName);

            // update main window
            model.updateScheduleWindowDisplay(group.getGroupName(), LocalDateTime.now(),
                    ScheduleWindowDisplayType.GROUP);

            try {
                FreeTimeslot freeTimeslot = model.getScheduleWindowDisplay().getFreeTimeslot(week, id);
                ClosestCommonLocationData commonLocationData = freeTimeslot.getClosestCommonLocationData();

                if (!commonLocationData.isOk()) {
                    String errorResponse = "";

                    if (freeTimeslot.getVenues().size() == 0) {
                        errorResponse = "Everyone has not started their schedule yet. Feel free to meet up any time.";
                    } else if (commonLocationData.getErrorResponse().length() != 0) {
                        errorResponse = commonLocationData.getErrorResponse();
                    } else {
                        logger.warning("Unknown error for time slot: " + freeTimeslot.toString());
                        return new CommandResult(INTERNAL_ERROR);
                    }
                    return new CommandResult(MESSAGE_USER_ERROR + errorResponse);
                }

                return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true,
                        freeTimeslot.getClosestCommonLocationData());

            } catch (InvalidTimeslotException e) {
                return new CommandResult("Invalid time slot ID: " + id + ". Please enter a valid id as "
                        + "shown in the GUI.");
            }

        } catch (GroupNotFoundException e) {
            return new CommandResult(ERROR_CANNOT_FIND_GROUP);
        }
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof PopupCommand);
    }
}
