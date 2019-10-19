package seedu.moneygowhere.logic.commands;

import java.util.TreeMap;

import seedu.moneygowhere.model.Model;

/**
 * Displays the current Budget.
 */
public class ShowBudgetCommand extends Command {

    public static final String COMMAND_WORD = "showbudget";

    public static final String MESSAGE_SUCCESS = "Your Monthly budget is ";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS + model.getBudget());
    }

}
