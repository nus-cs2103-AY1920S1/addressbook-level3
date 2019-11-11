package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.exceptions.MappingNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person from a group.
 */
public class DeleteFromGroupCommand extends Command {

    public static final String COMMAND_WORD = "deletefromgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME" + " "
            + PREFIX_GROUPNAME + "GROUP_NAME";

    public static final String MESSAGE_SUCCESS = "%s is removed from %s";
    public static final String MESSAGE_FAILURE = "Unable to delete mapping: %s";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Unable to find group";
    public static final String MESSAGE_MAPPING_NOT_FOUND = "Unable to find mapping";


    private final Name name;
    private final GroupName groupName;

    public DeleteFromGroupCommand(Name name, GroupName groupName) {
        requireNonNull(name);
        requireNonNull(groupName);

        this.name = name;
        this.groupName = groupName;
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

        PersonToGroupMapping mapping = new PersonToGroupMapping(person.getPersonId(), group.getGroupId());

        try {
            model.deletePersonToGroupMapping(mapping);

            model.updateScheduleWithGroup(group.getGroupName(),
                    LocalDateTime.now(), ScheduleState.GROUP);

        } catch (MappingNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_MAPPING_NOT_FOUND)).build();
        } catch (GroupNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND)).build();
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                person.getName().toString(), group.getGroupName().toString()));
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof DeleteFromGroupCommand)) {
            return false;
        } else if (((DeleteFromGroupCommand) command).name.equals(this.name)
                && ((DeleteFromGroupCommand) command).groupName.equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
