package seedu.revision.model.answerable;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerCheckerTest {

    @Test
    void check() {
        // Question: How many fingers does a person have?
        boolean result = AnswerChecker.check("10", Arrays.asList(new Answer("ten")));
        assertTrue(result);

        // Question: What diagram is used to represent a software system?
        result = AnswerChecker.check("uml", Arrays.asList(new Answer("UML diagram")));
        assertTrue(result);

        // Question: Name one functional testing used in software engineering
        result = AnswerChecker.check("regression test",
                Arrays.asList(new Answer("Unit Testing"), new Answer("Integration Testing"),
                        new Answer("System Testing"), new Answer("Sanity Testing"),
                        new Answer("Smoke Testing"), new Answer("Interface Testing"),
                        new Answer("Regression Testing"), new Answer("Acceptance Testing")));
        assertTrue(result);
    }
}
