package seedu.jarvis.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /**
     * CLI syntax for FinanceTracker commands.
     */
    public static class FinanceSyntax {
        public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
        public static final Prefix PREFIX_MONEY = new Prefix("a/");
    }

    /**
     * CLI syntax for Planner commands.
     */
    public static class PlannerSyntax {
        public static final Prefix PREFIX_TASK_TYPE = new Prefix("t/");
        public static final Prefix PREFIX_TASK_DES = new Prefix("des/");
        public static final Prefix PREFIX_DATE = new Prefix("d/");
        public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
        public static final Prefix PREFIX_FREQ = new Prefix("f/");
        public static final Prefix PREFIX_TAG = new Prefix("#");
    }

    /**
     * CLI syntax for Course commands.
     */
    public static class CourseSyntax {
        public static final Prefix PREFIX_COURSE = new Prefix("c/");
    }

    /**
     * CLI syntax for CcaTracker commands.
     */
    public static class CcaTrackerCliSyntax {
        public static final Prefix PREFIX_CCA_NAME = new Prefix("n/");
        public static final Prefix PREFIX_CCA_TYPE = new Prefix("t/");
        public static final Prefix PREFIX_EQUIPMENT_NAME = new Prefix("e/");
        public static final Prefix PREFIX_PROGRESS_LEVEL_NAMES = new Prefix("p/");
        public static final Prefix PREFIX_PROGRESS_LEVEL = new Prefix("l/");
    }
}
