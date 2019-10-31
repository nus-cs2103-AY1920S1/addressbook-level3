package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Interviewer;

/**
 * Adds an Interviewer to the {@code InterviewerList}.
 */
public class AddInterviewerCommand extends AddCommand {

    private final Interviewer toAdd;

    public AddInterviewerCommand(Interviewer toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInterviewer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addInterviewer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddInterviewerCommand // instanceof handles nulls
                && toAdd.equals(((AddInterviewerCommand) other).toAdd));
    }
}
