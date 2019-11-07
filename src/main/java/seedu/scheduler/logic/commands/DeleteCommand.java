package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.NoSuchElementException;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Role;
import seedu.scheduler.model.person.RoleType;
import seedu.scheduler.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's name and role from the scheduler book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an entity identified by the entity's name. "
            + "An entity can either be an interviewee or interviewer.\n"
            + "Parameters to delete interviewees:\n"
            + "NAME " + PREFIX_ROLE + "interviewee\n"
            + "Parameters to delete interviewees:\n"
            + "NAME " + PREFIX_ROLE + "interviewer\n"
            + "Examples:\n"
            + COMMAND_WORD + " John Doe " + "r/interviewee\n"
            + COMMAND_WORD + " Mary Jane " + "r/interviewer";

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
            if (targetRole.getRole().equals(RoleType.INTERVIEWEE)) {
                Interviewee i = model.getInterviewee(targetName.fullName);
                model.deleteInterviewee(i);
                model.updateFilteredIntervieweeList(Model.PREDICATE_SHOW_ALL_INTERVIEWEES);
                deleted = i.toString();
            } else if (targetRole.getRole().equals(RoleType.INTERVIEWER)) {
                Interviewer i = model.getInterviewer(targetName.fullName);
                model.deleteInterviewer(i);
                model.updateFilteredInterviewerList(Model.PREDICATE_SHOW_ALL_INTERVIEWERS);
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
