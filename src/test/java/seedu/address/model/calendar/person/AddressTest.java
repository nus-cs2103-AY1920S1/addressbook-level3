//package seedu.address.calendarModel.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.task.TaskPlace;
//
//
//
//public class AddressTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskPlace(null));
//    }
//
//    @Test
//    public void constructor_invalidAddress_throwsIllegalArgumentException() {
//        String invalidAddress = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskPlace(invalidAddress));
//    }
//
//    @Test
//    public void isValidAddress() {
//        // null address
//        assertThrows(NullPointerException.class, () -> TaskPlace.isValidAddress(null));
//
//        // invalid addresses
//        assertFalse(TaskPlace.isValidAddress("")); // empty string
//        assertFalse(TaskPlace.isValidAddress(" ")); // spaces only
//
//        // valid addresses
//        assertTrue(TaskPlace.isValidAddress("Blk 456, Den Road, #01-355"));
//        assertTrue(TaskPlace.isValidAddress("-")); // one character
//        assertTrue(TaskPlace.isValidAddress(
//                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
//    }
//}
