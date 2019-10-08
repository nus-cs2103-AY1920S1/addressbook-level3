package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import javafx.collections.ObservableList;
import seedu.tarence.commons.core.Messages;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.student.StudentsInTutorialPredicate;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Lists all persons in the application (or class) to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "liststu", "liststud"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "To list all students, just type list";

    private final StudentsInTutorialPredicate predicate;
    private final boolean showAllStudents;

    public ListCommand(boolean showAllStudents) {
        this.showAllStudents = showAllStudents;
        this.predicate = null;
    }

    public ListCommand(StudentsInTutorialPredicate predicate) {
        this.predicate = predicate;
        this.showAllStudents = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.showAllStudents) {
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        } else {
            setTutorialAndModule(model);
            model.updateFilteredStudentList(predicate);
        }
        return new CommandResult(MESSAGE_SUCCESS);
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

    /**
     * Sets the required tutorial name and module code to be filtered out
     */
    private void setTutorialAndModule(Model model) throws CommandException {
        try {
            ObservableList<Tutorial> tutorialList = model.getFilteredTutorialList();
            ObservableList<Module> modList = model.getFilteredModuleList();
            Tutorial tutorial = tutorialList.get(predicate.getIndex());
            predicate.setTutName(tutorial.getTutName());
            boolean match = modList.stream().anyMatch(mod -> mod.getTutorials().contains(tutorial));
            if (match) {
                predicate.setModCode(modList.get(predicate.getIndex()).getModCode());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (predicate == null) {
            return other == this || (other instanceof ListCommand);
        }
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && predicate.equals(((ListCommand) other).predicate)); // state check
    }
}
