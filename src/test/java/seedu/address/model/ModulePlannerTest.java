package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudyPlans.ALICE;
import static seedu.address.testutil.TypicalStudyPlans.getTypicalModulePlanner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.exceptions.DuplicateStudyPlanException;
import seedu.address.testutil.StudyPlanBuilder;

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
    public void resetData_withDuplicateStudyPlans_throwsDuplicateStudyPlanException() {
        // Two studyPlans with the same identity fields
        StudyPlan editedAlice = new StudyPlanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<StudyPlan> newStudyPlans = Arrays.asList(ALICE, editedAlice);
        ModulePlannerStub newData = new ModulePlannerStub(newStudyPlans);

        assertThrows(DuplicateStudyPlanException.class, () -> modulePlanner.resetData(newData));
    }

    @Test
    public void hasStudyPlan_nullStudyPlan_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modulePlanner.hasStudyPlan(null));
    }

    @Test
    public void hasStudyPlan_studyPlanNotInModulePlanner_returnsFalse() {
        assertFalse(modulePlanner.hasStudyPlan(ALICE));
    }

    @Test
    public void hasStudyPlan_studyPlanInModulePlanner_returnsTrue() {
        modulePlanner.addStudyPlan(ALICE);
        assertTrue(modulePlanner.hasStudyPlan(ALICE));
    }

    @Test
    public void hasStudyPlan_studyPlanWithSameIdentityFieldsInModulePlanner_returnsTrue() {
        modulePlanner.addStudyPlan(ALICE);
        StudyPlan editedAlice = new StudyPlanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(modulePlanner.hasStudyPlan(editedAlice));
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
    }

}
