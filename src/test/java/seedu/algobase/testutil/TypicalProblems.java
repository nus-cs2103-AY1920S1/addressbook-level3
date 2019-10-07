package seedu.algobase.testutil;

import seedu.algobase.model.problem.Problem;

public class TypicalProblems {
    public static final Problem QUICK_SORT = new ProblemBuilder()
            .withName("Quick Sort")
            .withDescription("This sorts an array quickly")
            .withAuthor("John Doe")
            .withWeblink("http://open.kattis.com")
            .build();
}
