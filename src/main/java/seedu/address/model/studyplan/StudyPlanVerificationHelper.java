package seedu.address.model.studyplan;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Checks the Study Plan for graduation requirements.
 */
public class StudyPlanVerificationHelper {

    public static final int MC_REQUIREMENT = 108;
    public static final int CORE_REQUIREMENT = 15;
    public static final int FOCUS_REQUIREMENT = 3;

    public static boolean checkAll(StudyPlan sp) {
        return checkMcs(sp) && checkCore(sp) && checkFocus(sp);
    }

    /**
     * Describes whether overall graduation requirements for a study plan have been fulfilled.
     */
    public static String describeAll(StudyPlan sp) {
        if (checkAll(sp)) {
            return "All graduation requirements have been fulfilled!";
        } else {
            return "Graduation requirements have not been fulfilled.";
        }
    }

    public static boolean checkMcs(StudyPlan sp) {
        return sp.getTotalMcCount() >= MC_REQUIREMENT;
    }

    public static String describeMcs(StudyPlan sp) {
        return "MCs: " + sp.getTotalMcCount() + "/" + MC_REQUIREMENT;
    }

    public static boolean checkCore(StudyPlan sp) {
        return sp.getNumCoreModules() >= CORE_REQUIREMENT;
    }

    public static String describeCore(StudyPlan sp) {
        return "Number of core modules taken: " + sp.getNumCoreModules() + "/" + CORE_REQUIREMENT;
    }

    /**
     * Checks the focus area primaries of a study plan.
     */
    public static boolean checkFocus(StudyPlan sp) {
        HashMap<String, Integer> map = sp.getFocusPrimaries();
        for (String focus : map.keySet()) {
            int value = map.get(focus);
            if (value >= FOCUS_REQUIREMENT) {
                return true;
            }
        }
        return false;
    }

    /**
     * Describes the focus area primaries of a study plan.
     */
    public static String describeFocus(StudyPlan sp) {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of focus area primaries taken:\n");
        HashMap<String, Integer> map = sp.getFocusPrimaries();
        for (String focus : map.keySet().stream().sorted().collect(Collectors.toList())) {
            int value = map.get(focus);
            sb.append("[" + focus + "]: " + value + "\n");
        }
        return sb.toString();
    }
}
