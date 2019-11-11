package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;

/**
 * Collapses all semesters.
 */
public class CollapseAllCommand extends Command {
    public static final String COMMAND_WORD = "collapseall";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Collapsing all semesters";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Collapses all semesters.\n";

    public static final String MESSAGE_SUCCESS = "All semesters have been collapsed.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        for (Semester s : model.getSemestersFromActiveSp()) {
            s.setExpanded(false);
        }
        model.addToHistory();
        return new CommandResult(MESSAGE_SUCCESS, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CollapseAllCommand); // instanceof handles nulls
    }
}
