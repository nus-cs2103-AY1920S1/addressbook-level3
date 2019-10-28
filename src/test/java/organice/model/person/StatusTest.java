package organice.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class StatusTest {

    private static final Status STATUS_PROCESSING = new Status(Status.STATUS_PROCESSING);
    private static final Status STATUS_NOT_PROCESSING = new Status(Status.STATUS_NOT_PROCESSING);
    private static final Status STATUS_DONE = new Status(Status.STATUS_DONE);

    private static final Status STATUS_PROCESSING_CAP = new Status(Status.STATUS_PROCESSING.toUpperCase());
    private static final Status STATUS_NOT_PROCESSING_CAP = new Status(Status.STATUS_NOT_PROCESSING.toUpperCase());
    private static final Status STATUS_DONE_CAP = new Status(Status.STATUS_DONE.toUpperCase());

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidStatus = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("abc")); // wrong Status
        assertFalse(Status.isValidStatus("1")); // numeric
        assertFalse(Status.isValidStatus("not+processing")); // + within alphabets
        assertFalse(Status.isValidStatus("pro cess ing")); // spaces within alphabets
        assertFalse(Status.isValidStatus("procesing")); // typo


        // valid status
        assertTrue(Status.isValidStatus("processing")); //valid case
        assertTrue(Status.isValidStatus("not processing")); //valid case
        assertTrue(Status.isValidStatus("done")); //valid case
        assertTrue(Status.isValidStatus("PROCESSING")); //case insensitive
        assertTrue(Status.isValidStatus("NOT PROCESSING")); //case insensitive
        assertTrue(Status.isValidStatus("DONE")); //case insensitive

    }

    @Test
    void isProcessing() {
        assertTrue(STATUS_PROCESSING.isProcessing()); //valid case
        assertTrue(STATUS_PROCESSING_CAP.isProcessing()); //case insensitive

        assertFalse(STATUS_NOT_PROCESSING.isProcessing()); //not valid case
        assertFalse(STATUS_NOT_PROCESSING_CAP.isProcessing()); //case insensitive

        assertFalse(STATUS_DONE.isProcessing()); //not valid case
        assertFalse(STATUS_DONE_CAP.isProcessing()); //case insensitive
    }

    @Test
    void isNotProcessing() {
        assertTrue(STATUS_NOT_PROCESSING.isNotProcessing()); //valid case
        assertTrue(STATUS_NOT_PROCESSING_CAP.isNotProcessing()); //case insensitive

        assertFalse(STATUS_PROCESSING.isNotProcessing()); //not valid case
        assertFalse(STATUS_PROCESSING_CAP.isNotProcessing()); //case insensitive

        assertFalse(STATUS_DONE.isNotProcessing()); //not valid case
        assertFalse(STATUS_DONE_CAP.isNotProcessing()); //case insensitive
    }

    @Test
    void isDone() {
        assertTrue(STATUS_DONE.isDone()); //valid case
        assertTrue(STATUS_DONE_CAP.isDone()); //case insensitive

        assertFalse(STATUS_PROCESSING.isDone()); //not valid case
        assertFalse(STATUS_PROCESSING_CAP.isDone()); //case insensitive

        assertFalse(STATUS_NOT_PROCESSING.isProcessing()); //not valid case
        assertFalse(STATUS_NOT_PROCESSING_CAP.isProcessing()); //case insensitive
    }

    @Test
    void testToString() {
        assertEquals(STATUS_PROCESSING.toString(), "processing");
        assertEquals(STATUS_PROCESSING_CAP.toString(), "processing"); //converted to lowercase

        assertEquals(STATUS_NOT_PROCESSING.toString(), "not processing");
        assertEquals(STATUS_NOT_PROCESSING_CAP.toString(), "not processing"); //converted to lowercase

        assertEquals(STATUS_DONE.toString(), "done");
        assertEquals(STATUS_DONE_CAP.toString(), "done"); //converted to lowercase
    }

    @Test
    void testEquals() {
        assertTrue(STATUS_PROCESSING.equals(STATUS_PROCESSING)); //same object
        assertTrue(STATUS_NOT_PROCESSING.equals(STATUS_NOT_PROCESSING)); //same object
        assertTrue(STATUS_DONE.equals(STATUS_DONE)); //same object

        assertTrue(STATUS_PROCESSING.equals(STATUS_PROCESSING_CAP)); //case insensitive
        assertTrue(STATUS_NOT_PROCESSING.equals(STATUS_NOT_PROCESSING_CAP)); //case insensitive
        assertTrue(STATUS_DONE.equals(STATUS_DONE_CAP)); //case insensitive

        assertFalse(STATUS_PROCESSING.equals(null)); //null object
        assertFalse(STATUS_NOT_PROCESSING.equals(null)); //null object
        assertFalse(STATUS_DONE.equals(null)); //null object

        assertFalse(STATUS_PROCESSING.equals("processing")); //subject object
        assertFalse(STATUS_NOT_PROCESSING.equals("not processing")); //subject object
        assertFalse(STATUS_DONE.equals("done")); //subject object
    }

    @Test
    void testHashCode() {
        assertEquals(STATUS_PROCESSING.hashCode(), STATUS_PROCESSING.hashCode());
        assertEquals(STATUS_PROCESSING.hashCode(), STATUS_PROCESSING_CAP.hashCode());

        assertEquals(STATUS_NOT_PROCESSING.hashCode(), STATUS_NOT_PROCESSING.hashCode());
        assertEquals(STATUS_NOT_PROCESSING.hashCode(), STATUS_NOT_PROCESSING_CAP.hashCode());

        assertEquals(STATUS_DONE.hashCode(), STATUS_DONE.hashCode());
        assertEquals(STATUS_DONE.hashCode(), STATUS_DONE_CAP.hashCode());

        assertNotEquals(STATUS_PROCESSING.hashCode(), STATUS_DONE.hashCode());
        assertNotEquals(STATUS_NOT_PROCESSING.hashCode(), STATUS_DONE.hashCode());
    }
}
