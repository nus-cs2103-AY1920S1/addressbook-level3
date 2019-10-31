package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.PriorityTagType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UserTag;

/**
 * Contains utility methods for populating {@code ModulePlanner} with sample data.
 */
public class SampleDataUtil {
    public static StudyPlan[] getSampleStudyPlans(ModulesInfo modulesInfo) {
        // Can populate this with more dummy study plans
        StudyPlan[] studyPlans = new StudyPlan[4];

        SemesterName sampleCurrentSemester = SemesterName.Y1S1;

        StudyPlan sp1 = new StudyPlan(new Title("first study plan"), modulesInfo, sampleCurrentSemester);
        StudyPlan sp2 = new StudyPlan(new Title("second study plan"), modulesInfo, sampleCurrentSemester);
        StudyPlan sp3 = new StudyPlan(new Title("third study plan"), modulesInfo, sampleCurrentSemester);
        StudyPlan defaultStudyPlan = DefaultStudyPlanUtil.getDefaultStudyPlan(modulesInfo);

        sp1.addModuleToSemester(new ModuleCode("CS1101S"), SemesterName.Y1S1);
        sp1.addModuleToSemester(new ModuleCode("CS2030"), SemesterName.Y1S2);
        sp1.addModuleToSemester(new ModuleCode("CS2040S"), SemesterName.Y2S1);

        sp2.addModuleToSemester(new ModuleCode("CS3230"), SemesterName.Y2S1);
        sp2.addModuleToSemester(new ModuleCode("CS2100"), SemesterName.Y2S1);
        sp2.addModuleToSemester(new ModuleCode("CS2103T"), SemesterName.Y2S1);

        sp3.addModuleToSemester(new ModuleCode("MA1521"), SemesterName.Y1S1);

        studyPlans[0] = sp1;
        studyPlans[1] = sp2;
        studyPlans[2] = sp3;
        studyPlans[3] = defaultStudyPlan;

        return studyPlans;
    }

    public static ReadOnlyModulePlanner getSampleModulePlanner(ModulesInfo modulesInfo) {
        ModulePlanner sampleAb = new ModulePlanner(modulesInfo);
        /*
        for (StudyPlan sampleStudyPlan : getSampleStudyPlans(modulesInfo)) {
            sampleStudyPlan.setActivated(true);
            sampleAb.addStudyPlan(sampleStudyPlan);
        }
         */
        StudyPlan defaultStudyPlan = DefaultStudyPlanUtil.getDefaultStudyPlan(modulesInfo);
        defaultStudyPlan.setActivated(true);
        sampleAb.addStudyPlan(defaultStudyPlan);
        sampleAb.activateStudyPlan(defaultStudyPlan.getIndex());
        return sampleAb;
    }

    /**
     * Returns a list containing the list of user tag names given.
     */
    public static List<Tag> getUserTagList(String... strings) {
        return Arrays.stream(strings)
                .map(UserTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list containing the list of priority tag names given.
     */
    public static List<Tag> getPriorityTagList(String... strings) {
        return Arrays.stream(strings)
                .map(type -> new PriorityTag(PriorityTagType.valueOf(type)))
                .collect(Collectors.toList());
    }

}
