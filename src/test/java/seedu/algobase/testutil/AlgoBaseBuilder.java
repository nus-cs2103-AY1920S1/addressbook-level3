package seedu.algobase.testutil;

import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Problem.Problem;

/**
 * A utility class to help with building Algobase objects.
 * Example usage: <br>
 *     {@code AlgoBase ab = new AlgoBaseBuilder().withProblem("John", "Doe").build();}
 */
public class AlgoBaseBuilder {

    private AlgoBase algoBase;

    public AlgoBaseBuilder() {
        algoBase = new AlgoBase();
    }

    public AlgoBaseBuilder(AlgoBase algoBase) {
        this.algoBase = algoBase;
    }

    /**
     * Adds a new {@code Problem} to the {@code AlgoBase} that we are building.
     */
    public AlgoBaseBuilder withProblem(Problem problem) {
        algoBase.addProblem(problem);
        return this;
    }

    public AlgoBase build() {
        return algoBase;
    }
}
