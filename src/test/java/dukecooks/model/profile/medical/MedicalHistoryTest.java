package dukecooks.model.profile.medical;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;

public class MedicalHistoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MedicalHistory(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MedicalHistory(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> MedicalHistory.isValidMedicalHistoryName(null));
    }

}
