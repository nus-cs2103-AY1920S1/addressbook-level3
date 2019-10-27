package seedu.jarvis.storage.cca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;

/**
 * Tests the behaviour of {@code JsonAdaptedCcaMilestone}.
 */
public class JsonAdaptedCcaMilestoneTest {

    @Test
    public void toModelType_validName_returnsCcaMilestone() throws Exception {
        String validName = "1";
        CcaMilestone ccaMilestone = new CcaMilestone(validName);
        JsonAdaptedCcaMilestone jsonAdaptedCcaMilestone = new JsonAdaptedCcaMilestone(ccaMilestone);
        assertEquals(ccaMilestone, jsonAdaptedCcaMilestone.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        String invalidName = " ";
        JsonAdaptedCcaMilestone jsonAdaptedCcaMilestone = new JsonAdaptedCcaMilestone(invalidName);
        assertThrows(IllegalValueException.class, CcaMilestone.MESSAGE_CONSTRAINTS,
                jsonAdaptedCcaMilestone::toModelType);
    }

}
