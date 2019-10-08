package seedu.address.testutil;

import seedu.address.model.ModulePlanner;
import seedu.address.model.studyplan.StudyPlan;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ModulePlanner ab = new ModulePlannerBuilder().withStudyPlan("John", "Doe").build();}
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

    public ModulePlanner build() {
        return modulePlanner;
    }
}
