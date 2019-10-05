package seedu.address.ui.itinerary;

import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

public class EditDayPage extends PageWithSidebar {

    private static final String FXML = "EditDayPage.fxml";

    public EditDayPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
    }
}
