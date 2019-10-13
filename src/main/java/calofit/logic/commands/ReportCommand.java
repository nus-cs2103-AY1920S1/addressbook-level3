package calofit.logic.commands;

import static java.util.Objects.requireNonNull;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;

/**
 * Generates a statistical report containing various data.
 */
public class ReportCommand extends Command {

    public static final String COMMAND_WORD = "report";

    public static final String MESSAGE_SUCCESS = "Report has been generated";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getMealLog().getMeals().size() == 0) {
            throw new CommandException("You currently have no meals in CaloFit. Get Started! :)");
        }
        model.updateStatistics();
        return new CommandResult(MESSAGE_SUCCESS, false, true, false);
    }

}
