package seedu.tarence.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.testutil.JsonUtil;


public class JsonAdaptedModuleTest {

    public static final String INVALID_MODULE_CODE = "CS10101010AAA";
    public static final String VALID_MODULE_CODE = "CS1010E";
    public static final LinkedHashMap<String, LinkedHashMap<String, String>> VALID_TUTORIAL_MAP =
                            JsonUtil.getValidMapOfDifferentTutorials();

    @Test
    public void toModelType_tutorialMapWithInvalidDay_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialDay(), "null");
        String expectedMessage = "Json data has missing fields for tutorial map";

        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidDuration_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialDuration(), "null");
        String expectedMessage = "Tutorial's Duration field is invalid! Or Tutorial's LocalTime field is invalid!";
        expectedMessage = "Json data has missing fields for tutorial map";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidStartTime_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialStartTime(), "null");
        String expectedMessage = "Json data has missing fields for tutorial map";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidWeeks_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialWeeks(), "null");
        String expectedMessage = "Error in reading field! Invalid weeks input. Must be 'even', 'odd', or 'every week', "
                + "or numbers separated by commas.";
        expectedMessage = "Json data has missing fields for tutorial map";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidModuleCode(), "null");
        String expectedMessage = "Json data has missing fields for tutorial map" + ModCode.MESSAGE_CONSTRAINTS;
        expectedMessage = "Json data has missing fields for tutorial map";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void getModuleCode_correctModuleCode_returnsTrue() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialWeeks(), "null");
        String moduleCode = module.getModuleCode();
        assertEquals(moduleCode, VALID_MODULE_CODE);
    }

    @Test
    public void getSemesterStartString_validSemesterStartString_returnsTrue() {
        String semesterStartString = String.valueOf("Mon Dec 31 00:00:00 SGT 2001");
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialWeeks(), semesterStartString);
        assertEquals(semesterStartString, module.getSemesterStartString());

    }

}
