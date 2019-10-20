package seedu.address.ui.expenditure;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.commands.sidebar.EnterExpenseManagerCommand;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * {@code Page} for displaying the expenditure details.
 */
public class DailyExpenditurePage extends ExpensesPage {

    private List<List<ExpenditureCard>> expenditureLists;

    private int numberOfDay;


    @FXML
    private ScrollPane ExpendituresContainer;

    @FXML
    private ToggleButton viewOptionButton;

    public DailyExpenditurePage(MainWindow mainWindow, Logic logic, Model model) {
        super(mainWindow, logic, model);
        expenses = model.getPageStatus().getTrip().getExpenditureList().internalUnmodifiableList;
        viewOptionButton.setSelected(true);
        getNumberOfDay();
        generateSummary();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        VBox dailyExpendituresPanelContainer = new VBox();
        dailyExpendituresPanelContainer.getChildren().addAll(generateTitledPanes());
        ExpendituresContainer.setContent(dailyExpendituresPanelContainer);
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
                expenditureLists.get(0).add(new ExpenditureCard(expenditure, Index.fromZeroBased(j)));
            } else {
                expenditureLists.get(Integer.parseInt(expenditure.getDayNumber().get().value)).
                        add(new ExpenditureCard(expenditure, Index.fromZeroBased(j)));
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
                .filter(index -> expenditureLists.get(index.getZeroBased()).size() != 0)
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
    private void handleToggle() {
        mainWindow.executeGuiCommand(EnterExpenseManagerCommand.COMMAND_WORD);
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
