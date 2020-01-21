package seedu.algobase.storage;

import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblemSearchRules.INVALID_ADAPTED_KEYWORD;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

class JsonAdaptedNameContainsKeywordsPredicateTest {

    @Test
    public void toModelType_invalidAdaptedKeyword_throwsIllegalValueException() {
        JsonAdaptedNameContainsKeywordsPredicate jsonAdaptedNameContainsKeywordsPredicate =
            new JsonAdaptedNameContainsKeywordsPredicate(Collections.singletonList(INVALID_ADAPTED_KEYWORD));
        String expectedMessage = Keyword.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage,
            jsonAdaptedNameContainsKeywordsPredicate::toModelType);
    }

}
