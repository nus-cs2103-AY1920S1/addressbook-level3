package seedu.revision.model.answerable;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.revision.model.category.Category;

class AnswerCheckerTest {

    @Test
    void checkTest() {

        boolean result = AnswerChecker.check("10", new Saq(new Question("How many fingers does a "
                + "person have?"),
                new ArrayList<>(Arrays.asList(new Answer("ten"))),
                new Difficulty("1"), getCategorySet("random")));
        assertTrue(result);

        result = AnswerChecker.check("uml", new Saq(new Question("What diagram is used to represent "
                + "a software system?"),
                new ArrayList<>(Arrays.asList(new Answer("UML diagram"))),
                new Difficulty("1"), getCategorySet("random")));
        assertTrue(result);

        result = AnswerChecker.check("regression test", new Saq(new Question("Name one functional "
                + "testing used in software engineering."),
                new ArrayList<>(Arrays.asList(new Answer("Unit Testing"), new Answer("Integration Testing"),
                        new Answer("System Testing"), new Answer("Sanity Testing"),
                        new Answer("Smoke Testing"), new Answer("Interface Testing"),
                        new Answer("Regression Testing"), new Answer("Acceptance Testing"))),
                new Difficulty("2"), getCategorySet("Software Testing")));
        assertTrue(result);

        result = AnswerChecker.check("13", new Saq(new Question("How many fingers does a person have?"),
                new ArrayList<>(Arrays.asList(new Answer("ten"))),
                new Difficulty("1"), getCategorySet("random")));
        assertFalse(result);

        result = AnswerChecker.check("block diagram", new Saq(new Question("What diagram is used to "
                + "represent a software system?"),
                new ArrayList<>(Arrays.asList(new Answer("UML diagram"))),
                new Difficulty("1"), getCategorySet("random")));
        assertFalse(result);

        result = AnswerChecker.check("bug testing", new Saq(new Question("Name one functional testing "
                + "used in software engineering."),
                new ArrayList<>(Arrays.asList(new Answer("Unit Testing"), new Answer("Integration Testing"),
                        new Answer("System Testing"), new Answer("Sanity Testing"),
                        new Answer("Smoke Testing"), new Answer("Interface Testing"),
                        new Answer("Regression Testing"), new Answer("Acceptance Testing"))),
                new Difficulty("2"), getCategorySet("Software Testing")));
        assertFalse(result);
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }
}
