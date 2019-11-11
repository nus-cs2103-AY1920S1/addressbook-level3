package seedu.address.logic.commands.cli;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NO_STUDY_PLAN;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

/**
 * Blocks the current semester with a specified reason
 */
public class UnblockCurrentSemesterCommand extends Command {
    public static final String COMMAND_WORD = "unblock";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Unblocks the given semester";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unblocks the given semester. \n"
            + "Parameters: "
            + "SEMESTER ";

    public static final String MESSAGE_SUCCESS = "Semester %1$s unblocked";

    private final SemesterName sem;

    public UnblockCurrentSemesterCommand(SemesterName sem) {
        requireNonNull(sem);
        this.sem = sem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (!model.getSemester(sem).isBlocked()) {
            throw new CommandException("Semester is not blocked");
        }

        model.unblockSemester(sem);
        model.addToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnblockCurrentSemesterCommand // instanceof handles nulls
                && sem.equals(((UnblockCurrentSemesterCommand) other).sem));
    }
}
