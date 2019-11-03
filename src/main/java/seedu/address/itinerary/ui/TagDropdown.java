package seedu.address.itinerary.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;

import seedu.address.ui.UiPart;

/**
 * The priority combo box to select the priority for each event.
 */
public class TagDropdown extends UiPart<Region> {

    private static final String FXML = "ItineraryTagWindow.fxml";

    private static String field = null;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    private ObservableList<String> options = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> tagWindow;

    public TagDropdown() {
        super(FXML);
        options.add("Priority: Critical");
        options.add("Priority: High");
        options.add("Priority: Medium");
        options.add("Priority: Low");
        options.add("Priority: None");
        tagWindow.setItems(options);
        tagWindow.getSelectionModel().select(4); // Select Singapore as default
    }

    public String updateDropdownText() {
        return field = tagWindow.getValue();
    }

    public static String getDropdownText() {
        return field;
    }
}
