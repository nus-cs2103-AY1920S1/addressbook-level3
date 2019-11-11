package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.group.exceptions.NoGroupFieldsEditedException;

/**
 * Edits a group details.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "editgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_EDIT + "GROUP_NAME" + " "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]" + " "
            + "[" + PREFIX_ROLE + "USER_ROLE]"
            + "\n" + "Note: At least on field must be edited";

    public static final String MESSAGE_SUCCESS = "Edit Group success: %s edited";
    public static final String MESSAGE_FAILURE = "Unable to edit Group: %s";

    public static final String MESSAGE_DUPLICATE_GROUP = "Group already exists";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Group does not exist";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final GroupName groupName;
    private final GroupDescriptor groupDescriptor;

    public EditGroupCommand(GroupName groupName, GroupDescriptor groupDescriptor) {
        requireNonNull(groupName);
        requireNonNull(groupDescriptor);

        this.groupName = groupName;
        this.groupDescriptor = groupDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Group group = model.editGroup(groupName, groupDescriptor);

            // update main window display
            model.updateScheduleWithGroup(group.getGroupName(), LocalDateTime.now(),
                    ScheduleState.GROUP);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUP);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, groupName.toString().trim())).build();

        } catch (DuplicateGroupException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_GROUP)).build();
        } catch (GroupNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND)).build();
        } catch (NoGroupFieldsEditedException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_NOT_EDITED)).build();
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof EditGroupCommand)) {
            return false;
        } else if (((EditGroupCommand) command).groupName.equals(this.groupName)
                && ((EditGroupCommand) command).groupDescriptor.equals(this.groupDescriptor)) {
            return true;
        } else {
            return false;
        }
    }
}
