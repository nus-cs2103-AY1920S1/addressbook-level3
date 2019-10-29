package seedu.address.ui.expenditure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.commands.sidebar.EnterExpenseManagerCommand;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;


/**
 * {@code Page} for displaying the expenditure details.
 */
public class DailyExpenditurePage extends ExpensesPage {

    private List<List<ExpenditureCard>> expenditureLists;

    private int numberOfDay;


    @FXML
    private ScrollPane expendituresContainer;

    @FXML
    private ToggleButton viewOptionButton;

    public DailyExpenditurePage(MainWindow mainWindow, Logic logic, Model model) {
        super(mainWindow, logic, model);
        expenses = model.getPageStatus().getTrip().getExpenditureList().internalUnmodifiableList;
        viewOptionButton.setSelected(true);
        getNumberOfDay();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        VBox dailyExpendituresPanelContainer = new VBox();
        dailyExpendituresPanelContainer.getChildren().addAll(generateTitledPanes());
        expendituresContainer.setContent(dailyExpendituresPanelContainer);
    }

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
