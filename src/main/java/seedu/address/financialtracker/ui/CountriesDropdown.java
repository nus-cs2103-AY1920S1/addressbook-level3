package seedu.address.financialtracker.ui;

import java.util.Collections;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.financialtracker.logic.FinancialTrackerLogic;
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
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ComboBox<String> countriesDropdown;

    private static String field = "Singapore";

    public CountriesDropdown(FinancialTrackerLogic financialTrackerLogic, ExpensePanel expensePanel) {
        super(FXML);
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            options.add(obj.getDisplayCountry());
            Collections.sort(options);
        }
        countriesDropdown.setItems(options);
        countriesDropdown.getSelectionModel().select(189); // Select Singapore as default
        logger.info("Financial_Tracker Selection Menu switched to: " + countriesDropdown.getValue());

        countriesDropdown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                field = countriesDropdown.getValue();
                financialTrackerLogic.setCountry(field);
                expensePanel.update();
                logger.info("Financial_Tracker Selection Menu switched to: " + countriesDropdown.getValue());
            }
        });

        // new AutoCompleteComboBoxListener<ComboBox<String>>(countriesDropdown);
    }

    public static String getDropdownText() {
        return field;
    }
}
