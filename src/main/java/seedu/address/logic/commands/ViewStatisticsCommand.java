//package seedu.address.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
//
//import java.util.stream.Collectors;
//
//import javafx.collections.ObservableList;
//import seedu.address.logic.CommandHistory;
//import seedu.address.model.Model;
//import seedu.address.model.person.Category;
//
//public class ViewStatisticsCommand {
//
//    public static final String COMMAND_WORD = "viewStatistics";
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View Statistics of guiltTrip(). Use a comma to "
//            + "seperate the two dates. \n"
//            + "Parameters: "
//            + PREFIX_TYPE + "TYPE "
//            + PREFIX_PERIOD + "PERIOD "
//            + "Example: " + COMMAND_WORD + " "
//            + PREFIX_TYPE + "Expense "
//            + PREFIX_PERIOD + "11/9/2019, 17/9/2019 ";
//    public static final String MESSAGE_SUCCESS = "Listed all statistics for Expense and Income.";
//
//    @Override
//    public CommandResult execute(Model model, CommandHistory history) {
//        requireNonNull(model);
//        ObservableList<Category> incomeCategory = model.getIncomeCategoryList();
//        String incomeCategoryString = incomeCategory.stream().map(e -> e.toString()).collect(Collectors.joining(","));
//        ObservableList<Category> expenseCategory = model.getExpenseCategoryList();
//        String expenseCategoryString = expenseCategory.stream().map(e -> e.toString()).collect(Collectors.joining(","));
//        return new CommandResult(String.format(MESSAGE_SUCCESS, incomeCategoryString, expenseCategoryString));
//    }
//}
