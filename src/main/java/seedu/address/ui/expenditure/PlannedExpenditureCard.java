package seedu.address.ui.expenditure;

import javafx.fxml.FXML;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expenditure.EnterDayOfExpenditureCommand;
import seedu.address.model.Model;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.ui.MainWindow;

/**
 * TODO: Implement display for inventory and booking labels.
 */
public class PlannedExpenditureCard extends ExpenditureCard {
    private static final String FXML = "expenses/ExpenditureCard.fxml";

    private Index displayedIndex;

    private MainWindow mainWindow;

    public PlannedExpenditureCard(Expenditure expenditure, Index displayedIndex, Model model, MainWindow mainWindow) {
        super(FXML, expenditure, displayedIndex, model);
        this.mainWindow = mainWindow;
        this.displayedIndex = displayedIndex;

    }

    @FXML
    private void handleEnterEventPage() {
        mainWindow.executeGuiCommand(EnterDayOfExpenditureCommand.COMMAND_WORD
                + " " + displayedIndex.getOneBased());
    }
}
