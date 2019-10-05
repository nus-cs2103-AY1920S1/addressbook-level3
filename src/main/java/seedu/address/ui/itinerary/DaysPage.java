package seedu.address.ui.itinerary;

import javafx.stage.Stage;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.WindowWithSidebar;

public class DaysPage extends WindowWithSidebar {

    private static final String FXML = "DaysPage.fxml";

    public DaysPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    protected void fillInnerParts() {
    }
}
