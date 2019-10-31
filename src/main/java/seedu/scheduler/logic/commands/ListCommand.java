package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.model.Model.PREDICATE_SHOW_ALL_INTERVIEWEES;
import static seedu.scheduler.model.Model.PREDICATE_SHOW_ALL_INTERVIEWERS;

import seedu.scheduler.model.Model;

/**
 * Lists all persons in the scheduler book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredIntervieweeList(PREDICATE_SHOW_ALL_INTERVIEWEES);
        model.updateFilteredInterviewerList(PREDICATE_SHOW_ALL_INTERVIEWERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
