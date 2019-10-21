package seedu.tarence.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.util.SampleDataUtil;
import seedu.tarence.storage.JsonAdaptedModule;
import seedu.tarence.testutil.SerializableTestClass;
import seedu.tarence.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    public static final String INVALID_STUDENT_STRING_MISSING_STUDENT_NAME = "[studentEmail=e0035152@u.nus.edu.sg, "
            + "studentMatricNumber=OptionalA0155413M, studentNusnetId=OptionalE0031550, "
            + "studentModuleCode=CS1010S, studentTutorialName=Lab Session}]";
    public static final String INVALID_STUDENT_STRING_WRONG_ORDER_OF_FIELDS = "[studentName=Alice, "
            + "studentEmail=e0035152@u.nus.edu.sg, "
            + "studentMatricNumber=OptionalA0155413M, studentNusnetId=OptionalE0031550, "
            + "studentTutorialName=Lab Session, studentModuleCode=CS1010S}]";
    public static final String INVALID_TUTORIAL_STRING_MISSING_TUTORIAL_NAME = "{tutorialDayOfWeek=MONDAY, "
            + "studentListString=[], tutorialModuleCode=CS1010S, tutorialStartTime=12:00, tutorialDuration=PT2H, "
            + "tutorialWeeks=[1, 4, 7]}";
    public static final String INVALID_TUTORIAL_STRING_WRONG_ORDER_OF_FIELDS = "{tutorialName=Sectional, "
            + "tutorialDayOfWeek=MONDAY, "
            + "studentListString=[], tutorialModuleCode=CS1010S, tutorialStartTime=12:00, tutorialDuration=PT2H, "
            + "tutorialWeeks=[1, 4, 7]}";

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.json");

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void isValidStudentString_studentStringWithoutNameField_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidStudentString(INVALID_STUDENT_STRING_MISSING_STUDENT_NAME));
    }

    @Test
    public void isValidStudentString_studentStringInWrongOrder_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidStudentString(INVALID_STUDENT_STRING_WRONG_ORDER_OF_FIELDS));
    }

    @Test
    public void isValidTutorialString_tutorialStringInWrongOrder_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidTutorialString(INVALID_TUTORIAL_STRING_WRONG_ORDER_OF_FIELDS));
    }

    @Test
    public void isValidTutorialString_tutorialStringWithoutNameField_returnsFalse() {
        JsonAdaptedModule module = new JsonAdaptedModule(SampleDataUtil.getSampleModule());
        assertEquals(false, seedu.tarence.commons.util.JsonUtil
                .isValidTutorialString(INVALID_TUTORIAL_STRING_MISSING_TUTORIAL_NAME));
    }

    //TODO: @Test jsonUtil_readJsonStringToObjectInstance_correctObject()

    //TODO: @Test jsonUtil_writeThenReadObjectToJson_correctObject()
}
