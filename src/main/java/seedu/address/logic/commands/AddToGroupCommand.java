package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Adds a person into a group.
 */
public class AddToGroupCommand extends Command {
    public static final String COMMAND_WORD = "addtogroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME "
            + PREFIX_GROUPNAME + " GROUPNAME";

    public static final String MESSAGE_SUCCESS = "Add to group success: ";
    public static final String MESSAGE_FAILURE = "Unable to find person or group";

    public final Name name;
    public final GroupName groupName;

    public AddToGroupCommand(Name name, GroupName groupName) {
        this.name = name;
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        Person person = model.findPerson(name);
        Group group = model.findGroup(groupName);

        if (person == null || group == null) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            PersonToGroupMapping mapping = new PersonToGroupMapping(person.getPersonId(), group.getGroupId());
            model.addPersonToGroupMapping(mapping);
            return new CommandResult(MESSAGE_SUCCESS + mapping.toString());
        }
    }
}
