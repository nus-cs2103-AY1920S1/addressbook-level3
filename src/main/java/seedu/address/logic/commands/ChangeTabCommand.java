package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAB_CHANGE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EARNINGS;

import seedu.address.model.Model;
import seedu.address.model.WindowView;


/**
 * Changes the tab of the address book.
 */
public class ChangeTabCommand extends Command {

    public static final String COMMAND_WORD = "change_tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change tab to any of the available ones \n"

            + "Parameters: "
            + PREFIX_TAB_CHANGE + "tab/TAB_DESTINATION \n"

            + "Example: \n" + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "notepad\n"
            + COMMAND_WORD + " "
            + PREFIX_TAB_CHANGE + "earnings";

    public static final String MESSAGE_SUCCESS_CALENDAR = "Changed tab to CALENDAR";
    public static final String MESSAGE_SUCCESS_EARNINGS = "Changed tab to EARNINGS";
    public static final String MESSAGE_SUCCESS_STUDENT_PROFILE = "Changed tab to STUDENT_PROFILE";
    public static final String MESSAGE_SUCCESS_NOTEPAD = "Changed tab to NOTEPAD";
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
        } else {
            return new CommandResult(MESSAGE_ERROR);
        }
    }
}
