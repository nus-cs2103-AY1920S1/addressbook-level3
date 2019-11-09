package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes an interviewee from Scheduler.
 */
public class DeleteIntervieweeCommand extends DeleteCommand {

    private final Name targetName;

    public DeleteIntervieweeCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String deleted;
        try {
            Interviewee toDelete = model.getInterviewee(targetName.fullName);
            model.deleteInterviewee(toDelete);
            model.updateFilteredIntervieweeList(Model.PREDICATE_SHOW_ALL_INTERVIEWEES);
            deleted = toDelete.toString();
        } catch (NoSuchElementException | PersonNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deleted));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteIntervieweeCommand // instanceof handles nulls
                && targetName.equals(((DeleteIntervieweeCommand) other).targetName)); // state check
    }
}
