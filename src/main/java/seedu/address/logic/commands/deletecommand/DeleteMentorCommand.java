package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;

/**
 * Deletes a {@link Mentor} in Alfred.
 */
public class DeleteMentorCommand extends DeleteCommand {
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX = "The mentor ID provided is "
            + "invalid or does not exist.";
    public static final String MESSAGE_DELETE_MENTOR_SUCCESS = "Deleted Mentor: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " mentor"
            + ": Deletes the mentor by the ID shown in the list of mentors.\n"
            + "Format: " + COMMAND_WORD + " mentor ID\n"
            + "Example: " + COMMAND_WORD + " mentor M-1";

    public DeleteMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Mentor mentorToBeDeleted;
        try {
            mentorToBeDeleted = model.deleteMentor(this.id);
            model.updateHistory(this);
            model.recordCommandExecution(this.getCommandInputString());
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MENTOR_SUCCESS,
                mentorToBeDeleted.toString()), CommandType.M);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMentorCommand // instanceof handles nulls
                && id.equals(((DeleteMentorCommand) other).id));
    }
}
