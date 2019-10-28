package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Color;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalModule;

/**
 * A test class for JsonAdaptedModule.
 */
public class JsonAdaptedModuleTest {

    private static final String INVALID_MODULE_CODE = "CS111";
    private static final String INVALID_COLOR = "invalid color";

    private static final String VALID_MODULE_CODE = TypicalModule.CS1101S.getModuleCode().toString();
    private static final String VALID_COLOR = Color.RED.getColorName();
    private static final List<JsonAdaptedTag> VALID_USER_TAGS = new ArrayList<>();

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        Module typicalModule = TypicalModule.CS3244;
        JsonAdaptedModule adaptedModule = new JsonAdaptedModule(typicalModule);
        Module loadedModule = adaptedModule.toModelType();
        assertTrue(moduleLoadedCorrectly(typicalModule, loadedModule));
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(INVALID_MODULE_CODE, VALID_COLOR, VALID_USER_TAGS);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(null, VALID_COLOR, VALID_USER_TAGS);
        String expectedMessage = String.format(JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT,
                ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidColor_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_MODULE_CODE, INVALID_COLOR, VALID_USER_TAGS);
        String expectedMessage = Color.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullColor_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE, null, VALID_USER_TAGS);
        String expectedMessage = String.format(JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT,
                Color.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    /**
     * Returns true if the module is loaded correct from JSON and a {@code JsonAdaptedModule}.
     */
    public static boolean moduleLoadedCorrectly(Module originalModule, Module loadedModule) {
        boolean result = true;

        // module code
        if (!originalModule.getModuleCode().equals(loadedModule.getModuleCode())) {
            result = false;
        }

        // color
        if (!originalModule.getColor().equals(loadedModule.getColor())) {
            result = false;
        }

        // tags
        List<Tag> originalTags = originalModule.getTags().asUnmodifiableObservableList();
        List<Tag> loadedTags = loadedModule.getTags().asUnmodifiableObservableList();
        for (int i = 0; i < originalTags.size(); i++) {
            Tag originalTag = originalTags.get(i);
            Tag loadedTag = loadedTags.get(i);
            if (!originalTag.equals(loadedTag)) {
                result = false;
            }
        }

        return result;
    }
}
