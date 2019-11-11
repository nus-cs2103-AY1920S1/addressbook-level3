package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;
import static seedu.address.logic.commands.cli.AddModuleCommand.MESSAGE_SEMESTER_BLOCKED;
import static seedu.address.logic.commands.cli.AddModuleCommand.MESSAGE_SEMESTER_DOES_NOT_EXIST;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Collapses a given semester.
 */
public class CollapseCommand extends Command {
    public static final String COMMAND_WORD = "collapse";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Collapsing the indicated semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the given semester to be collapsed.\n"
            + "Parameters: "
            + "SEMESTER\n";

    public static final String MESSAGE_SUCCESS = "Semester %1$s has been collapsed.";
    public static final String MESSAGE_FAILURE = "Semester %1$s is already collapsed.";

    private final SemesterName sem;

    public CollapseCommand(SemesterName sem) {
        requireNonNull(sem);
        this.sem = sem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (model.getSemester(this.sem) == null) {
            throw new CommandException(MESSAGE_SEMESTER_DOES_NOT_EXIST);
        }

        if (model.getSemester(this.sem).isBlocked()) {
            throw new CommandException(MESSAGE_SEMESTER_BLOCKED);
        }

        if (!model.getSemester(sem).isExpanded()) {
            return new CommandResult(String.format(MESSAGE_FAILURE, sem), true, false);
        }

        model.getSemester(sem).setExpanded(false);
        model.addToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CollapseCommand // instanceof handles nulls
                && sem.equals(((CollapseCommand) other).sem));
    }
}
