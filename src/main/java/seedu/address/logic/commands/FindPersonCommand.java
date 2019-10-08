package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.GroupId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Find a person.
 */
public class FindPersonCommand extends Command {
    public static final String COMMAND_WORD = "findperson";
    public static final String MESSAGE_SUCCESS = "Found person: ";
    public static final String MESSAGE_FAILURE = "Unable to find person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME";

    public final Name name;

    public FindPersonCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person person = model.findPerson(name);
        if (person != null) {
            ArrayList<GroupId> groups = model.findGroupsOfPerson(person.getPersonId());
            String s = "===== GROUPS ===== \n";
            int i;
            if (groups.size() == 0) {
                s += "NO GROUPS AVAILABLE";
            }
            for (i = 0; i < groups.size(); i++) {
                s += "    " + model.findGroup(groups.get(i)).toString() + "\n";
            }

            // update main window
            model.updateDetailWindowDisplay(person.getName(), LocalDateTime.now(), DetailWindowDisplayType.PERSON);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSONS);

            return new CommandResult(MESSAGE_SUCCESS + person.details() + s);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof FindPersonCommand)) {
            return false;
        } else if (((FindPersonCommand) command).name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }
}
