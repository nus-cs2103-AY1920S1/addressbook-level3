package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.Role;
import seedu.address.model.mapping.exceptions.AlreadyInGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a person into a group.
 */
public class AddToGroupCommand extends Command {
    public static final String COMMAND_WORD = "addtogroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME" + " "
            + PREFIX_GROUPNAME + "GROUP_NAME" + " "
            + "[" + PREFIX_ROLE + "ROLE]";

    public static final String MESSAGE_SUCCESS = "Add to group success: %s added to %s";
    public static final String MESSAGE_FAILURE = "Unable to add person to group: %s";

    public static final String MESSAGE_UPDATED_ROLE = "Role updated to %s";
    public static final String MESSAGE_DUPLICATE = "Duplicate Mapping";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Unable to find group";

    private final Name name;
    private final GroupName groupName;
    private final Role role;

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
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
        }

        Group group;
        try {
            group = model.findGroup(groupName);
        } catch (GroupNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND)).build();
        }

        PersonToGroupMapping mapping = new PersonToGroupMapping(person.getPersonId(), group.getGroupId(), role);

        try {
            model.addPersonToGroupMapping(mapping);

            // updates main window
            model.updateDisplayWithGroup(group.getGroupName(),
                    LocalDateTime.now(), ScheduleState.GROUP);

            // updates side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUP);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS,
                    person.getName().toString(), group.getGroupName().toString())).build();

        } catch (DuplicateMappingException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE)).build();
        } catch (AlreadyInGroupException e) {

            try {
                model.updateDisplayWithGroup(group.getGroupName(),
                        LocalDateTime.now(), ScheduleState.GROUP);
            } catch (GroupNotFoundException ex) {
                return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND)).build();
            }

            return new CommandResultBuilder(String.format(MESSAGE_UPDATED_ROLE, mapping.getRole().toString())).build();
        } catch (GroupNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND)).build();
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof AddToGroupCommand)) {
            return false;
        } else if (((AddToGroupCommand) command).name.equals(this.name)
                && ((AddToGroupCommand) command).groupName.equals(this.groupName)
                && ((AddToGroupCommand) command).role.equals(this.role)) {
            return true;
        } else {
            return false;
        }
    }


}
