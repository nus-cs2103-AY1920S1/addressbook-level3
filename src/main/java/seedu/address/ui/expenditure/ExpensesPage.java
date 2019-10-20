package seedu.address.ui.expenditure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
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

    private List<List<Expenditure>> expenditureLists;

    private List<Expenditure> expenses;

    private double totalExpenditure;

    private double totalBudget;

    private int numberOfDay;


    @FXML
    private Accordion dailyExpendituresPanelContainer;

    @FXML
    private Label totalBudgetLabel;

    @FXML
    private Label totalExpenditureLabel;

    @FXML
    private Label budgetLeftLabel;


    public ExpensesPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        expenses  = model.getPageStatus().getTrip().getExpenditureList().internalUnmodifiableList;
        getNumberOfDay();
        generateSummary();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        dailyExpendituresPanelContainer.getPanes().clear();
        dailyExpendituresPanelContainer.getPanes().addAll(generateTitledPanes());
        totalBudgetLabel.setText("Your budget for the trip: $" + totalBudget);
        totalExpenditureLabel.setText("Your total expenses: $" + totalExpenditure);
        budgetLeftLabel.setText("Your budget left: $" + (totalBudget - totalExpenditure));
    }

    /**
     * Generates total expenditure and total budget for the trip
     */
    private void generateSummary(){
        totalExpenditure = expenses.stream().mapToDouble(expense -> {
            return Double.parseDouble(expense.getBudget().toString());
        }).sum();

        totalBudget = Double.parseDouble(model.getPageStatus().getTrip().getBudget().toString());
    };


    private void divideExpenditures() {
        expenditureLists = new ArrayList<>();
        for(int i = 0; i < numberOfDay; i++){
            expenditureLists.add(new ArrayList<>());
        }

        for(int j = 0; j < expenses.size(); j++) {
            Expenditure expenditure = expenses.get(j);
            if(expenditure.getDayNumber().isEmpty()){
                expenditureLists.get(0).add(expenditure);
            } else {
                expenditureLists.get(Integer.parseInt(expenditure.getDayNumber().get().value)).add(expenditure);
            }
        }
    }

    private void getNumberOfDay() {
        if(expenses.get(expenses.size() - 1).getDayNumber().isPresent()) {
            numberOfDay = Integer.parseInt(expenses.get(expenses.size() - 1).getDayNumber().get().value) + 1;
        } else {numberOfDay = 1;}
    }

    private List<TitledPane> generateTitledPanes() {
        divideExpenditures();
        List<TitledPane> titledPanes = IntStream.range(0, numberOfDay)
                .mapToObj(Index::fromZeroBased)
                .map(index -> {
                    DailyExpendituresPanel dailyExpendituresPanel = new DailyExpendituresPanel(
                            expenditureLists.get(index.getZeroBased()), index, model);
                    String header;
                    if(index.getZeroBased() == 0) {
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
    private void handleAddExpenditure() {
        mainWindow.executeGuiCommand(EnterCreateExpenditureCommand.COMMAND_WORD);
    }

    @FXML
    private void handlePreferences() {
        mainWindow.executeGuiCommand(EnterPrefsCommand.COMMAND_WORD);
    }

}
