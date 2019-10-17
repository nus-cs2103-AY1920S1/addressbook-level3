//package seedu.address.calendarModel.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.task.TaskDescription;
//
//
//
//public class EmailTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskDescription(null));
//    }
//
//    @Test
//    public void constructor_invalidEmail_throwsIllegalArgumentException() {
//        String invalidEmail = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskDescription(invalidEmail));
//    }
//
//    @Test
//    public void isValidEmail() {
//        // null email
//        assertThrows(NullPointerException.class, () -> TaskDescription.isValidEmail(null));
//
//        // blank email
//        assertFalse(TaskDescription.isValidEmail("")); // empty string
//        assertFalse(TaskDescription.isValidEmail(" ")); // spaces only
//
//        // missing parts
//        assertFalse(TaskDescription.isValidEmail("@example.com")); // missing local part
//        assertFalse(TaskDescription.isValidEmail("peterjackexample.com")); // missing '@' symbol
//        assertFalse(TaskDescription.isValidEmail("peterjack@")); // missing domain name
//
//        // invalid parts
//        assertFalse(TaskDescription.isValidEmail("peterjack@-")); // invalid domain name
//        assertFalse(TaskDescription.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
//        assertFalse(TaskDescription.isValidEmail("peter jack@example.com")); // spaces in local part
//        assertFalse(TaskDescription.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
//        assertFalse(TaskDescription.isValidEmail(" peterjack@example.com")); // leading space
//        assertFalse(TaskDescription.isValidEmail("peterjack@example.com ")); // trailing space
//        assertFalse(TaskDescription.isValidEmail("peterjack@@example.com")); // double '@' symbol
//        assertFalse(TaskDescription.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
//        assertFalse(TaskDescription.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
//        assertFalse(TaskDescription.isValidEmail("peterjack@.example.com")); // domain name starts with a period
//        assertFalse(TaskDescription.isValidEmail("peterjack@example.com.")); // domain name ends with a period
//        assertFalse(TaskDescription.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
//        assertFalse(TaskDescription.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen
//
//        // valid email
//        assertTrue(TaskDescription.isValidEmail("PeterJack_1190@example.com"));
//        assertTrue(TaskDescription.isValidEmail("a@bc")); // minimal
//        assertTrue(TaskDescription.isValidEmail("test@localhost")); // alphabets only
//        assertTrue(TaskDescription
//                .isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
//        assertTrue(TaskDescription.isValidEmail("123@145")); // numeric local part and domain name
//        assertTrue(TaskDescription
//                .isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
//        assertTrue(TaskDescription.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
//        assertTrue(TaskDescription.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
//    }
//}
