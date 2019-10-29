package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.Role;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a person into a group.
 */
public class AddToGroupCommand extends Command {
    public static final String COMMAND_WORD = "addtogroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME "
            + PREFIX_GROUPNAME + " GROUPNAME";

    public static final String MESSAGE_SUCCESS = "Add to group success: %s";
    public static final String MESSAGE_FAILURE = "Unable to add person to group: %s";

    public static final String MESSAGE_DUPLICATE = "Duplicate Mapping";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Unable to find group";

    public final Name name;
    public final GroupName groupName;
    public final Role role;

    public AddToGroupCommand(Name name, GroupName groupName, Role role) {
        requireNonNull(name);
        requireNonNull(groupName);
        requireNonNull(role);

        this.name = name;
        this.groupName = groupName;
        this.role = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        Person person;
        try {
            person = model.findPerson(name);
        } catch (PersonNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND));
        }

        Group group;
        try {
            group = model.findGroup(groupName);
        } catch (GroupNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND));
        }

        PersonToGroupMapping mapping = new PersonToGroupMapping(person.getPersonId(), group.getGroupId(), role);

        try {

            model.addPersonToGroupMapping(mapping);

            // updates main window
            model.updateDetailWindowDisplay(group.getGroupName(),
                    LocalDateTime.now(), DetailWindowDisplayType.GROUP);

            // updates side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUPS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, mapping.toString()));

        } catch (DuplicateMappingException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE));
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof AddToGroupCommand)) {
            return false;
        } else if (((AddToGroupCommand) command).name.equals(this.name)
                && ((AddToGroupCommand) command).groupName.equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
