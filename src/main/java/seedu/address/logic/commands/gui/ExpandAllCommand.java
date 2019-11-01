package seedu.address.logic.commands.gui;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.semester.Semester;

/**
 * Expands all semesters.
 */
public class ExpandAllCommand extends Command {
    public static final String COMMAND_WORD = "expandall";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Expanding all semesters";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Expands all semesters.\n";

    public static final String MESSAGE_SUCCESS = "All semesters have been expanded.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        for (Semester s : model.getSemestersFromActiveSp()) {
            s.setExpanded(true);
        }
        return new CommandResult(MESSAGE_SUCCESS, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpandAllCommand); // instanceof handles nulls
    }
}
