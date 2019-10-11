package seedu.address.ui.itinerary;

import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;
import seedu.address.ui.template.Page;

public class ItineraryPage extends Page<AnchorPane> {
    private static final String FXML = "itinerary/Itinerary.fxml";

    public ItineraryPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
        fillPage();
    }

    @Override
    public void fillPage() {

    }
}
