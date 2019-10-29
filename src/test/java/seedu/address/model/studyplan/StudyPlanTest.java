package seedu.address.model.studyplan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.address.logic.commands.CommandTestUtil.SP_2_SEMESTER_NAME;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.SP_2;
import static seedu.address.testutil.TypicalStudyPlans.SP_2_TITLE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.semester.SemesterName;
import seedu.address.testutil.StudyPlanBuilder;

/**
 * A test class for {@code StudyPlan}.
 */
public class StudyPlanTest {
    @Test
    public void equals() throws CloneNotSupportedException {
        // same values -> returns true
        StudyPlan sp1Copy = new StudyPlanBuilder(SP_1).build();
        assertEquals(SP_1, sp1Copy);

        // same object -> returns true
        assertEquals(SP_1, SP_1);

        // null -> returns false
        assertNotEquals(null, SP_1);

        // different type -> returns false
        assertNotEquals(5, SP_1);

        // different module -> returns true
        assertNotEquals(SP_1, SP_2);

        // different name -> returns false
        StudyPlan editedSp1 = new StudyPlanBuilder(SP_1).withTitle(SP_2_TITLE).build();
        assertNotEquals(SP_1, editedSp1);

        StudyPlan sp1Clone = sp1Copy.clone();
        assertEquals(sp1Clone, sp1Copy);
        assertEquals(sp1Clone.getModuleTags(), sp1Copy.getModuleTags());
        assertNotSame(sp1Copy, sp1Clone);
    }

    @Test
    public void addModuleToSemester_valid() {

    }

    @Test
    public void getValidMods_returnsValidMods() {
        List<String> validMods = SP_1.getValidMods(SemesterName.Y3S1);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("CS1101S");
        expected.add("CS1231S");
        expected.add("IS1103X");
        expected.add("MA1521");
        assertEquals(validMods, expected);
    }

    @Test
    public void getNumCoreModules_noModules_returns0() {
        int numCoreModules = SP_1.getNumCoreModules();
        assertEquals(numCoreModules, 0);
    }

    @Test
    public void getters_setters() {
        StudyPlan sp1Copy = new StudyPlanBuilder(SP_1).build();
        sp1Copy.setTitle(SP_2_TITLE);
        assertEquals(SP_2_TITLE, sp1Copy.getTitle());
        sp1Copy.setCurrentSemester(SP_2_SEMESTER_NAME);
        assertEquals(SP_2_SEMESTER_NAME, sp1Copy.getCurrentSemester());
    }
}
