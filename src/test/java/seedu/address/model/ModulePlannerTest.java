package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudyPlans.SP_1;
import static seedu.address.testutil.TypicalStudyPlans.SP_5;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.versiontracking.VersionTrackingManager;


public class ModulePlannerTest {

    private final ModulePlanner modulePlanner = new ModulePlanner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), modulePlanner.getStudyPlanList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanner.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyModulePlanner_replacesData() {
        ModulePlanner newData = getTypicalModulePlanner();
        modulePlanner.resetData(newData);
        assertEquals(newData, modulePlanner);
    }

    @Test
    public void hasStudyPlan_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanner.hasStudyPlan(null));
    }

    @Test
    public void hasStudyPlan_studyPlanNotInModulePlanner_returnsFalse() {
        assertFalse(modulePlanner.hasStudyPlan(SP_5));
    }

    @Test
    public void hasStudyPlan_studyPlanInModulePlanner_returnsTrue() {
        modulePlanner.addStudyPlan(SP_1);
        assertTrue(modulePlanner.hasStudyPlan(SP_1));
    }

    @Test
    public void getStudyPlanList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modulePlanner.getStudyPlanList().remove(0));
    }

    /**
     * A stub ReadOnlyModulePlanner whose studyPlans list can violate interface constraints.
     */
    private static class ModulePlannerStub implements ReadOnlyModulePlanner {
        private final ObservableList<StudyPlan> studyPlans = FXCollections.observableArrayList();

        ModulePlannerStub(Collection<StudyPlan> studyPlans) {
            this.studyPlans.setAll(studyPlans);
        }

        @Override
        public ObservableList<StudyPlan> getStudyPlanList() {
            return studyPlans;
        }

        @Override
        public StudyPlan getActiveStudyPlan() {
            return null;
        }

        @Override
        public VersionTrackingManager getVersionTrackingManager() {
            return null;
        }

        @Override
        public SemesterName getCurrentSemester() {
            return null;
        }

        @Override
        public ModulesInfo getModulesInfo() {
            return null;
        }

        @Override
        public UniqueTagList getActiveTags() {
            return null;
        }

        @Override
        public List<String> getModuleCodes() {
            return null;
        }

        @Override
        public List<String> getActiveListOfSemesterNames() {
            return null;
        }
    }

}
