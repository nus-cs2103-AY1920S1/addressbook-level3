package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;
import seedu.billboard.model.expense.Expense;

import java.util.List;

public class TestCommand extends Command {
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Expense> expenses = model.getFilteredExpenses();
        for (Expense expense : expenses) {
            model.addArchiveExpense("test", expense);
        }
        return new CommandResult("test done");
    }
}
