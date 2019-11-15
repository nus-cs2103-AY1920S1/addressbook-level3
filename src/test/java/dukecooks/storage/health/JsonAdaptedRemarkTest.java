package dukecooks.storage.health;

import static dukecooks.logic.commands.CommandTestUtil.VALID_REMARK_GLUCOSE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.health.components.Remark;
import dukecooks.testutil.Assert;

public class JsonAdaptedRemarkTest {
    private static final String INVALID_REMARK = "G!ucose";

    @Test
    public void toModelType_validRemarkDetails_returnsRemark() throws Exception {
        JsonAdaptedRemark remark = new JsonAdaptedRemark(VALID_REMARK_GLUCOSE);
        assertEquals(new Remark(VALID_REMARK_GLUCOSE), remark.toModelType());
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedRemark remark =
                new JsonAdaptedRemark(INVALID_REMARK);
        String expectedMessage = Remark.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, remark::toModelType);
    }

}
