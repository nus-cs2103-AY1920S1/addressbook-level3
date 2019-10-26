package dukecooks.model.dashboard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.testutil.Assert;

public class DashboardNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DashboardName(null));
    }

    @Test
    public void constructor_invalidDashboardName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DashboardName(invalidName));
    }

    @Test
    public void isValidDashboardName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> DashboardName.isValidName(null));

        // invalid name
        assertFalse(DashboardName.isValidName("")); // empty string
        assertFalse(DashboardName.isValidName(" ")); // spaces only
        assertFalse(DashboardName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(DashboardName.isValidName("Bake a cake*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(DashboardName.isValidName("Bake a cake")); // alphabets only
        assertTrue(DashboardName.isValidName("12345")); // numbers only
        assertTrue(DashboardName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(DashboardName.isValidName("Capital Tan")); // with capital letters
        assertTrue(DashboardName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
