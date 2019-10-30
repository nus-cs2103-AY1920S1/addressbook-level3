package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSROOMNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Classroom;
import seedu.address.model.Model;


/**
 * Adds a classroom to the notebook.
 */
public class AddClassroomCommand extends Command {

    public static final String COMMAND_WORD = "addclass";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a classroom "
                                                       + "Parameters: "
                                                       + PREFIX_CLASSROOMNAME + "CLASSROOMNAME ";

    public static final String MESSAGE_SUCCESS = "New classroom added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLASSROOM = "This classroom already exists in the notebook";


    private final Classroom toAdd;

    public AddClassroomCommand(String classroomName) {
        requireNonNull(classroomName);
        toAdd = new Classroom(classroomName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClassroom(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLASSROOM);
        }

        model.addClassroom(toAdd);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                       || (other instanceof AddLessonCommand // instanceof handles nulls
                                   && toAdd.equals(((AddClassroomCommand) other).toAdd));
    }
}
