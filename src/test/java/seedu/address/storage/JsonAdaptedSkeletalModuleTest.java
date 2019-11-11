package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.testutil.TypicalModule;

/**
 * A test class for JsonAdaptedSkeletalModule.
 */
public class JsonAdaptedSkeletalModuleTest {

    private static final String INVALID_MODULE_CODE = "CS111";

    private static final String VALID_MODULE_CODE = TypicalModule.CS1101S.getModuleCode().toString();

    @Test
    public void toModelType_validModuleDetails_returnsModule() throws Exception {
        Module typicalModule = TypicalModule.CS3244;
        JsonAdaptedSkeletalModule adaptedModule = new JsonAdaptedSkeletalModule(typicalModule);
        Module loadedModule = adaptedModule.toModelType();
        assertEquals(typicalModule.getModuleCode(), loadedModule.getModuleCode());
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedSkeletalModule module =
                new JsonAdaptedSkeletalModule(INVALID_MODULE_CODE);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

}
