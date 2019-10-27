package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Find a group.
 */
public class FindGroupCommand extends Command {
    public static final String COMMAND_WORD = "findgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    public static final String MESSAGE_SUCCESS = "Found group: %s";
    public static final String MESSAGE_FAILURE = "Unable to find group: %s does not exist";

    public final GroupName groupName;

    public FindGroupCommand(GroupName groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            Group group = model.findGroup(groupName);

            // update main window
            model.updateDetailWindowDisplay(group.getGroupName(), LocalDateTime.now(), DetailWindowDisplayType.GROUP);

            //update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.GROUPS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, groupName.toString()));

        } catch (GroupNotFoundException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, groupName.toString()));
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof FindGroupCommand)) {
            return false;
        } else if (((FindGroupCommand) command).groupName.equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
