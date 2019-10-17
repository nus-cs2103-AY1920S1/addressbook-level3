//package seedu.address.calendarModel.calendar.tag;
//
//import static seedu.address.testutil.Assert.assertThrows;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.tag.TaskTag;
//
//
//
//public class TagTest {
//
//    @Test
//    public void constructor_null_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> new TaskTag(null));
//    }
//
//    @Test
//    public void constructor_invalidTagName_throwsIllegalArgumentException() {
//        String invalidTagName = "";
//        assertThrows(IllegalArgumentException.class, () -> new TaskTag(invalidTagName));
//    }
//
//    @Test
//    public void isValidTagName() {
//        // null tag name
//        assertThrows(NullPointerException.class, () -> TaskTag.isValidTagName(null));
//    }
//
//}
