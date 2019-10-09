package seedu.address.logic.commands.cli;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.SemesterName;

public class BlockCurrentSemesterCommand extends Command {
    public static final String COMMAND_WORD = "block";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Block off the given semester, for reasons such as exchange, LOA, etc.\n. "
            + "Parameters: "
            + PREFIX_SEMESTER + "SEMESTER ";

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
        model.blockSemester(sem, reason);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sem));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BlockCurrentSemesterCommand // instanceof handles nulls
                && sem.equals(((BlockCurrentSemesterCommand) other).sem));
    }
}
