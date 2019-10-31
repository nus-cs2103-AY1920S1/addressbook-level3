package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewer;

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
