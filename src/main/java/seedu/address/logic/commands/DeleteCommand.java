package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.NoSuchElementException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's name and role from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by his/her name and role.\n"
            + "Parameters: NAME (case-sensitive), ROLE (interviewee/interviewer)\n"
            + "Example: " + COMMAND_WORD + " John Doe" + "r/interviewee";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Name targetName;
    private final Role targetRole;

    public DeleteCommand(Name targetName, Role targetRole) {
        this.targetName = targetName;
        this.targetRole = targetRole;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        String deleted;
        try {
            if (targetRole.value.equals("interviewee")) {
                Interviewee i = model.getInterviewee(targetName.fullName);
                model.deleteInterviewee(i);
                deleted = i.toString();
            } else if (targetRole.value.equals("interviewer")) {
                Interviewer i = model.getInterviewer(targetName.fullName);
                model.deleteInterviewer(i);
                deleted = i.toString();
            } else {
                throw new AssertionError(Messages.MESSAGE_CRITICAL_ERROR);
            }
        } catch (NoSuchElementException | PersonNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_NAME);
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, deleted));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetName.equals(((DeleteCommand) other).targetName) // state check
                && targetName.equals(((DeleteCommand) other).targetName));
    }
}
