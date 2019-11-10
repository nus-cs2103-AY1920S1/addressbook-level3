package seedu.address.ui.expenditure;

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
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.commands.expenditure.EnterDaysViewCommand;
import seedu.address.logic.commands.expenditure.EnterListViewCommand;
import seedu.address.logic.parser.expense.ExpenseManagerCommand;
import seedu.address.model.Model;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Budget;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;
import seedu.address.ui.template.UiChangeConsumer;

/**
 * {@code Page} for displaying the expenditure details.
 */

public class ExpensesPage extends PageWithSidebar<AnchorPane> implements UiChangeConsumer {

    private static final String FXML = "expenses/ExpensesPage.fxml";


    @FXML
    private Label totalBudgetLabel;
    @FXML
    private Label totalExpenditureLabel;
    @FXML
    private Label budgetLeftLabel;
    @FXML
    private ToggleButton switchCurrency;
    @FXML
    private ScrollPane expendituresContainer;
    @FXML
    private ToggleButton viewOptionButton;

    private String budgetLeft;
    private List<List<ExpenditureCard>> expenditureLists;
    private List<Expenditure> expenses;
    private int numberOfDay;
    private String totalExpenditure;
    private String totalBudget;

    public ExpensesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        expenses = model.getPageStatus().getTrip().getExpenditureList().internalUnmodifiableList;
        fillPage();
        fillList();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        generateSummary();
        totalBudgetLabel.setText("Your budget for the trip: " + totalBudget);
        totalExpenditureLabel.setText("Your total expenses: " + totalExpenditure);
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
        VBox dailyExpendituresPanelContainer = new VBox();
        dailyExpendituresPanelContainer.getChildren().addAll(generateTitledPanes());
        expendituresContainer.setContent(dailyExpendituresPanelContainer);
    }

    /**
     * Fills up all the placeholders of the list view of expenses.
     */
    public void fillList() {
        viewOptionButton.setText("Show Days");
        viewOptionButton.setSelected(false);
        VBox expenditureCardsContainer = new VBox();
        List<Node> expenditureCards = IntStream.range(0, expenses.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    ExpenditureCard expenditureCard = new ExpenditureCard(expenses.get(index.getZeroBased()),
                            index, model);
                    return expenditureCard.getRoot();
                }).collect(Collectors.toList());
        expenditureCardsContainer.getChildren().addAll(expenditureCards);
        expendituresContainer.setContent(expenditureCardsContainer);
    }

    /**
     * Generates total expenditure and total budget for the trip
     */
    private void generateSummary() {
        CustomisedCurrency currency = model.getTravelPal().getCurrencies().get(0);
        double expenditureSum = expenses.stream().mapToDouble(expense -> {
            return Double.parseDouble(expense.getBudget().toString());
        }).sum();

        double budget = Double.parseDouble(model.getPageStatus().getTrip().getBudget().toString());
        totalExpenditure = new Budget(expenditureSum).getValueStringInCurrency(currency);
        totalBudget = new Budget(budget).getValueStringInCurrency(currency);
        budgetLeft = new Budget(budget - expenditureSum).getValueStringInCurrency(currency);
    };

    /**
     * Divides expenditures into lists of expenditure cards grouped according to day.
     */
    private void divideExpenditures() {
        expenditureLists = new ArrayList<>();
        for (int i = 0; i <= numberOfDay; i++) {
            expenditureLists.add(new ArrayList<>());
        }

        for (int j = 0; j < expenses.size(); j++) {
            Expenditure expenditure = expenses.get(j);
            if (expenditure.getDayNumber().isEmpty()) {
                expenditureLists.get(0).add(new ExpenditureCard(expenditure, Index.fromZeroBased(j), model));
            } else {
                expenditureLists.get(Integer.parseInt(expenditure.getDayNumber().get().value))
                        .add(new ExpenditureCard(expenditure, Index.fromZeroBased(j), model));
            }
        }
    }

    /**
     * Get the number of days in the trip.
     */
    private void getNumberOfDay() {
        if (expenses.get(expenses.size() - 1).getDayNumber().isPresent()) {
            numberOfDay = Integer.parseInt(expenses.get(expenses.size() - 1).getDayNumber().get().value);
        } else {
            numberOfDay = 0;
        }
    }

    /**
     * Generates titled panes containing expenditures in each day.
     */
    private List<TitledPane> generateTitledPanes() {
        divideExpenditures();
        List<TitledPane> titledPanes = IntStream.range(0, numberOfDay + 1)
                .mapToObj(Index::fromZeroBased)
                .filter(index -> expenditureLists.get(index.getZeroBased()).size() != 0)
                .map(index -> {
                    DailyExpendituresPanel dailyExpendituresPanel = new DailyExpendituresPanel(
                            expenditureLists.get(index.getZeroBased()), index, model);
                    String header;
                    if (index.getZeroBased() == 0) {
                        header = "Unassigned";
                    } else {
                        header = "Day " + index.getZeroBased();
                    }
                    TitledPane titledPane = new TitledPane(header, dailyExpendituresPanel.getRoot());
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
    private void handleAddExpenditure() {
        mainWindow.executeGuiCommand(EnterCreateExpenditureCommand.COMMAND_WORD);
    }
}
