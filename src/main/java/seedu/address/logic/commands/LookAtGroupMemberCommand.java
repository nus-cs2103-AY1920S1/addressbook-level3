package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.GroupScheduleDisplay;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.person.Name;

/**
 * Command that filters group members by name.
 */
public class LookAtGroupMemberCommand extends Command {
    public static final String COMMAND_WORD = "lookat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " allows only " + PREFIX_NAME + " PERSON NAME";

    public static final String MESSAGE_SUCCESS = "Looking at members: %s";
    public static final String MESSAGE_NOT_FOUND = "Members that are not found in this group: %s";
    public static final String MESSAGE_FAILURE = "No group schedule is shown. Cannot filter anything";

    private List<Name> membersToBeFiltered;

    public LookAtGroupMemberCommand(List<Name> membersToBeFiltered) {
        this.membersToBeFiltered = membersToBeFiltered;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        ScheduleState status = model.getState();

        if (!status.equals(ScheduleState.GROUP)) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        GroupScheduleDisplay groupScheduleDisplay = (GroupScheduleDisplay) model.getScheduleDisplay();
        groupScheduleDisplay.setFilteredNames(membersToBeFiltered);
        //model.updateScheduleWindowDisplay(groupScheduleDisplay);

        //Present names.
        List<Name> namesFoundInGroup = groupScheduleDisplay.getFilteredNames().get();
        StringBuilder found = new StringBuilder();
        for (Name name : namesFoundInGroup) {
            found.append(name.fullName);
            found.append(' ');
        }
        StringBuilder notFound = new StringBuilder();
        if (namesFoundInGroup.size() < membersToBeFiltered.size()) {
            //The given names contain names that are not found in the group.
            for (Name name : membersToBeFiltered) {
                if (!namesFoundInGroup.contains(name)) {
                    notFound.append(name.fullName);
                    notFound.append(' ');
                }
            }
        }
        String successMessage = String.format(MESSAGE_SUCCESS, found.toString().trim());
        final String feedback = notFound.toString().equals("")
                ? successMessage
                : successMessage + "\n" + String.format(MESSAGE_NOT_FOUND, notFound.toString().trim());

        return new CommandResultBuilder(feedback)
                .setFilter().build();

    }

    @Override
    public boolean equals(Command command) {
        if (command == this) {
            return true;
        } else if (command instanceof LookAtGroupMemberCommand) {
            //Same list of names to be filtered. Order does not matter.
            int numberOfMatch = 0;
            LookAtGroupMemberCommand otherCommand = (LookAtGroupMemberCommand) command;
            for (Name name : this.membersToBeFiltered) {
                if (otherCommand.membersToBeFiltered.contains(name)) {
                    numberOfMatch++;
                }
            }
            return numberOfMatch == membersToBeFiltered.size();
        } else {
            return false;
        }
    }
}
