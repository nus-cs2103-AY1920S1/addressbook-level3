package seedu.address.ui.expense;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.currency.EnterCreateCurrencyCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.expense.EnterCreateExpenseCommand;
import seedu.address.logic.commands.expense.EnterDaysViewCommand;
import seedu.address.logic.commands.expense.EnterListViewCommand;
import seedu.address.logic.parser.expense.ExpenseManagerCommand;
import seedu.address.model.Model;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.MiscExpense;
import seedu.address.model.expense.PlannedExpense;
import seedu.address.model.itinerary.Budget;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;
import seedu.address.ui.template.UiChangeConsumer;

/**
 * {@code Page} for displaying the expense.
 */
public class ExpensesPage extends PageWithSidebar<AnchorPane> implements UiChangeConsumer {

    private static final String FXML = "expense/ExpensesPage.fxml";


    @FXML
    private Label totalBudgetLabel;
    @FXML
    private Label totalExpenseLabel;
    @FXML
    private Label budgetLeftLabel;
    @FXML
    private ToggleButton switchCurrency;
    @FXML
    private ScrollPane expensesContainer;
    @FXML
    private ToggleButton viewOptionButton;

    private String budgetLeft;
    private List<List<ExpenseCard>> expenseLists;
    private List<Expense> expenses;
    private int numberOfDay;
    private String totalExpense;
    private String totalBudget;

    public ExpensesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        expenses = model.getPageStatus().getTrip().getExpenseList().internalUnmodifiableList;
        fillPage();
        fillList();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        generateSummary();
        totalBudgetLabel.setText("Your budget for the trip: " + totalBudget);
        totalExpenseLabel.setText("Your total expenses: " + totalExpense);
        budgetLeftLabel.setText("Your budget left: " + (budgetLeft));
        switchCurrency.setText("Edit Currency");
    }

    @Override
    public void changeUi(String commandWord) throws CommandException {
        ExpenseManagerCommand expenseManagerCommand = ExpenseManagerCommand.valueOf(commandWord);
        switch (expenseManagerCommand) {
        case SHOWDAYS:
            fillDays();
            break;
        case SHOWLIST:
            fillList();
            break;
        case DELETE:
        case SORT:
            fillPage();
            if (!viewOptionButton.isSelected()) {
                fillList();
            } else {
                fillDays();
            }
            break;
        default:
            throw new AssertionError("Unknown command");
        }
    }

    /**
     * Fills up all the placeholders of the days view of expenses.
     */
    public void fillDays() {
        viewOptionButton.setText("Show List");
        getNumberOfDay();
        viewOptionButton.setSelected(true);
        VBox dailyExpensesPanelContainer = new VBox();
        dailyExpensesPanelContainer.getChildren().addAll(generateTitledPanes());
        expensesContainer.setContent(dailyExpensesPanelContainer);
    }

    /**
     * Fills up all the placeholders of the list view of expenses.
     */
    public void fillList() {
        viewOptionButton.setText("Show Days");
        viewOptionButton.setSelected(false);
        VBox expenseCardsContainer = new VBox();
        List<Node> expenseCards = IntStream.range(0, expenses.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    Expense expense = expenses.get(index.getZeroBased());
                    ExpenseCard expenseCard = generateExpenseCard(expense, index);
                    return expenseCard.getRoot();
                }).collect(Collectors.toList());
        expenseCardsContainer.getChildren().addAll(expenseCards);
        expensesContainer.setContent(expenseCardsContainer);
    }

    /**
     * Generates total expense and total budget for the trip
     */
    private void generateSummary() {
        CustomisedCurrency currency = model.getTravelPal().getCurrencies().get(0);
        double expenseSum = expenses.stream().mapToDouble(expense -> {
            return Double.parseDouble(expense.getBudget().toString());
        }).sum();

        double budget = Double.parseDouble(model.getPageStatus().getTrip().getBudget().toString());
        totalExpense = new Budget(expenseSum).getValueStringInCurrency(currency);
        totalBudget = new Budget(budget).getValueStringInCurrency(currency);
        budgetLeft = new Budget(budget - expenseSum).getValueStringInCurrency(currency);
        setBudgetLeftLabelColor(budget - expenseSum);
    };

    /**
     * Sets the color of {@code budgetLeftLabel} according to the amount of budget left.
     */
    private void setBudgetLeftLabelColor(double budgetLeft) {
        if (budgetLeft < 0) {
            budgetLeftLabel.setStyle("-fx-text-fill: red");
        } else {
            budgetLeftLabel.setStyle("-fx-text-fill: #555555");
        }
    };

    /**
     * Divides expenses into lists of expense cards grouped according to day.
     */
    private void divideExpenses() {
        expenseLists = new ArrayList<>();
        for (int i = 0; i <= numberOfDay; i++) {
            expenseLists.add(new ArrayList<>());
        }

        for (int j = 0; j < expenses.size(); j++) {
            Expense expense = expenses.get(j);
            ExpenseCard expenseCard = generateExpenseCard(expense, Index.fromZeroBased(j));
            expenseLists.get(expense.getDayNumber().getValue()).add(expenseCard);
        }
    }

    /**
     * Generates an expense card according to expense type
     *
     * @param expense expense to be displayed in expense card
     * @param index index of expense card
     * @return an expense card with matching type
     */
    private ExpenseCard generateExpenseCard(Expense expense, Index index) {
        ExpenseCard expenseCard;
        if (expense instanceof PlannedExpense) {
            expenseCard = new PlannedExpenseCard(expense, index, model, mainWindow);
        } else if (expense instanceof MiscExpense) {
            expenseCard = new MiscExpenseCard(expense, index, model);
        } else {
            throw new AssertionError("Invalid expense type");
        }
        return expenseCard;
    }

    /**
     * Get the latest day with at least an expense in it.
     */
    private void getNumberOfDay() {
        numberOfDay = model.getPageStatus().getTrip().getExpenseList().getLargestDayNumber();
    }

    /**
     * Generates titled panes containing expenses in each day.
     */
    private List<TitledPane> generateTitledPanes() {
        divideExpenses();
        List<TitledPane> titledPanes = IntStream.range(0, numberOfDay + 1)
                .mapToObj(Index::fromZeroBased)
                .filter(index -> expenseLists.get(index.getZeroBased()).size() != 0)
                .map(index -> {
                    DailyExpensesPanel dailyExpensesPanel = new DailyExpensesPanel(
                            expenseLists.get(index.getZeroBased()), index, model);
                    String header = "Day " + index.getZeroBased();
                    TitledPane titledPane = new TitledPane(header, dailyExpensesPanel.getRoot());
                    return titledPane;
                }).collect(Collectors.toList());
        return titledPanes;
    }

    @FXML
    private void handleEnterCurrency() {
        mainWindow.executeGuiCommand(EnterCreateCurrencyCommand.COMMAND_WORD);
    }

    /**
     * Toggles the list view according to the current view of the page.
     */
    @FXML
    private void handleToggle() {
        if (!viewOptionButton.isSelected()) {
            mainWindow.executeGuiCommand(EnterListViewCommand.COMMAND_WORD);
        } else {
            mainWindow.executeGuiCommand(EnterDaysViewCommand.COMMAND_WORD);
        }
    }

    @FXML
    private void handleAddExpense() {
        mainWindow.executeGuiCommand(EnterCreateExpenseCommand.COMMAND_WORD);
    }
}
