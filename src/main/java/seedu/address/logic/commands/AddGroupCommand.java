package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.exceptions.DuplicateGroupException;

/**
 * Add a group.
 */
public class AddGroupCommand extends Command {
    public static final String COMMAND_WORD = "addgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_GROUPNAME + "GROUP_NAME" + " "
            + "[" + PREFIX_DESCRIPTION + "GROUP_DESCRIPTION]" + " "
            + "[" + PREFIX_ROLE + "USER_ROLE]";

    public static final String MESSAGE_SUCCESS = "New group added: %s";
    public static final String MESSAGE_FAILURE = "Unable to add group: %s";

    public static final String MESSAGE_DUPLICATE_GROUP = "Group already exists";

    private final GroupDescriptor groupDescriptor;

    public AddGroupCommand(GroupDescriptor groupDescriptor) {
        requireNonNull(groupDescriptor);
        this.groupDescriptor = groupDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            Group group = model.addGroup(groupDescriptor);

            // updates main window
            model.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.HOME);

            // updates side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUP);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, group.getGroupName().toString())).build();

        } catch (DuplicateGroupException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_GROUP)).build();
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
