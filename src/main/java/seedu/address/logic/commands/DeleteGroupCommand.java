package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;

/**
 * Deletes a group.
 */
public class DeleteGroupCommand extends Command {
    public static final String COMMAND_WORD = "deletegroup";
    public static final String MESSAGE_SUCCESS = "Delete group success";
    public static final String MESSAGE_FAILURE = "Unable to delete group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    public final GroupName groupName;

    public DeleteGroupCommand(GroupName groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Group toDelete = model.findGroup(groupName);
        if (toDelete != null) {
            if (model.deleteGroup(toDelete.getGroupId())) {

                // update main window display
                model.updateDetailWindowDisplay(new DetailWindowDisplay());

                // update side panel display
                model.updateSidePanelDisplay(SidePanelDisplayType.GROUPS);

                return new CommandResult(MESSAGE_SUCCESS);
            }
        }
        return new CommandResult(MESSAGE_FAILURE);
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
