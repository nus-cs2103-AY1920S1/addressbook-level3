package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAB_CHANGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EARNINGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.model.Model;
import seedu.address.model.WindowView;


/**
 * Changes the tab of the address book.
 */
public class ChangeTabCommand extends Command {

    public static final String COMMAND_WORD = "change_tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change tab to any of the available ones \n"

            + "Parameters: "
            + PREFIX_TAB_CHANGE + "TAB_DESTINATION \n"

            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "notepad\n"
            + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "earnings\n"
            + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "student_profile\n"
            + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "calendar\n"
            + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "reminders";

    public static final String MESSAGE_SUCCESS_CALENDAR = "Changed tab to CALENDAR";
    public static final String MESSAGE_SUCCESS_TASK = "Changed tab to TASK";
    public static final String MESSAGE_SUCCESS_EARNINGS = "Changed tab to EARNINGS";
    public static final String MESSAGE_SUCCESS_STUDENT_PROFILE = "Changed tab to STUDENT_PROFILE";
    public static final String MESSAGE_SUCCESS_NOTEPAD = "Changed tab to NOTEPAD";
    public static final String MESSAGE_SUCCESS_REMINDERS = "Changed tab to REMINDERS";
    public static final String MESSAGE_ERROR = "Error: Unable to change tab";

    private WindowView newView;

    public ChangeTabCommand(WindowView newView) {
        requireNonNull(newView);

        this.newView = newView;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (newView.getIndexNumber() == 2) {
            model.updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
            return new CommandResult(MESSAGE_SUCCESS_EARNINGS);
        } else if (newView.getIndexNumber() == 1) {
            model.updateFilteredCalendarList();
            return new CommandResult(MESSAGE_SUCCESS_CALENDAR);
        } else if (newView.getIndexNumber() == 3) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_STUDENT_PROFILE);
        } else if (newView.getIndexNumber() == 4) {
            model.updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
            return new CommandResult(MESSAGE_SUCCESS_NOTEPAD);
        } else if (newView.getIndexNumber() == 5) {
            model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
            return new CommandResult(MESSAGE_SUCCESS_REMINDERS);
        } else if (newView.getIndexNumber() == 6) {
            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            return new CommandResult(MESSAGE_SUCCESS_TASK);
        } else {
            return new CommandResult(MESSAGE_ERROR);
        }
    }
}
