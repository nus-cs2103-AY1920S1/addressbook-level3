package dukecooks.storage.profile;

import static dukecooks.logic.commands.CommandTestUtil.VALID_HISTORY_DENGUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.profile.medical.MedicalHistory;
import dukecooks.testutil.Assert;

public class JsonAdaptedMedicalHistoryTest {
    private static final String INVALID_NAME = "D0ry F!sh";

    private static final String VALID_NAME = VALID_HISTORY_DENGUE;

    @Test
    public void toModelType_validMedicalHistoryDetails_returnsMedicalHistory() throws Exception {
        JsonAdaptedMedicalHistory medicalHistory = new JsonAdaptedMedicalHistory(VALID_HISTORY_DENGUE);
        assertEquals(new MedicalHistory(VALID_HISTORY_DENGUE), medicalHistory.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMedicalHistory medicalHistory =
                new JsonAdaptedMedicalHistory(INVALID_NAME);
        String expectedMessage = MedicalHistory.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, medicalHistory::toModelType);
    }

}
