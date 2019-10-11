package mams.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mams.commons.exceptions.IllegalValueException;
import mams.model.student.Credits;
import mams.model.student.Email;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.testutil.Assert;
import mams.testutil.TypicalStudents;

public class JsonAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_CREDITS = "24";
    private static final String INVALID_MATRICID = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = TypicalStudents.BENSON.getName().toString();
    private static final String VALID_CREDITS = TypicalStudents.BENSON.getCredits().toString();
    private static final String VALID_EMAIL = TypicalStudents.BENSON.getEmail().toString();
    private static final String VALID_MATRICID = TypicalStudents.BENSON.getMatricId().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TypicalStudents.BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudentDetails_returnsStudent() throws Exception {
        JsonAdaptedStudent student = new JsonAdaptedStudent(TypicalStudents.BENSON);
        Assertions.assertEquals(TypicalStudents.BENSON, student.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(INVALID_NAME, VALID_CREDITS, VALID_EMAIL, VALID_MATRICID, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(null, VALID_CREDITS, VALID_EMAIL,
                VALID_MATRICID, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullCredits_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, null,
                VALID_EMAIL, VALID_MATRICID, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                Credits.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_CREDITS, INVALID_EMAIL, VALID_MATRICID, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_CREDITS, null,
                VALID_MATRICID, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidMatricId_throwsIllegalValueException() {
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_CREDITS, VALID_EMAIL, INVALID_MATRICID, VALID_TAGS);
        String expectedMessage = MatricId.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_nullMatricId_throwsIllegalValueException() {
        JsonAdaptedStudent student = new JsonAdaptedStudent(VALID_NAME, VALID_CREDITS,
                VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(JsonAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT,
                MatricId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, student::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudent student =
                new JsonAdaptedStudent(VALID_NAME, VALID_CREDITS, VALID_EMAIL, VALID_MATRICID, invalidTags);
        Assert.assertThrows(IllegalValueException.class, student::toModelType);
    }

}
