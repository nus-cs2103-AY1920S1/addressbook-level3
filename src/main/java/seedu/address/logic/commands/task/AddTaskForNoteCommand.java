package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.task.Task;

/**
 * Adds a revision task for lecture note to the task list.
 */
public class AddTaskForNoteCommand extends Command {
    public static final String COMMAND_WORD = "rn";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note revision task to NUStudy. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Lecture 1 "
            + PREFIX_DATE + "15/10/2019 "
            + PREFIX_TIME + "1500";

    public static final String MESSAGE_SUCCESS = "Revision task added: %1$s";

    private final Task toAdd;

    /**
     * Creates an AddTaskForNoteCommand to add the specified {@code Task}
     */
    public AddTaskForNoteCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!hasValidNoteForTask(model)) {
            throw new CommandException(Messages.MESSAGE_NOTE_NOT_FOUND);
        }

        if (model.hasTask(toAdd)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    private boolean hasValidNoteForTask(Model model) {
        Note note = new Note (new Title(toAdd.getHeading().toString()), new Content("Dummy entry"));
        return model.hasNote(note);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskForNoteCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskForNoteCommand) other).toAdd));
    }
}
