package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.testutil.TypicalSemester;

/**
 * A test class for JsonAdaptedSemester.
 */
public class JsonAdaptedSemesterTest {

    private static final String INVALID_SEMESTER_NAME = "CS111";

    private static final String VALID_SEMESTER_NAME = SemesterName.Y2S2.toString();
    private static final boolean VALID_IS_BLOCKED = true;
    private static final String VALID_REASON_FOR_BLOCKED = "reason";
    private static final List<JsonAdaptedSkeletalModule> VALID_MODULES = new ArrayList<>();

    @Test
    public void toModelType_validSemesterDetails_returnsSemester() throws Exception {
        Semester typicalSemester = TypicalSemester.FULL_UNBLOCKED_SEMESTER_1;
        JsonAdaptedSemester adaptedSemester = new JsonAdaptedSemester(typicalSemester);
        Semester loadedSemester = adaptedSemester.toModelType();
        assertTrue(semesterLoadedCorrectly(typicalSemester, loadedSemester));
    }

    @Test
    public void toModelType_invalidSemesterName_throwsIllegalValueException() {
        JsonAdaptedSemester semester =
                new JsonAdaptedSemester(INVALID_SEMESTER_NAME, VALID_IS_BLOCKED,
                        VALID_REASON_FOR_BLOCKED, VALID_MODULES);
        String expectedMessage = SemesterName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, semester::toModelType);
    }

    /**
     * Returns true if the semester is loaded correct from JSON and a {@code JsonAdaptedSemester}.
     */
    public static boolean semesterLoadedCorrectly(Semester originalSemester, Semester loadedSemester) {
        boolean result = true;

        // semester name
        if (!originalSemester.getSemesterName().equals(loadedSemester.getSemesterName())) {
            result = false;
        }

        // is blocked
        if (originalSemester.isBlocked() != loadedSemester.isBlocked()) {
            result = false;
        }

        // reason for blocked
        if (!originalSemester.getReasonForBlocked().equals(loadedSemester.getReasonForBlocked())) {
            result = false;
        }

        // modules
        List<Module> originalModules = originalSemester.getModules().asUnmodifiableObservableList();
        List<Module> loadedModules = loadedSemester.getModules().asUnmodifiableObservableList();
        for (int i = 0; i < originalModules.size(); i++) {
            Module originalModule = originalModules.get(i);
            Module loadedModule = loadedModules.get(i);
            if (!originalModule.getModuleCode().equals(loadedModule.getModuleCode())) {
                result = false;
            }
        }

        return result;
    }
}
