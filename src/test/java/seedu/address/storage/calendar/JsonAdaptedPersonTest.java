//package seedu.address.storage.calendar;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
//import static seedu.address.testutil.Assert.assertThrows;
//import static seedu.address.testutil.TypicalPersons.BENSON;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.calendarModel.task.TaskTime;
//import seedu.address.calendarModel.task.TaskDescription;
//import seedu.address.calendarModel.task.TaskTitle;
//import seedu.address.calendarModel.task.TaskDay;
//
//public class JsonAdaptedPersonTest {
//    private static final String INVALID_NAME = "R@chel";
//    private static final String INVALID_PHONE = "+651234";
//    private static final String INVALID_ADDRESS = " ";
//    private static final String INVALID_EMAIL = "example.com";
//    private static final String INVALID_TAG = "#friend";
//
//    private static final String VALID_NAME = BENSON.getTaskTitle().toString();
//    private static final String VALID_PHONE = BENSON.getTaskDay().toString();
//    private static final String VALID_EMAIL = BENSON.getTaskDescription().toString();
//    private static final String VALID_ADDRESS = BENSON.getTaskTime().toString();
//    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTaskTags().stream()
//            .map(JsonAdaptedTag::new)
//            .collect(Collectors.toList());
//
//    @Test
//    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
//        JsonAdaptedTask task = new JsonAdaptedTask(BENSON);
//        assertEquals(BENSON, task.toModelType());
//    }
//
//    @Test
//    public void toModelType_invalidName_throwsIllegalValueException() {
//        JsonAdaptedTask task =
//                new JsonAdaptedTask(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskTitle.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullName_throwsIllegalValueException() {
//        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTitle.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidPhone_throwsIllegalValueException() {
//        JsonAdaptedTask task =
//                new JsonAdaptedTask(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskDay.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullPhone_throwsIllegalValueException() {
//        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDay.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidEmail_throwsIllegalValueException() {
//        JsonAdaptedTask task =
//                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskDescription.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullEmail_throwsIllegalValueException() {
//        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDescription.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidAddress_throwsIllegalValueException() {
//        JsonAdaptedTask task =
//                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskTime.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullAddress_throwsIllegalValueException() {
//        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTime.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidTags_throwsIllegalValueException() {
//        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
//        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
//        JsonAdaptedTask task =
//                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
//        assertThrows(IllegalValueException.class, task::toModelType);
//    }
//
//}
