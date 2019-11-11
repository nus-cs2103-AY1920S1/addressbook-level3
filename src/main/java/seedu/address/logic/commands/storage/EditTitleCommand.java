package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyplan.Title;
import seedu.address.model.studyplan.exceptions.InvalidTitleException;

/**
 * Edits the title of an existing studyPlan in the module planner.
 */
public class EditTitleCommand extends Command {

    public static final String COMMAND_WORD = "title";

    public static final String HELP_MESSAGE = COMMAND_WORD + ": Editing the title of the current study plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the title of the current active study plan.\n"
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: "
            + "title Algo and Graphics";
    public static final String MESSAGE_SUCCESS = "Edited study plan: %1$s";

    private final String newTitle;

    public EditTitleCommand(String newTitle) {
        requireNonNull(newTitle);
        this.newTitle = newTitle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.changeActiveStudyPlanTitle(newTitle);
            model.addToHistory();
        } catch (InvalidTitleException e) {
            throw new CommandException(Title.MESSAGE_CONSTRAINTS);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, newTitle), true, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTitleCommand)) {
            return false;
        }

        // state check
        EditTitleCommand e = (EditTitleCommand) other;
        return newTitle.equals(e.newTitle);
    }

}
