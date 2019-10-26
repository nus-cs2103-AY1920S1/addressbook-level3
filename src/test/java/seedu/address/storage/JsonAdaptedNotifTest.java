package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.notif.Notif;
import seedu.address.testutil.NotifBuilder;

//@@author arjavibahety
public class JsonAdaptedNotifTest {
    private static final String VALID_BODY_ID = "1";
    private static final String INVALID_BODY_ID = "B001";
    private static final String VALID_DATE_TIME = "1500000";
    private static final String INVALID_DATE_TIME_ALPHA = "a";
    private static final String INVALID_DATE_TIME_NEGATIVE = "-5";

    @Test
    public void toModelType_validNotifDetails_returnsNotif() throws Exception {
        Notif notif = new NotifBuilder().build();
        JsonAdaptedNotif jsonNotif = new JsonAdaptedNotif(notif);
        Pair<Integer, Long> notifPair = new Pair(notif.getBody().getIdNum().getIdNum(),
                notif.getNotifCreationTime().getTime());
        assertEquals(notifPair, jsonNotif.toModelType());
    }

    @Test
    public void toModelType_invalidBodyId_throwsIllegalValueException() {
        JsonAdaptedNotif notif = new JsonAdaptedNotif(INVALID_BODY_ID, VALID_DATE_TIME);
        String expectedMessage = JsonAdaptedNotif.INVALID_BODY_ID_NUMBER;
        assertThrows(IllegalValueException.class, expectedMessage, notif::toModelType);
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedNotif notif = new JsonAdaptedNotif(VALID_BODY_ID, INVALID_DATE_TIME_ALPHA);
        String expectedMessage = JsonAdaptedNotif.INVALID_DATE_TIME;
        assertThrows(IllegalValueException.class, expectedMessage, notif::toModelType);

        notif = new JsonAdaptedNotif(VALID_BODY_ID, INVALID_DATE_TIME_NEGATIVE);
        expectedMessage = JsonAdaptedNotif.INVALID_DATE_TIME;
        assertThrows(IllegalValueException.class, expectedMessage, notif::toModelType);
    }
}
//@@author
