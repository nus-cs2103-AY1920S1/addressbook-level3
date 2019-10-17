package seedu.address.ui.expenditure;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.NavigationSidebarLeft;
import seedu.address.ui.components.NavigationSidebarRight;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} for displaying the expenditure details.
 */
public class ExpensesPage extends PageWithSidebar<AnchorPane> {

    private static final String FXML = "expenses/ExpensesPage.fxml";

    @FXML
    private VBox expenditureCardContainer;

    @FXML
    private Label totalBudgetLabel;

    @FXML
    private Label totalExpenditureLabel;

    @FXML
    private Label budgetLeftLabel;

    @FXML
    private VBox sideBarLeft;

    @FXML
    private VBox sideBarRight;


    public ExpensesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        // nav bar
        sideBarRight.getChildren().clear();
        sideBarLeft.getChildren().clear();
        NavigationSidebarRight navigationSidebarRight = new NavigationSidebarRight(mainWindow);
        NavigationSidebarLeft navigationSidebarLeft = new NavigationSidebarLeft(mainWindow);
        sideBarLeft.getChildren().add(navigationSidebarLeft.getRoot());
        sideBarRight.getChildren().add(navigationSidebarRight.getRoot());

        // Filling expenditures
        expenditureCardContainer.getChildren().clear();
        List<Expenditure> expenses = model.getPageStatus().getTrip().getExpenditureList().internalUnmodifiableList;

        double totalExpenditure = expenses.stream().mapToDouble(expense -> {
            return Double.parseDouble(expense.getBudget().toString());
        }).sum();

        double totalBudget = Double.parseDouble(model.getPageStatus().getTrip().getBudget().toString());

        List<Node> expenditureCards = IntStream.range(0, expenses.size())
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    ExpenditureCard expenditureCard = new ExpenditureCard(expenses.get(index.getZeroBased()), index);
                    return expenditureCard.getRoot();
                }).collect(Collectors.toList());
        expenditureCardContainer.getChildren().addAll(FXCollections.observableArrayList(expenditureCards));
        totalBudgetLabel.setText("Your budget for the trip: $" + totalBudget);
        totalExpenditureLabel.setText("Your total expenses: $" + totalExpenditure);
        budgetLeftLabel.setText("Your budget left: $" + (totalBudget - totalExpenditure));
    }

    @FXML
    private void handleAddExpenditure() {
        mainWindow.executeGuiCommand(EnterCreateExpenditureCommand.COMMAND_WORD);
    }

    @FXML
    private void handlePreferences() {
        mainWindow.executeGuiCommand(EnterPrefsCommand.COMMAND_WORD);
    }

}
