package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes an Interviewer from Scheduler.
 */
public class DeleteInterviewerCommand extends DeleteCommand {

    private final Name targetName;

    public DeleteInterviewerCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String deleted;
        try {
            Interviewer toDelete = model.getInterviewer(targetName.fullName);
            model.deleteInterviewer(toDelete);
            model.updateFilteredInterviewerList(Model.PREDICATE_SHOW_ALL_INTERVIEWERS);
            deleted = toDelete.toString();
        } catch (NoSuchElementException | PersonNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deleted));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteInterviewerCommand // instanceof handles nulls
                && targetName.equals(((DeleteInterviewerCommand) other).targetName)); // state check
    }
}
