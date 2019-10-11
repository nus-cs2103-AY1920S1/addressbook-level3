package seedu.address.logic.commands.deletecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;

/**
 * Deletes a {@link Mentor} in Alfred.
 */
public class DeleteMentorCommand extends DeleteCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX = "The mentor ID provided is invalid";
    public static final String MESSAGE_DELETE_MENTOR_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the mentor identified by the ID used in the displayed mentor list.\n"
            + "Parameters: mentor ID\n"
            + "Example: " + COMMAND_WORD + " M-1";

    private Name teamName;

    public DeleteMentorCommand(Id id) {
        super(id);
    }

    public DeleteMentorCommand(Id id, Name teamName) {
        super(id);
        requireNonNull(teamName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.teamName != null) {
            // find team (or throw Exception)
            // delete mentor from team
            // return CommandResult
        }

        Mentor mentorToBeDeleted;
        try {
            mentorToBeDeleted = model.deleteMentor(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MENTOR_SUCCESS,
                mentorToBeDeleted.toString()));
    }

}
