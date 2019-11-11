package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.storage.JsonAdaptedStudyPlan.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.Tag;

/**
 * A test class for JsonAdaptedStudyPlan.
 */
public class JsonAdaptedStudyPlanTest {

    private static final String INVALID_TITLE = "AAAAAAAAAAAAAAAAAAAAA"; // 21 characters

    private static final int VALID_TOTAL_NUMBER = 10;
    private static final String VALID_TITLE = SP_1.getTitle().toString();
    private static final int VALID_INDEX = SP_1.getIndex();
    private static final List<JsonAdaptedSemester> VALID_SEMESTERS =
            SP_1.getSemesters().asUnmodifiableObservableList().stream()
                    .map(JsonAdaptedSemester::new).collect(Collectors.toList());
    private static final List<JsonAdaptedModule> VALID_MODULES =
            SP_1.getModules().values().stream().map(JsonAdaptedModule::new).collect(Collectors.toList());
    private static final List<JsonAdaptedTag> VALID_TAGS =
            SP_1.getModuleTags().asUnmodifiableObservableList().stream()
                    .map(JsonAdaptedTag::new).collect(Collectors.toList());
    private static final SemesterName VALID_CURRENT_SEMESTER = SP_1.getCurrentSemester();
    private static final List<JsonAdaptedTag> VALID_STUDY_PLAN_TAGS =
            SP_1.getStudyPlanTags().asUnmodifiableObservableList().stream()
                    .map(JsonAdaptedTag::new).collect(Collectors.toList());

    @Test
    public void toModelType_validStudyPlanDetails_returnsStudyPlan() throws Exception {
        JsonAdaptedStudyPlan adaptedStudyPlan = new JsonAdaptedStudyPlan(SP_1);
        StudyPlan skeletalStudyPlan = adaptedStudyPlan.toModelType();

        assertTrue(studyPlanLoadedCorrectly(SP_1, skeletalStudyPlan));
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedStudyPlan studyPlan =
                new JsonAdaptedStudyPlan(VALID_TOTAL_NUMBER, INVALID_TITLE, VALID_INDEX,
                        VALID_SEMESTERS, VALID_MODULES, VALID_TAGS, VALID_CURRENT_SEMESTER, VALID_STUDY_PLAN_TAGS);
        String expectedMessage = Title.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studyPlan::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedStudyPlan studyPlan = new JsonAdaptedStudyPlan(VALID_TOTAL_NUMBER, null, VALID_INDEX,
                VALID_SEMESTERS, VALID_MODULES, VALID_TAGS, VALID_CURRENT_SEMESTER, VALID_STUDY_PLAN_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studyPlan::toModelType);
    }

    /**
     * Returns a boolean to indicate whether the study plan has been loaded correctly from JSON.
     */
    public static boolean studyPlanLoadedCorrectly(StudyPlan originalStudyPlan, StudyPlan skeletalStudyPlan) {
        // test whether this study plan is rendered properly. compare between original and loaded (from JSON)
        boolean result = true;
        // semesters
        List<Semester> originalSemesters = originalStudyPlan.getSemesters().asUnmodifiableObservableList();
        List<Semester> loadedSemesters = skeletalStudyPlan.getSemesters().asUnmodifiableObservableList();
        for (int i = 0; i < originalSemesters.size(); i++) {
            SemesterName originalSemesterName = originalSemesters.get(i).getSemesterName();
            SemesterName loadedSemesterName = loadedSemesters.get(i).getSemesterName();
            if (!originalSemesterName.equals(loadedSemesterName)) {
                result = false;
            }
        }

        // title
        assertEquals(originalStudyPlan.getTitle(), skeletalStudyPlan.getTitle());

        // index
        assertEquals(originalStudyPlan.getIndex(), skeletalStudyPlan.getIndex());

        // modules
        HashMap<String, Module> originalModules = originalStudyPlan.getModules();
        HashMap<String, Module> loadedModules = skeletalStudyPlan.getModules();
        for (Module module : originalModules.values()) {
            String originalModuleCode = module.getModuleCode().value;
            String loadedModuleCode = loadedModules.get(originalModuleCode).getModuleCode().value;
            if (!originalModuleCode.equals(loadedModuleCode)) {
                result = false;
                break;
            }
        }

        // tags
        List<Tag> originalTags = originalStudyPlan.getModuleTags().asUnmodifiableObservableList();
        List<Tag> loadedTags = skeletalStudyPlan.getModuleTags().asUnmodifiableObservableList();
        for (int i = 0; i < originalTags.size(); i++) {
            Tag originalTag = originalTags.get(i);
            Tag loadedTag = loadedTags.get(i);
            if (!originalTag.equals(loadedTag)) {
                result = false;
                break;
            }
        }

        // study plan tags
        List<Tag> originalStudyPlanTags = originalStudyPlan.getStudyPlanTags().asUnmodifiableObservableList();
        List<Tag> loadedStudyPlanTags = skeletalStudyPlan.getStudyPlanTags().asUnmodifiableObservableList();
        for (int i = 0; i < originalStudyPlanTags.size(); i++) {
            Tag originalStudyPlanTag = originalStudyPlanTags.get(i);
            Tag loadedStudyPlanTag = loadedStudyPlanTags.get(i);
            if (!originalStudyPlanTag.equals(loadedStudyPlanTag)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
