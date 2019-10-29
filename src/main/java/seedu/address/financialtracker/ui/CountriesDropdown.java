package seedu.address.financialtracker.ui;

import java.util.Collections;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Expense}.
 */
public class CountriesDropdown extends UiPart<Region> {

    private static final String FXML = "CountriesDropdown.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    ObservableList<String> options = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> countriesDropdown;

    public CountriesDropdown() {
        super(FXML);
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            options.add(obj.getDisplayCountry());
            Collections.sort(options);
        }
        countriesDropdown.setItems(options);
        countriesDropdown.getSelectionModel().select(189); // Select Singapore as default
    }

    public String getDropdownText() {
        return countriesDropdown.getValue();
    }
}
