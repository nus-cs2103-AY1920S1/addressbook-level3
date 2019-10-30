package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FREETIMESLOT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.ClosestCommonLocationData;
import seedu.address.model.display.schedulewindow.FreeTimeslot;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Command to show popup of the locations suggested.
 */
public class PopupCommand extends Command {

    public static final String COMMAND_WORD = "popup";
    public static final String MESSAGE_SUCCESS = "Showing locations.";
    public static final String MESSAGE_FAILURE = "Internal error";
    public static final String MESSAGE_FAILURE_NO_LOCATION = "We could not find a common "
            + "location because all places cannot be found in NUS. The locations are:\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME"
            + PREFIX_FREETIMESLOT_ID + " FREETIMESLOTID";

    private GroupName groupName;
    private int id;

    public PopupCommand(GroupName groupName, int id) {
        this.groupName = groupName;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            Group group = model.findGroup(groupName);

            // update main window
            model.updateScheduleWindowDisplay(group.getGroupName(), LocalDateTime.now(),
                    ScheduleWindowDisplayType.GROUP);

            //update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUP);

            //Get Week 0 FreeSchedule and find the correct FreeTimeslot.
            Optional<FreeTimeslot> freeTimeslot =
                    model.getScheduleWindowDisplay().getFreeSchedule().get(0).getFreeTimeslot(id);

            System.out.println("TEST: " + freeTimeslot.get().toString());

            ClosestCommonLocationData commonLocationData = freeTimeslot.get().getClosestCommonLocationData();

            if (!commonLocationData.isOk()) {
                String locations = "";

                if (freeTimeslot.get().getVenues().size() == 0) {
                    locations = "Everyone has not started their schedule yet. Feel free to meet up any time.";
                }

                for (String s : freeTimeslot.get().getVenues()) {
                    locations += s + "\n";
                }
                return new CommandResult(MESSAGE_FAILURE_NO_LOCATION + locations);
            }

            if (freeTimeslot.isEmpty()) {
                return new CommandResult(MESSAGE_FAILURE);
            }

            return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true,
                    freeTimeslot.get().getClosestCommonLocationData());
        } catch (GroupNotFoundException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof PopupCommand);
    }
}
