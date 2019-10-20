package seedu.address.ui.expenditure;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.expenditure.EnterCreateExpenditureCommand;
import seedu.address.logic.commands.expenditure.EnterDaysViewCommand;
import seedu.address.logic.commands.sidebar.EnterExpenseManagerCommand;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;
import seedu.address.ui.itinerary.EventCard;
import seedu.address.ui.template.PageWithSidebar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * {@code Page} for displaying the expenditure details.
 */
public class ExpensesListPage extends ExpensesPage {

    private static final String FXML = "expenses/ExpensesPage.fxml";

    @FXML
    private ScrollPane ExpendituresContainer;

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
        VBox ExpenditureCardsContainer = new VBox();
        List<Node> expenditureCards = IntStream.range(0, expenses.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    ExpenditureCard expenditureCard = new ExpenditureCard(expenses.get(index.getZeroBased()), index);
                    return expenditureCard.getRoot();
                }).collect(Collectors.toList());
        ExpenditureCardsContainer.getChildren().addAll(expenditureCards);
        ExpendituresContainer.setContent(ExpenditureCardsContainer);
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
