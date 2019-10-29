package seedu.address.ui.expenditure;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.commands.expenditure.EnterDaysViewCommand;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * {@code Page} for displaying the expenditure details.
 */
public class ExpensesListPage extends ExpensesPage {

    private static final String FXML = "expenses/ExpensesPage.fxml";

    @FXML
    private ScrollPane expendituresContainer;

    @FXML
    private ToggleButton viewOptionButton;

    public ExpensesListPage(MainWindow mainWindow, Logic logic, Model model) {
        super(mainWindow, logic, model);
        viewOptionButton.setSelected(false);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
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

    @FXML
    private void handleToggle() {
        mainWindow.executeGuiCommand(EnterDaysViewCommand.COMMAND_WORD);
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
