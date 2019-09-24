package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.PersonId;

/**
 * Find a group.
 */
public class FindGroupCommand extends Command {
    public static final String COMMAND_WORD = "findgroup";
    public static final String MESSAGE_SUCCESS = "Found group: ";
    public static final String MESSAGE_FAILURE = "Unable to find group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    public final GroupName groupName;

    public FindGroupCommand(GroupName groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Group group = model.findGroup(groupName);
        if (group != null) {
            ArrayList<PersonId> persons = model.findPersonsOfGroup(group.getGroupId());
            String s = "Persons: \n";
            int i;
            for (i = 0; i < persons.size(); i++) {
                s += model.findPerson(persons.get(i)).toString();
            }
            return new CommandResult(MESSAGE_SUCCESS + group.toString() + s);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
