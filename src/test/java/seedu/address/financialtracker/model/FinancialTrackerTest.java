package seedu.address.financialtracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

class FinancialTrackerTest {

    @Test
    void setCurrentCountry() {
        FinancialTracker ft = new FinancialTracker();
        assertEquals("Singapore", ft.getCurrentCountry());
        ft.setCurrentCountry("Hello");
        // the set should not be success, and default value "Singapore will be returned".
        assertEquals("Singapore", ft.getCurrentCountry());
    }

    @Test
    void getInternalUnmodifiableExpenseListMap() {
        ObservableMap<String, ExpenseList> expenseListMap1 = FXCollections.observableHashMap();;
        String[] locales = Locale.getISOCountries();
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            expenseListMap1.put(obj.getDisplayCountry(), new ExpenseList(obj.getDisplayCountry()));
        }
        ObservableMap<String, ExpenseList> expenseListMap2 =
                new FinancialTracker().getInternalUnmodifiableExpenseListMap();
        assertEquals(expenseListMap1.get("Singapore").getSummary(), expenseListMap2.get("Singapore").getSummary());
    }
}
