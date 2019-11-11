package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;
import static seedu.address.logic.commands.cli.AddModuleCommand.MESSAGE_SEMESTER_DOES_NOT_EXIST;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Sets a semester as the current semester
 */
public class SetCurrentSemesterCommand extends Command {
    public static final String COMMAND_WORD = "setcurrent";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Setting the current semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the given semester as the current one. "
            + "Semesters before the specified semester will be locked and therefore modules cannot"
            + " be added or removed from them.\n"
            + "Parameters: "
            + "SEMESTER\n";

    public static final String MESSAGE_SUCCESS = "Semester %1$s has been set as the current semester";

    private final SemesterName sem;

    public SetCurrentSemesterCommand(SemesterName sem) {
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

        model.setSemester(sem);
        model.updateAllCompletedTags();
        model.addToHistory();

        return new CommandResult(String.format(MESSAGE_SUCCESS, sem), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCurrentSemesterCommand // instanceof handles nulls
                && sem.equals(((SetCurrentSemesterCommand) other).sem));
    }
}
