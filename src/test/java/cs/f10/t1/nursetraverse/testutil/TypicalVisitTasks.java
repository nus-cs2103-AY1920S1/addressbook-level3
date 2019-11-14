package cs.f10.t1.nursetraverse.testutil;

import cs.f10.t1.nursetraverse.model.util.SampleDataUtil;
import cs.f10.t1.nursetraverse.model.visittask.VisitTask;

/**
 * A utility class containing a list of {@code VisitTask} objects to be used in tests.
 */
public class TypicalVisitTasks {

    public static final VisitTask SAMPLE_VISIT_TASK_NO_DETAIL = SampleDataUtil
            .makeVisitTask("Descript!", "", true);
    public static final VisitTask SAMPLE_VISIT_TASK_WITH_DETAIL = SampleDataUtil
            .makeVisitTask("test Description", "with detail", false);
    public static final VisitTask SAMPLE_VISIT_TASK_SPECIAL_CHARS = SampleDataUtil
            .makeVisitTask("Ensure he drinks 100% of it and pays $10.20!",
                    "I don't think it worked", false);
}
