package seedu.algobase.storage;

import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblemSearchRules.INVALID_KEYWORD;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

class JsonAdaptedKeywordTest {

    @Test
    public void toModelType_invalidKeyword_throwsIllegalValueException() {
        JsonAdaptedKeyword jsonAdaptedKeyword = new JsonAdaptedKeyword(INVALID_KEYWORD);
        String expectedMessage = Keyword.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedKeyword::toModelType);
    }

}
