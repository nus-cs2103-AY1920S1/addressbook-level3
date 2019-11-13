package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSROOM;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.classroom.Classroom;

//@@author weikiat97
/**
 * Deletes a classroom identified using it's displayed index from the notebook.
 */
public class DeleteClassroomCommand extends Command {

    public static final String COMMAND_WORD = "deleteclass";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the classroom identified by the user.\n"
            + "Parameters: " + PREFIX_CLASSROOM + "CLASSROOM_NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CLASSROOM + "4C";

    public static final String MESSAGE_DELETE_CLASSROOM_SUCCESS = "Deleted Classroom: %1$s";
    public static final String MESSAGE_NO_SUCH_CLASSROOM = "This classroom does not exist in the notebook";
    public static final String MESSAGE_BLANK_CLASSNAME = "A class must have a name.";



    private final Classroom toDelete;

    public DeleteClassroomCommand(Classroom classroom) {
        this.toDelete = classroom;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        if (!model.hasClassroom(toDelete)) {
            throw new CommandException(MESSAGE_NO_SUCH_CLASSROOM);
        }

        model.deleteClassroom(toDelete);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_DELETE_CLASSROOM_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteClassroomCommand // instanceof handles nulls
                && toDelete.equals(((DeleteClassroomCommand) other).toDelete)); // state check
    }
}
