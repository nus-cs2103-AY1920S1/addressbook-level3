package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
            return new CommandResult(MESSAGE_SUCCESS + group.details() + s);
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
