package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import mams.model.Model;

/**
 * Lists all students in the MAMS to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_LIST_APPEALS_SUCCESS = "Listed all appeals";
    public static final String MESSAGE_LIST_MODULES_SUCCESS = "Listed all modules";
    public static final String MESSAGE_LIST_STUDENTS_SUCCESS = "Listed all students";

    public static final String NEWLINE = "\n";

    public static final String ASSERT_ERROR_MESSAGE = "Developer error: ListCommand should not have "
            + "been instantiated with all false booleans";

    private final boolean showAppeals;
    private final boolean showModules;
    private final boolean showStudents;

    public ListCommand() {
        this.showAppeals = true;
        this.showModules = true;
        this.showStudents = true;
    }

    public ListCommand(boolean showAppeals, boolean showModules, boolean showStudents) {
        this.showAppeals = showAppeals;
        this.showModules = showModules;
        this.showStudents = showStudents;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // it is responsibility of parser to enforce this condition, thus
        // we raise an AssertionError as this condition should not occur
        // ie. there is no graceful way for the app/user to handle this error
        // and recover from it without silently perpetuating a bug into future
        // iterations of MAMS.
        requireAtLeastOneTrue(showAppeals, showModules, showStudents);

        StringBuilder msg = new StringBuilder();

        if (showAppeals) {
            model.updateFilteredAppealList(Model.PREDICATE_SHOW_ALL_APPEALS);
            msg.append(MESSAGE_LIST_APPEALS_SUCCESS);
            msg.append(NEWLINE);
        }
        if (showModules) {
            model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
            msg.append(MESSAGE_LIST_MODULES_SUCCESS);
            msg.append(NEWLINE);
        }
        if (showStudents) {
            model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
            msg.append(MESSAGE_LIST_STUDENTS_SUCCESS);
            msg.append(NEWLINE);
        }

        return new CommandResult(msg.toString().trim());
    }

    /**
     * assert that at least one element in boolean array {@code params} is true
     * @param params
     */
    public static void requireAtLeastOneTrue(boolean... params) {
        boolean hasAtLeastOneTrue = false;
        for (boolean param : params) {
            hasAtLeastOneTrue = hasAtLeastOneTrue || param;
        }
        if (!hasAtLeastOneTrue) {
            throw new AssertionError(ASSERT_ERROR_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // state check
        ListCommand l = (ListCommand) other;
        return showAppeals == l.showAppeals
                && showModules == l.showModules
                && showStudents == l.showStudents;
    }
}
