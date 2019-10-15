//package seedu.address.model.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.task.TaskTime;
//
//
//
//public class PhoneTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskTime(null));
//    }
//
//    @Test
//    public void constructor_invalidPhone_throwsIllegalArgumentException() {
//        String invalidPhone = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskTime(invalidPhone));
//    }
//
//    @Test
//    public void isValidPhone() {
//        // null phone number
//        assertThrows(NullPointerException.class, () -> TaskTime.isValidPhone(null));
//
//        // invalid phone numbers
//        assertFalse(TaskTime.isValidPhone("")); // empty string
//        assertFalse(TaskTime.isValidPhone(" ")); // spaces only
//        assertFalse(TaskTime.isValidPhone("91")); // less than 3 numbers
//        assertFalse(TaskTime.isValidPhone("phone")); // non-numeric
//        assertFalse(TaskTime.isValidPhone("9011p041")); // alphabets within digits
//        assertFalse(TaskTime.isValidPhone("9312 1534")); // spaces within digits
//
//        // valid phone numbers
//        assertTrue(TaskTime.isValidPhone("911")); // exactly 3 numbers
//        assertTrue(TaskTime.isValidPhone("93121534"));
//        assertTrue(TaskTime.isValidPhone("124293842033123")); // long phone numbers
//    }
//}
