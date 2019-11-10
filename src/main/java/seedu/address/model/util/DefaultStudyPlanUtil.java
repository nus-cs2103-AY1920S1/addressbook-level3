package seedu.address.model.util;

import seedu.address.model.ModulesInfo;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;

/**
 * Contains utility methods for creating a default {@code StudyPlan} based on NUS SOC's recommended study plan.
 * This study plan can be found
 * at https://www.comp.nus.edu.sg/images/resources/content/undergraduates/study_planner-CS2019.pdf
 */
public class DefaultStudyPlanUtil {

    // remember to setActivated(true) and activate it if a default plan is needed.
    public static StudyPlan getDefaultStudyPlan(ModulesInfo modulesInfo) {

        SemesterName defaultCurrentSemester = SemesterName.Y1S1;

        // Sample Study Plan for B.Comp(CS) Cohort AY19/20
        StudyPlan defaultStudyPlan = new StudyPlan(new Title("Recommended Plan"),
                modulesInfo, defaultCurrentSemester);

        // Y1S1
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS1101S"), SemesterName.Y1S1);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS1231S"), SemesterName.Y1S1);

        // Y1S2
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2030"), SemesterName.Y1S2);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2040S"), SemesterName.Y1S2);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2100"), SemesterName.Y1S2);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("IS1103X"), SemesterName.Y1S2);

        // Y2S1
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2103T"), SemesterName.Y2S1);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2101"), SemesterName.Y2S1);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2105"), SemesterName.Y2S1);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS2106"), SemesterName.Y2S1);

        // Y2S2
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS3230"), SemesterName.Y2S2);
        defaultStudyPlan.addModuleToSemester(new ModuleCode("ES2660"), SemesterName.Y2S2);

        // Y3S1
        defaultStudyPlan.addModuleToSemester(new ModuleCode("CS3203"), SemesterName.Y3S1);

        return defaultStudyPlan;
    }

}
