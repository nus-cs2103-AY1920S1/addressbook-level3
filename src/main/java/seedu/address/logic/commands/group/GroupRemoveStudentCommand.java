package seedu.address.logic.commands.group;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class GroupRemoveStudentCommand extends GroupCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an existing student from an existing group\n"
            + "Parameters:\n"
            + "groupId/ [GROUP_ID]\n"
            + "Example: groupId/ G03\n\n"
            + "groupIndexNumber/ [INDEX_NUMBER]\n"
            + "Example: groupIndexNumber/ 2 (Specifies the student number in the group to remove)";

    private final String groupId;
    private final int groupIndexNumber;

    /**
     * Creates a QuizRemoveQuestionCommand instance with the appropriate attributes.
     * @param groupId The identifier of the quiz.
     * @param groupIndexNumber The question number of the quiz to be removed.
     */
    public GroupRemoveStudentCommand(String groupId, int groupIndexNumber) {
        this.groupId = groupId;
        this.groupIndexNumber = groupIndexNumber;
    }

    /**
     * Executes the user command.
     * @param model {@code Model} which the command should operate on.
     * @return The result of the command.
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.removeStudentFromGroup(groupId, groupIndexNumber);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates a command execution success message.
     * @return The String representation of a success message.
     */
    private String generateSuccessMessage() {
        return "Removed student: " + groupIndexNumber + " from group: " + groupId;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupRemoveStudentCommand)) {
            return false;
        }

        // state check
        GroupRemoveStudentCommand e = (GroupRemoveStudentCommand) other;
        return this.groupId.equals(e.groupId);
    }
}
