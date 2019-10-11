package calofit.logic.commands;

import static java.util.Objects.requireNonNull;

import calofit.model.Model;

/**
 * Generates a statistical report containing various data.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_SUCCESS = "Report has been generated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        //Interact with model.Model.mealLog and model.CalorieBudget.budgetHistory
        model.updateStatistics();
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }

}
