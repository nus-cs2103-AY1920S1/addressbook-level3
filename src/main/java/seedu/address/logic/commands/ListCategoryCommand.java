package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Category;

/**
 * Lists the available Categories in guiltTrip.
 */
public class ListCategoryCommand extends Command {

    public static final String COMMAND_WORD = "listCategory";

    public static final String MESSAGE_SUCCESS = "Listed all categories \n Income: %1$s \n Expense: %2$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        ObservableList<Category> incomeCategory = model.getIncomeCategoryList();
        String incomeCategoryString = incomeCategory.stream().map(e -> e.toString()).collect(Collectors.joining(","));
        ObservableList<Category> expenseCategory = model.getExpenseCategoryList();
        String expenseCategoryString = expenseCategory.stream().map(e -> e.toString()).collect(Collectors.joining(","));
        return new CommandResult(String.format(MESSAGE_SUCCESS, incomeCategoryString, expenseCategoryString));
    }
}
