//package seedu.address.calendarModel.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.task.TaskDay;
//
//
//
//public class PhoneTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskDay(null));
//    }
//
//    @Test
//    public void constructor_invalidPhone_throwsIllegalArgumentException() {
//        String invalidPhone = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskDay(invalidPhone));
//    }
//
//    @Test
//    public void isValidPhone() {
//        // null phone number
//        assertThrows(NullPointerException.class, () -> TaskDay.isValidPhone(null));
//
//        // invalid phone numbers
//        assertFalse(TaskDay.isValidPhone("")); // empty string
//        assertFalse(TaskDay.isValidPhone(" ")); // spaces only
//        assertFalse(TaskDay.isValidPhone("91")); // less than 3 numbers
//        assertFalse(TaskDay.isValidPhone("phone")); // non-numeric
//        assertFalse(TaskDay.isValidPhone("9011p041")); // alphabets within digits
//        assertFalse(TaskDay.isValidPhone("9312 1534")); // spaces within digits
//
//        // valid phone numbers
//        assertTrue(TaskDay.isValidPhone("911")); // exactly 3 numbers
//        assertTrue(TaskDay.isValidPhone("93121534"));
//        assertTrue(TaskDay.isValidPhone("124293842033123")); // long phone numbers
//    }
//}
