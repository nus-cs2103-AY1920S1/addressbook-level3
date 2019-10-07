package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.mainwindow.MainWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupName;

import java.time.LocalDateTime;

/**
 * Edits a group details.
 */
public class EditGroupCommand extends Command {

    public static final String COMMAND_WORD = "editgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_EDIT + " GROUP_NAME "
            + "[" + PREFIX_REMARK + " REMARK]\n";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_SUCCESS = "Edited Group: \n\n";
    public static final String MESSAGE_FAILURE = "Unable to edit Group";

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

        if (!groupDescriptor.isAnyFieldEdited()) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        if (model.findGroup(groupName) == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        Group group = model.editGroup(groupName, groupDescriptor);

        if (group == null) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {

            // update main window display
            model.updateMainWindowDisplay(group.getGroupName(), LocalDateTime.now(), MainWindowDisplayType.SCHEDULE);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUPS);

            return new CommandResult(MESSAGE_SUCCESS + group.details());
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
