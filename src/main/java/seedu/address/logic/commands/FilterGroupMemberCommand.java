package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.person.Name;

/**
 * Command that filters group members by name.
 */
public class FilterGroupMemberCommand extends Command {
    public static final String COMMAND_WORD = "filtergroupmembers";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " PERSON NAME";

    public static final String MESSAGE_SUCCESS = "Filtered members: ";
    public static final String MESSAGE_FAILURE = "No group schedule is shown. Cannot filter anything";

    private List<Name> membersToBeFiltered;

    public FilterGroupMemberCommand(List<Name> membersToBeFiltered) {
        this.membersToBeFiltered = membersToBeFiltered;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ScheduleWindowDisplayType status = model.getState();
        if (!status.equals(ScheduleWindowDisplayType.GROUP) && !status.equals(ScheduleWindowDisplayType.NONE)) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.getScheduleWindowDisplay().setFilteredNames(membersToBeFiltered);
        return new CommandResult(MESSAGE_SUCCESS, false, false,
                false, false, false, false, false, true);
    }

    @Override
    public boolean equals(Command command) {
        if (command == this) {
            return true;
        } else if (command instanceof FilterGroupMemberCommand) {
            //Same list of names to be filtered. Order does not matter.
            int numberOfMatch = 0;
            FilterGroupMemberCommand filterGroupMemberCommand = (FilterGroupMemberCommand) command;
            for (Name name : membersToBeFiltered) {
                if (filterGroupMemberCommand.membersToBeFiltered.contains(name)) {
                    numberOfMatch++;
                }
            }
            return numberOfMatch == membersToBeFiltered.size();
        } else {
            return false;
        }
    }
}
