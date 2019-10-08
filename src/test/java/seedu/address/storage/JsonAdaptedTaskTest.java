package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTasks.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
=======
import seedu.address.model.task.Email;
import seedu.address.model.task.Name;
import seedu.address.model.task.Phone;
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java

public class JsonAdaptedTaskTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(BENSON);
        assertEquals(BENSON, task.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
=======
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
=======
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_PHONE, VALID_EMAIL, VALID_TAGS);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_TAGS);
=======
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_TAGS);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_TAGS);
=======
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, null, VALID_EMAIL, VALID_TAGS);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_TAGS);
=======
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_TAGS);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
=======
        JsonAdaptedTask task = new JsonAdaptedTask(VALID_NAME, VALID_PHONE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
<<<<<<< HEAD:src/test/java/seedu/address/storage/JsonAdaptedPersonTest.java
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
=======
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_NAME, VALID_PHONE, VALID_EMAIL, invalidTags);
        assertThrows(IllegalValueException.class, task::toModelType);
>>>>>>> team/master:src/test/java/seedu/address/storage/JsonAdaptedTaskTest.java
    }

}
