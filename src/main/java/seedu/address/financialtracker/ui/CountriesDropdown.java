package seedu.address.financialtracker.ui;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
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
 * An UI component that displays a dropdown menu which contains list of countries.
 */
public class CountriesDropdown extends UiPart<Region> {

    private static final String FXML = "CountriesDropdown.fxml";
    private static final ObservableList<String> options = FXCollections.observableArrayList();
    private static final HashSet<String> checker = new HashSet<>();
    static {
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            options.add(obj.getDisplayCountry());
            checker.add(obj.getDisplayCountry());
        }
        Collections.sort(options);
    }
    private static String field = "Singapore";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final FinancialTrackerLogic financialTrackerLogic;
    private final ExpensePanel expensePanel;

    @FXML
    private ComboBox<String> countriesDropdown;

    public CountriesDropdown(FinancialTrackerLogic financialTrackerLogic, ExpensePanel expensePanel) {
        super(FXML);
        this.financialTrackerLogic = financialTrackerLogic;
        financialTrackerLogic.addDependencies(this);
        this.expensePanel = expensePanel;
        // set the combo box
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
    }

    /**
     * Handles update from user input.
     * @param country from user input
     */
    public void handleUpdateFromUserInput(String country) {
        requireNonNull(country);
        assert options.indexOf(country) != -1 : "User input country can't be invalid! check your parser?";
        countriesDropdown.getSelectionModel().select(options.indexOf(country));
        field = country;
        financialTrackerLogic.setCountry(field);
        expensePanel.update();
        logger.info("Financial_Tracker Selection Menu switched to: " + countriesDropdown.getValue());
    }

    /**
     * Checks whether user input's country is the same as in this dropdown menu.
     * Main purpose is to avoid errors as expense list uses specific country string as key.
     */
    public static boolean isValidDropdownCountry(String country) {
        return checker.contains(country);
    }

}
