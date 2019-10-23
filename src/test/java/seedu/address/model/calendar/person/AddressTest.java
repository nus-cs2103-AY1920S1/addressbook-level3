//package seedu.address.calendarModel.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.task.TaskTime;
//
//
//
//public class AddressTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskTime(null));
//    }
//
//    @Test
//    public void constructor_invalidAddress_throwsIllegalArgumentException() {
//        String invalidAddress = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskTime(invalidAddress));
//    }
//
//    @Test
//    public void isValidAddress() {
//        // null address
//        assertThrows(NullPointerException.class, () -> TaskTime.isValidAddress(null));
//
//        // invalid addresses
//        assertFalse(TaskTime.isValidAddress("")); // empty string
//        assertFalse(TaskTime.isValidAddress(" ")); // spaces only
//
//        // valid addresses
//        assertTrue(TaskTime.isValidAddress("Blk 456, Den Road, #01-355"));
//        assertTrue(TaskTime.isValidAddress("-")); // one character
//        assertTrue(TaskTime.isValidAddress(
//                "Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
//    }
//}
