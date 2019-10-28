package seedu.address.testutil;

import seedu.address.model.ModulePlanner;
import seedu.address.model.studyplan.StudyPlan;

/**
 * A utility class to help with building ModulePlanner objects.
 * Example usage: <br>
 * {@code ModulePlanner mp = new ModulePlannerBuilder().withStudyPlan(studyPlan).build();}
 */
public class ModulePlannerBuilder {

    private ModulePlanner modulePlanner;

    public ModulePlannerBuilder() {
        modulePlanner = new ModulePlanner();
    }

    public ModulePlannerBuilder(ModulePlanner modulePlanner) {
        this.modulePlanner = modulePlanner;
    }

    /**
     * Adds a new {@code StudyPlan} to the {@code ModulePlanner} that we are building.
     */
    public ModulePlannerBuilder withStudyPlan(StudyPlan studyPlan) {
        modulePlanner.addStudyPlan(studyPlan);
        return this;
    }

    /**
     * Adds new {@code StudyPlan}s to the {@code ModulePlanner} that we are building.
     */
    public ModulePlannerBuilder withStudyPlans(StudyPlan... studyPlans) {
        for (StudyPlan studyPlan : studyPlans) {
            modulePlanner.addStudyPlan(studyPlan);
        }
        return this;
    }

    public ModulePlanner build() {
        return modulePlanner;
    }
}
