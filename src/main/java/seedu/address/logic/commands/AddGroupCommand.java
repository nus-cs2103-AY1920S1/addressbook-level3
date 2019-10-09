package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;

/**
 * Add a group.
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";
    public static final String MESSAGE_SUCCESS = "New group added: ";
    public static final String MESSAGE_FAILURE = "Unable to add group: Group already exist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    private final GroupDescriptor groupDescriptor;

    public AddGroupCommand(GroupDescriptor groupDescriptor) {
        requireNonNull(groupDescriptor);
        this.groupDescriptor = groupDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (model.findGroup(groupDescriptor.getGroupName()) != null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        Group group = model.addGroup(groupDescriptor);
        if (group != null) {

            // updates main window
            model.updateDetailWindowDisplay(group.getGroupName(), LocalDateTime.now(), DetailWindowDisplayType.EMPTY);

            // updates side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUPS);

            return new CommandResult(MESSAGE_SUCCESS + group.details());
        } else {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof AddGroupCommand)) {
            return false;
        } else if (((AddGroupCommand) command).groupDescriptor.equals(this.groupDescriptor)) {
            return true;
        } else {
            return false;
        }
    }
}
