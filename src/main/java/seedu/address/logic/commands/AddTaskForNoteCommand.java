package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.task.Task;

public class AddTaskForNoteCommand extends Command {
    public static final String COMMAND_WORD = "radd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a revision task to NUStudy. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE + "DATE"
            + PREFIX_TIME + "TIME"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Lecture 1 "
            + PREFIX_DATE + "15/10/2019"
            + PREFIX_TIME + "1500";

    public static final String MESSAGE_SUCCESS = "Revision task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TITLE = "This task already exists";

    private final Task toAdd;

    /**
     * Creates an AddNoteCommand to add the specified {@code Note}
     */
    public AddTaskForNoteCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Note> lastShownList = model.getFilteredNoteList();
        Note note = toAdd.getNote();
        for (int i = 0; i < lastShownList.size(); i++) {
            if (note.isSameNote(lastShownList.get(i))) {
                note = lastShownList.get(i);
                break;
            }
        }

        toAdd.setNote(note);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TITLE);
        }

        model.addTask(toAdd);
        System.out.println("Task" + toAdd.toString() + "is added");
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskForNoteCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskForNoteCommand) other).toAdd));
    }
}
