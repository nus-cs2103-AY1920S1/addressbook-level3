package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows attendance of a person identified using it's displayed index from the address book.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks attendance of person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_SUCCESS = "Attendance shown.";

    /**
     * Index of person identified.
     */
    private Index index;

    /**
     * Constructor of AttendanceCommand.
     * @param index Sets Index of person identified to {@code index}
     */
    public AttendanceCommand(Index index) {
        this.index = index;
    }

    /**
     * Executes AttendanceCommand. Displays the attendance of Person specified by the index.
     * @param model {@code Model} which the command should operate on.
     * @return Outcome of executed command.
     * @throws CommandException Thrown when specified index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(index.getZeroBased());

        StringBuilder result = new StringBuilder();
        result.append(MESSAGE_SUCCESS);
        result.append("\n");
        result.append(person.getName().toString());
        result.append(": ");
        result.append(model.getPersonAttendanceRateString(person));

        return new CommandResult(result.toString());
    }
    /**
     * Indicates whether an AttendanceCommand can be undone.
     * @return Returns false as a AttendanceCommand is not undoable.
     */
    @Override
    public boolean isUndoable() {
        return false;
    }
    @Override
    public String toString() {
        return "Attendance Command";
    }
}
