package seedu.address.logic.commands.viewcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;

/**
 * Shows detailed view of the {@link Mentor} at specified ID.
 */
public class ViewMentorCommand extends ViewCommand {

    public static final String COMMAND_WORD = "view mentor";
    public static final String MESSAGE_SUCCESS = "Showing mentor with ID: %s"; // %s -> Id
    public static final String MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX = "The mentor index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " mentor"
            + ": shows details of the mentor with specified ID. \n"
            + "Format: view mentor [mentor ID] \n"
            + "Example: " + COMMAND_WORD + " mentor M-1";

    public ViewMentorCommand(Id id) {
        super(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Mentor mentorToView;
        try {
            mentorToView = model.getMentor(this.id);
            model.updateHistory(this);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_MENTOR_DISPLAYED_INDEX);
        }
        model.viewEntity(mentorToView);
        this.displayDetailedEntity(mentorToView);

        return new CommandResult(String.format(MESSAGE_SUCCESS, this.id), CommandType.M);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewMentorCommand // instanceof handles nulls
                && id.equals(((ViewMentorCommand) other).id));
    }
}
