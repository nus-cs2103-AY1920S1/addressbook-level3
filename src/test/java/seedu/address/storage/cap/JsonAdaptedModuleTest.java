package seedu.address.storage.cap;

import static seedu.address.storage.cap.JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalModule.CS2100;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cap.module.Credit;
import seedu.address.model.cap.module.Grade;
import seedu.address.model.cap.module.ModuleCode;
import seedu.address.model.cap.module.Title;

public class JsonAdaptedModuleTest {

    private static final String INVALID_TITLE = "1234";
    private static final String INVALID_CREDIT = "0";
    private static final String INVALID_GRADE = "G";

    private static final String VALID_MODULECODE = CS2100.getModuleCode().toString();
    private static final String VALID_TITLE = CS2100.getTitle().toString();
    private static final String VALID_SEMESTER = CS2100.getSemester().toString();
    private static final String VALID_CREDIT = CS2100.getCredit().toString();
    private static final String VALID_GRADE = CS2100.getGrade().toString();

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(null, VALID_TITLE, VALID_SEMESTER,
            VALID_CREDIT, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_MODULECODE, INVALID_TITLE, VALID_SEMESTER,
                VALID_CREDIT, VALID_GRADE);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULECODE, null, VALID_SEMESTER,
            VALID_CREDIT, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidCredit_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_MODULECODE, VALID_TITLE, VALID_SEMESTER,
                INVALID_CREDIT, VALID_GRADE);
        String expectedMessage = Credit.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullCredit_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULECODE, VALID_TITLE, VALID_SEMESTER,
                null, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Credit.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        JsonAdaptedModule module =
                new JsonAdaptedModule(VALID_MODULECODE, VALID_TITLE, VALID_SEMESTER,
                VALID_CREDIT, INVALID_GRADE);
        String expectedMessage = Grade.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullGrade_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULECODE, VALID_TITLE, VALID_SEMESTER,
            null, VALID_GRADE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Grade.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

}
