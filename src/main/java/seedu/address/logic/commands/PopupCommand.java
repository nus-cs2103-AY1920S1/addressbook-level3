package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

/**
 * Command to show popup of the locations suggested.
 */
public class PopupCommand extends Command {

    public static final String COMMAND_WORD = "popup";
    public static final String MESSAGE_SUCCESS = "Showing locations.";
    public static final String MESSAGE_FAILURE = "Internal error.";

    private GroupName groupName;
    private int id;

    public PopupCommand(GroupName groupName, int id) {
        this.groupName = groupName;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Group group = model.findGroup(groupName);
        if (group != null) {
            ArrayList<PersonId> persons = model.findPersonsOfGroup(group.getGroupId());
            String s = "========== PERSONS ========== \n\n";
            int i;
            if (persons.size() == 0) {
                s += "NO PERSONS AVAILABLE";
            }
            for (i = 0; i < persons.size(); i++) {
                Person currentPerson = model.findPerson(persons.get(i));
                s += currentPerson.toString() + "\n";
                s += currentPerson.getSchedule().toString() + "";
            }

            // update main window
            model.updateDetailWindowDisplay(group.getGroupName(), LocalDateTime.now(), ScheduleWindowDisplayType.GROUP);

            //update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUP);

            //FreeTimeslot freeTimeslot = model.getScheduleWindowDisplay().getFreeSchedules().get(id);

            return new CommandResult(MESSAGE_SUCCESS, false, false , false , false, true,
                    null);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Command command) {
        return (command instanceof PopupCommand);
    }
}
