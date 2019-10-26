package seedu.address.logic.commands.storage;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Edits the title of an existing studyPlan in the module planner.
 */
public class EditTitleCommand extends Command {

    public static final String COMMAND_WORD = "title";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Editing the title of the current study plan";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Edits the title of the current active study plan "
            + "Parameters: "
            + "PLAN_TITLE \n"
            + "Example: "
            + "title Algo and Graphics";

    public static final String MESSAGE_EDIT_STUDYPLAN_SUCCESS = "Edited StudyPlan: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDYPLAN = "This studyPlan already exists in the module planner.";

    private final String newTitle;

    public EditTitleCommand(String newTitle) {
        requireNonNull(newTitle);
        this.newTitle = newTitle;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.changeActiveStudyPlanTitle(newTitle);

        return new CommandResult(String.format(MESSAGE_EDIT_STUDYPLAN_SUCCESS, newTitle), true, false);
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
