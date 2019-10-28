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

    private final boolean showAppeals;
    private final boolean showModules;
    private final boolean showStudents;

    public ListCommand(boolean showAppeals, boolean showModules, boolean showStudents) {
        this.showAppeals = showAppeals;
        this.showModules = showModules;
        this.showStudents = showStudents;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

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
