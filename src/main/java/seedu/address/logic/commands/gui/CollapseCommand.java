package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;

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

    private final SemesterName sem;

    public CollapseCommand(SemesterName sem) {
        requireNonNull(sem);
        this.sem = sem;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.getSemester(sem).setExpanded(false);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem), true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CollapseCommand // instanceof handles nulls
                && sem.equals(((CollapseCommand) other).sem));
    }
}
