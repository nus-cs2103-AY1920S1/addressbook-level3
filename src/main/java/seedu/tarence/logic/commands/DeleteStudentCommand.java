package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.student.Student;
import seedu.tarence.storage.Storage;

/**
 * Deletes a student identified using its displayed index from T.A.rence.
 */
public class DeleteStudentCommand extends Command {

    public static final String COMMAND_WORD = "deleteStudent";

    public static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "deletestu", "deletestud", "delstudent", "delstu", "delstud"};

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the index number used in the displayed person list.\n"
            + "Parameters:\n"
            + "INDEX (must be a positive integer)\n"
            + "Example:\n"
            + COMMAND_WORD + " 1\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private final Index targetIndex;

    public DeleteStudentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Student studentToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteStudent(studentToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, studentToDelete));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteStudentCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteStudentCommand) other).targetIndex)); // state check
    }
}
