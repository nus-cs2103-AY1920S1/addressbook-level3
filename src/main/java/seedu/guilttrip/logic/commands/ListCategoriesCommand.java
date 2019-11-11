package seedu.guilttrip.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.entry.Category;

/**
 * Lists the available Categories in GuiltTrip.
 */
public class ListCategoriesCommand extends Command {

    public static final String COMMAND_WORD = "listCategories";
    public static final String ONE_LINER_DESC = COMMAND_WORD + ": Lists the available Categories in GuiltTrip.";
    public static final String MESSAGE_USAGE = ONE_LINER_DESC;
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
