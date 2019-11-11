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
public class BlockCurrentSemesterCommand extends Command {
    public static final String COMMAND_WORD = "block";
    public static final String HELP_MESSAGE = COMMAND_WORD
            + ": Block off the given semester, for reasons such as exchange, LOA, etc.";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Block off the given semester, for reasons such as exchange, LOA, etc.\n. "
            + "Parameters: "
            + "SEMESTER ";

    public static final String MESSAGE_SUCCESS = "Semester %1$s blocked";

    private final SemesterName sem;
    private final String reason;

    public BlockCurrentSemesterCommand(SemesterName sem, String reason) {
        requireNonNull(sem);
        this.sem = sem;
        this.reason = reason;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getActiveStudyPlan() == null) {
            throw new CommandException(MESSAGE_NO_STUDY_PLAN);
        }

        if (model.getSemester(sem).isBlocked()) {
            throw new CommandException("Semester is already blocked");
        }

        model.blockSemester(sem, reason);
        model.addToHistory();
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BlockCurrentSemesterCommand // instanceof handles nulls
                && sem.equals(((BlockCurrentSemesterCommand) other).sem)
                && reason.equals(((BlockCurrentSemesterCommand) other).reason));
    }
}
