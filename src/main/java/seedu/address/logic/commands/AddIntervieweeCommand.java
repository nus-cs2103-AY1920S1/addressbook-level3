package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Interviewee;

/**
 * Adds an Interviewee to the {@code IntervieweeList}.
 */
public class AddIntervieweeCommand extends AddCommand {

    private final Interviewee toAdd;

    public AddIntervieweeCommand(Interviewee toAdd) {
        requireNonNull(toAdd);
        this.toAdd = toAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasInterviewee(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addInterviewee(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddIntervieweeCommand // instanceof handles nulls
                && toAdd.equals(((AddIntervieweeCommand) other).toAdd));
    }
}
