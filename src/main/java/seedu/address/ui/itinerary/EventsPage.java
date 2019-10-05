package seedu.address.ui.itinerary;

import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.PageWithSidebar;

public class EventsPage extends PageWithSidebar {

    private static final String FXML = "EventsPage.fxml";

    public EventsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
    }
}
