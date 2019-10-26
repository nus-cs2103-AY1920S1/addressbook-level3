package seedu.address.itinerary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

import java.net.URL;

public class ItineraryDatePicker extends UiPart<Region> {

    private static final String FXML_WINDOW = "ItineraryDatePicker.fxml";

    @FXML
    private DatePicker datePicker;

    public ItineraryDatePicker(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }
}
