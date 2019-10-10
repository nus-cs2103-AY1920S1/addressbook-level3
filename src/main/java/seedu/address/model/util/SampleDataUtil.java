package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.UserTag;

/**
 * Contains utility methods for populating {@code ModulePlanner} with sample data.
 */
public class SampleDataUtil {
    public static StudyPlan[] getSampleStudyPlans() {
        // Can populate this with more study plans
        return new StudyPlan[] {
                new StudyPlan(new Title("study plan first draft"))
        };
    }

    public static ReadOnlyModulePlanner getSampleModulePlanner(ModulesInfo modulesInfo) {
        ModulePlanner sampleAb = new ModulePlanner(modulesInfo);
        for (StudyPlan sampleStudyPlan : getSampleStudyPlans()) {
            sampleAb.addStudyPlan(sampleStudyPlan);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<UserTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(UserTag::new)
                .collect(Collectors.toSet());
    }

}
