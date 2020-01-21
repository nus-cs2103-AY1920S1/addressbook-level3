package seedu.algobase.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.model.plan.PlanName;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;
/**
 * A utility class containing a sample AlgoBase to be used in tests.
 */
public class TypicalAlgoBase {

    /**
     * Returns an {@code AlgoBase} with all typical problems and a sample plan.
     */
    public static AlgoBase getTypicalAlgoBase() {
        AlgoBase algoBase = new AlgoBase();
        Set<Task> tasks = new HashSet<>();
        int i = 0;
        for (Problem problem : TypicalProblems.getTypicalProblems()) {
            algoBase.addProblem(problem);
            if (i < 2) {
                i++;
                tasks.add(new Task(problem, LocalDate.now().plusMonths(1), i % 2 == 0));
            }
        }
        Plan samplePlan = new Plan(
            new PlanName("Data Structures"),
            new PlanDescription("CS2040 Data Structures"),
            LocalDate.now(),
            LocalDate.now().plusMonths(2),
            tasks
        );
        algoBase.addPlan(samplePlan);
        return algoBase;
    }

}
