package seedu.address.logic.commands.mark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Represents a Remove Mark Command.
 */
public class RemoveMarkCommand extends MarkCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes mark from a marked student that already exists in the student list\n"
            + "Parameters:\n"
            + "unmark \n"
            + "index/[STUDENT_INDEX]\n"
            + "Full Example: mark unmark index/1 --> unmarks student at index 1(must be previously marked)\n\n";

    public static final String MESSAGE_SUCCESS = "Student at index %1$d has been unmarked";
    public static final String MESSAGE_STUDENT_ALREADY_UNMARKED = "Student at index %1$d was not previously marked";

    private final Index toUnmark;

    /**
     * Creates a AddMarkCommand object.
     *
     * @param toUnmark index of student to be unmarked.
     */
    public RemoveMarkCommand(Index toUnmark) {
        requireAllNonNull(toUnmark);
        this.toUnmark = toUnmark;
    }

    /**
     * Executes the add student command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return a command result if the command is executed successfully.
     * @throws CommandException if the student does not exist in the student list/is already unmarked.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (toUnmark.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }
        Student studentToBeUnmarked = lastShownList.get(toUnmark.getZeroBased());
        if (!(studentToBeUnmarked.getIsMarked())) {
            throw new CommandException(String.format(MESSAGE_STUDENT_ALREADY_UNMARKED, toUnmark.getOneBased()));
        }
        studentToBeUnmarked.setUnmarked();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toUnmark.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveMarkCommand // instanceof handles nulls
                && toUnmark.equals(((RemoveMarkCommand) other).toUnmark));
    }
}
