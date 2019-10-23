package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;



/**
 * Adds an assignment to the classroom.
 */
public class AddAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "addassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an assignment \n"
                                                   + "Parameters: "
                                                   + PREFIX_ASSIGNMENT + "ASSIGNMENTNAME "
                                                   + "Example: " + COMMAND_WORD + " "
                                                   + PREFIX_ASSIGNMENT + "Math Test ";
    public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the classroom";

    private final Assignment toAdd;

    /**
     * Creates an AddAssignmentCommand to add the specified {@code Assignment}
     */

    public AddAssignmentCommand(Assignment assignment) {
        requireNonNull(assignment);
        toAdd = assignment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasAssignment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }

        model.addAssignment(toAdd);
        model.saveState();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof AddCommand // instanceof handles nulls
                           && toAdd.equals(((AddAssignmentCommand) other).toAdd));
    }
}
