package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;

/**
 * Command to show the details of a person.
 */
public class ShowGroupCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SUCCESS = "Showing group: %1$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "This group does not exists in time book!";
    public static final String MESSAGE_USAGE = "Show command takes in a person's or group's name as argument!";

    private final GroupName groupName;

    public ShowGroupCommand(GroupName groupName) {
        requireNonNull(groupName);
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Group> groupList = model.getObservableGroupList();

        Optional<Group> group = Optional.empty();
        for (Group g : groupList) {
            if (g.getGroupName().equals(groupName)) {
                group = Optional.of(g);
                break;
            }
        }

        if (group.isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }

        model.updateDetailWindowDisplay(groupName, LocalDateTime.now(), DetailWindowDisplayType.GROUP);
        return new CommandResult(String.format(MESSAGE_SUCCESS, group.get()), false,
                false);
    }

    @Override
    public boolean equals(Command command) {
        return this == command //short circuit if same command
                || (command instanceof ShowGroupCommand)
                && (groupName.equals(((ShowGroupCommand) command).groupName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowGroupCommand // instanceof handles nulls
                && groupName.equals(((ShowGroupCommand) other).groupName));
    }

}
