package seedu.tarence.storage;

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
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialDay());
        String expectedMessage = "Error in reading field! Invalid day entered";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    /*
    TODO: fix test with correcy tutorial map
    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(INVALID_MODULE_CODE, VALID_TUTORIAL_MAP);
        String expectedMessage = "Invalid field in Module";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }
    */


    @Test
    public void toModelType_tutorialMapWithInvalidDuration_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialDuration());
        String expectedMessage = "Tutorial's Duration field is invalid! Or Tutorial's LocalTime field is invalid!";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidStartTime_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialStartTime());
        String expectedMessage = "Tutorial's Duration field is invalid! Or Tutorial's LocalTime field is invalid!";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidWeeks_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidTutorialWeeks());
        String expectedMessage = "Error in reading field! Invalid weeks input. Must be 'even', 'odd', or 'every week', "
                + "or numbers separated by commas.";
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_tutorialMapWithInvalidModuleCode_throwsIllegalValueExcepion() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE,
                JsonUtil.getMapOfSingleTutorialWithInvalidModuleCode());
        String expectedMessage = "Error in reading field! " + ModCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

}
