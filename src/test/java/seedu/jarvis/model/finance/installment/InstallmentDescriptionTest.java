package seedu.jarvis.model.finance.installment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.financetracker.installment.InstallmentDescription;

public class InstallmentDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InstallmentDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new InstallmentDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        //null description
        assertThrows(NullPointerException.class, () -> InstallmentDescription.isValidDescription(null));

        // invalid name
        assertFalse(InstallmentDescription.isValidDescription("")); // empty string
        assertFalse(InstallmentDescription.isValidDescription(" ")); // spaces only
        assertFalse(InstallmentDescription.isValidDescription("^")); // only non-alphanumeric characters

        // valid name
        assertTrue(InstallmentDescription.isValidDescription("phone bill")); // alphabets only
        assertTrue(InstallmentDescription.isValidDescription("club penguin 2019")); // alphanumeric characters
    }

    @Test
    public void isEquals() {
        InstallmentDescription description1 = new InstallmentDescription("spotify");
        InstallmentDescription description2 = new InstallmentDescription("spotify");
        assertTrue(description1.equals(description2));
    }
}
