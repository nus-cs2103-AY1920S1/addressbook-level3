package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;

/**
 * Find a group.
 */
public class FindGroupCommand extends Command {
    public static final String COMMAND_WORD = "findgroup";
    public static final String MESSAGE_SUCCESS = "Found group: \n\n";
    public static final String MESSAGE_FAILURE = "Unable to find group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    public final GroupName groupName;

    public FindGroupCommand(GroupName groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
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
            model.updateDetailWindowDisplay(group.getGroupName(), LocalDateTime.now(), DetailWindowDisplayType.GROUP);

            //update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUPS);

            return new CommandResult(MESSAGE_SUCCESS + group.details() + s);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof FindGroupCommand)) {
            return false;
        } else if (((FindGroupCommand) command).groupName.equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
