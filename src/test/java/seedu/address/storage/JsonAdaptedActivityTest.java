package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Title;

public class JsonAdaptedActivityTest {

    private static final Title chalet = new Title("Chalet");
    private static final Activity chaletActivity = new Activity(chalet);

    @Test
    public void toModelType_validActivityDetails_returnsActivity() throws Exception {
        JsonAdaptedActivity activity = new JsonAdaptedActivity(chaletActivity);
        assertEquals(chaletActivity, activity.toModelType());
    }
    // Only implemented 1 test
}
