package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.QuestionBuilder;

class QuestionContainsKeywordsPredicateTest {
    @Test
    void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        QuestionContainsKeywordsPredicate firstPredicateCopy =
                new QuestionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different note -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    void test_bodyContainsKeywords_returnsTrue() {
        // One keyword
        QuestionContainsKeywordsPredicate predicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new QuestionBuilder().withQuestionBody("Alice Bob").build()));

        // Multiple keywords
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new QuestionBuilder().withQuestionBody("Alice Bob").build()));

        // Only one matching keyword
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new QuestionBuilder().withQuestionBody("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new QuestionBuilder().withQuestionBody("Alice Bob").build()));
    }

    @Test
    void test_bodyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QuestionContainsKeywordsPredicate predicate = new QuestionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new QuestionBuilder().withQuestionBody("Alice").build()));

        // Non-matching keyword
        predicate = new QuestionContainsKeywordsPredicate(Collections.singletonList("Carol"));
        assertFalse(predicate.test(new QuestionBuilder().withQuestionBody("Alice Bob").build()));

        // Keywords match answer, subject and difficulty, but does not match question body
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("12345", "person", "Main", "Street"));
        assertTrue(predicate.test(new QuestionBuilder()
                .withQuestionBody("Alice")
                .withAnswer("12345")
                .withSubject("person")
                .withDifficulty("Main Street")
                .build()));
    }
}
