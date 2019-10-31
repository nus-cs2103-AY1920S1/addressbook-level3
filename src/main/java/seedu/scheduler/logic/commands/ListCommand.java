package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;

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
        model.updateFilteredIntervieweeList(Model.PREDICATE_SHOW_ALL_INTERVIEWEES);
        model.updateFilteredInterviewerList(Model.PREDICATE_SHOW_ALL_INTERVIEWERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
