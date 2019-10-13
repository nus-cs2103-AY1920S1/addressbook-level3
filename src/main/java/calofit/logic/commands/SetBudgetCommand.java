package calofit.logic.commands;

import java.util.Objects;

import calofit.logic.commands.exceptions.CommandException;
import calofit.model.Model;
import calofit.model.dish.Calorie;

/**
 * Sets the current calorie budget value.
 */
public class SetBudgetCommand extends Command {
    private Calorie budget;

    public SetBudgetCommand(Calorie calories) {
        this.budget = calories;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.getCalorieBudget().setCurrentBudget(budget.getValue());
        return new CommandResult(String.format("Budget set to %d!", budget.getValue()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetBudgetCommand that = (SetBudgetCommand) o;
        return Objects.equals(budget, that.budget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budget);
    }
}
