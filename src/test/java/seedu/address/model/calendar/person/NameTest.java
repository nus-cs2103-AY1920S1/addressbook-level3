//package seedu.address.model.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.model.task.TaskTitle;
//
//
//
//public class NameTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskTitle(null));
//    }
//
//    @Test
//    public void constructor_invalidName_throwsIllegalArgumentException() {
//        String invalidName = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskTitle(invalidName));
//    }
//
//    @Test
//    public void isValidName() {
//        // null name
//        assertThrows(NullPointerException.class, () -> TaskTitle.isValidName(null));
//
//        // invalid name
//        assertFalse(TaskTitle.isValidName("")); // empty string
//        assertFalse(TaskTitle.isValidName(" ")); // spaces only
//        assertFalse(TaskTitle.isValidName("^")); // only non-alphanumeric characters
//        assertFalse(TaskTitle.isValidName("peter*")); // contains non-alphanumeric characters
//
//        // valid name
//        assertTrue(TaskTitle.isValidName("peter jack")); // alphabets only
//        assertTrue(TaskTitle.isValidName("12345")); // numbers only
//        assertTrue(TaskTitle.isValidName("peter the 2nd")); // alphanumeric characters
//        assertTrue(TaskTitle.isValidName("Capital Tan")); // with capital letters
//        assertTrue(TaskTitle.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
//    }
//}
