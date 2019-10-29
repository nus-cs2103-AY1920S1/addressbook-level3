package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.SP_1_SEMESTER_NAME;
import static seedu.address.logic.commands.CommandTestUtil.SP_2_SEMESTER_NAME;
import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.CS3244;
import static seedu.address.testutil.TypicalModule.CS5219;
import static seedu.address.testutil.TypicalModule.CS5339;
import static seedu.address.testutil.TypicalModule.ST2334;
import static seedu.address.testutil.TypicalSemesterList.TYPICAL_SEMESTER_LIST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.module.Module;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;

/**
 * A utility class containing a list of {@code StudyPlan} objects to be used in tests.
 */
public class TypicalStudyPlans {
    public static final Title SP_1_TITLE = new Title("first study plan");
    public static final Title SP_2_TITLE = new Title("second study plan");

    private static final ModulesInfo modulesInfo = TypicalModulesInfo.getTypicalModulesInfo();

    // typical study plans
    public static final StudyPlan SP_1 = new StudyPlan(SP_1_TITLE, modulesInfo, SP_1_SEMESTER_NAME);
    public static final StudyPlan SP_2 = new StudyPlan(SP_2_TITLE, modulesInfo, SP_2_SEMESTER_NAME);
    public static final StudyPlan SP_3 = new StudyPlan(new Title("third study plan"), modulesInfo,
            SP_1_SEMESTER_NAME);

    // more study plans that are not included in the Typical Module Planner
    public static final StudyPlan SP_4 = new StudyPlan(new Title("fourth study plan"), modulesInfo,
            SP_2_SEMESTER_NAME);
    public static final StudyPlan SP_5 = new StudyPlan(new Title("fifth study plan"), modulesInfo,
            SP_2_SEMESTER_NAME);

    public static StudyPlan getTypicalStudyPlan() {
        HashMap<String, Module> moduleHashMap = new HashMap<>();
        moduleHashMap.put("ST2334", ST2334);
        moduleHashMap.put("CS3244", CS3244);
        moduleHashMap.put("CS1101S", CS1101S);
        moduleHashMap.put("CS2102", CS2102);
        moduleHashMap.put("CS5339", CS5339);
        moduleHashMap.put("CS5219", CS5219);

        UniqueSemesterList uniqueSemesterList = null;
        try {
            uniqueSemesterList = TYPICAL_SEMESTER_LIST.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        // construct model containing study plan without CS1101S in Y1S1
        return new StudyPlanBuilder()
                .withModules(moduleHashMap).withSemesters(uniqueSemesterList).build();
    }

    /**
     * Returns an {@code ModulePlanner} with all the typical studyPlans.
     */
    public static ModulePlanner getTypicalModulePlanner() {
        ModulePlanner ab = new ModulePlanner(modulesInfo);
        for (StudyPlan studyPlan : getTypicalStudyPlans()) {
            ab.addStudyPlan(studyPlan);
            studyPlan.setActivated(true);
        }
        ab.activateStudyPlan(SP_1.getIndex());
        return ab;
    }

    public static List<StudyPlan> getTypicalStudyPlans() {
        return new ArrayList<>(Arrays.asList(SP_1, SP_2, SP_3));
    }
}
