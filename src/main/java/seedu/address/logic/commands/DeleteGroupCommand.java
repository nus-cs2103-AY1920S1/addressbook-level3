package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Deletes a group.
 */
public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "deletegroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_GROUPNAME + "GROUP_NAME";

    public static final String MESSAGE_SUCCESS = "Delete group success: %s deleted";
    public static final String MESSAGE_FAILURE = "Unable to delete group: %s";

    public static final String MESSAGE_GROUP_NOT_FOUND = "Group does not exist";

    public final GroupName groupName;

    public DeleteGroupCommand(GroupName groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {

            model.deleteGroup(groupName);

            // update main window display
            model.updateScheduleWithUser(LocalDateTime.now(), ScheduleState.HOME);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUP);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, groupName.toString())).build();

        } catch (GroupNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_GROUP_NOT_FOUND)).build();
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof DeleteGroupCommand)) {
            return false;
        } else if (((DeleteGroupCommand) command).groupName.equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
