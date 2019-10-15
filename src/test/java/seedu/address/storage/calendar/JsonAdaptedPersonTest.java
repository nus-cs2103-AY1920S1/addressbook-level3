//package seedu.address.storage.calendar;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
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
//import seedu.address.model.task.TaskPlace;
//import seedu.address.model.task.TaskDescription;
//import seedu.address.model.task.TaskTitle;
//import seedu.address.model.task.TaskTime;
//
//public class JsonAdaptedPersonTest {
//    private static final String INVALID_NAME = "R@chel";
//    private static final String INVALID_PHONE = "+651234";
//    private static final String INVALID_ADDRESS = " ";
//    private static final String INVALID_EMAIL = "example.com";
//    private static final String INVALID_TAG = "#friend";
//
//    private static final String VALID_NAME = BENSON.getTaskTitle().toString();
//    private static final String VALID_PHONE = BENSON.getTaskTime().toString();
//    private static final String VALID_EMAIL = BENSON.getTaskDescription().toString();
//    private static final String VALID_ADDRESS = BENSON.getTaskPlace().toString();
//    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
//            .map(JsonAdaptedTag::new)
//            .collect(Collectors.toList());
//
//    @Test
//    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
//        JsonAdaptedPerson task = new JsonAdaptedPerson(BENSON);
//        assertEquals(BENSON, task.toModelType());
//    }
//
//    @Test
//    public void toModelType_invalidName_throwsIllegalValueException() {
//        JsonAdaptedPerson task =
//                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskTitle.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullName_throwsIllegalValueException() {
//        JsonAdaptedPerson task = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTitle.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidPhone_throwsIllegalValueException() {
//        JsonAdaptedPerson task =
//                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskTime.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullPhone_throwsIllegalValueException() {
//        JsonAdaptedPerson task = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskTime.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidEmail_throwsIllegalValueException() {
//        JsonAdaptedPerson task =
//                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskDescription.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullEmail_throwsIllegalValueException() {
//        JsonAdaptedPerson task = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskDescription.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidAddress_throwsIllegalValueException() {
//        JsonAdaptedPerson task =
//                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = TaskPlace.MESSAGE_CONSTRAINTS;
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullAddress_throwsIllegalValueException() {
//        JsonAdaptedPerson task = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskPlace.class.getSimpleName());
//        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidTags_throwsIllegalValueException() {
//        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
//        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
//        JsonAdaptedPerson task =
//                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
//        assertThrows(IllegalValueException.class, task::toModelType);
//    }
//
//}
