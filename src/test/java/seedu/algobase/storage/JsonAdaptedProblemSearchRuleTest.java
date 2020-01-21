package seedu.algobase.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.algobase.storage.JsonAdaptedProblemSearchRule.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblemSearchRules.ALL_PREDICATE;
import static seedu.algobase.testutil.TypicalProblemSearchRules.MEDIUM_DIFFICULTY;
import static seedu.algobase.testutil.TypicalProblemSearchRules.NAME_SEQUENCES;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Name;

class JsonAdaptedProblemSearchRuleTest {

    private static final String INVALID_NAME = "*%^SAD";
    private static final JsonAdaptedDifficultyIsInRangePredicate INVALID_ADAPTED_DIFFICULTY_PREDICATE_ONE =
        new JsonAdaptedDifficultyIsInRangePredicate(0.0, 3.0);
    private static final JsonAdaptedDifficultyIsInRangePredicate INVALID_ADAPTED_DIFFICULTY_PREDICATE_TWO =
        new JsonAdaptedDifficultyIsInRangePredicate(1.0, 5.1);
    private static final JsonAdaptedDifficultyIsInRangePredicate INVALID_ADAPTED_DIFFICULTY_PREDICATE_THREE =
        new JsonAdaptedDifficultyIsInRangePredicate(3.0, 1.0);
    private static final JsonAdaptedDifficultyIsInRangePredicate INVALID_ADAPTED_DIFFICULTY_PREDICATE_FOUR =
        new JsonAdaptedDifficultyIsInRangePredicate(-1.0, 3.0);
    private static final JsonAdaptedDifficultyIsInRangePredicate INVALID_ADAPTED_DIFFICULTY_PREDICATE_FIVE =
        new JsonAdaptedDifficultyIsInRangePredicate(-3.0, -1.0);

    private static final String VALID_NAME = ALL_PREDICATE.getName().name;
    private static final JsonAdaptedNameContainsKeywordsPredicate VALID_ADAPTED_NAME_PREDICATE =
        new JsonAdaptedNameContainsKeywordsPredicate(ALL_PREDICATE.getNamePredicate().get());
    private static final JsonAdaptedAuthorMatchesKeywordPredicate VALID_ADAPTED_AUTHOR_PREDICATE =
        new JsonAdaptedAuthorMatchesKeywordPredicate(ALL_PREDICATE.getAuthorPredicate().get());
    private static final JsonAdaptedDescriptionContainsKeywordsPredicate VALID_ADAPTED_DESCRIPTION_PREDICATE =
        new JsonAdaptedDescriptionContainsKeywordsPredicate(ALL_PREDICATE.getDescriptionPredicate().get());
    private static final JsonAdaptedSourceMatchesKeywordPredicate VALID_ADAPTED_SOURCE_PREDICATE =
        new JsonAdaptedSourceMatchesKeywordPredicate(ALL_PREDICATE.getSourcePredicate().get());
    private static final JsonAdaptedDifficultyIsInRangePredicate VALID_ADAPTED_DIFFICULTY_PREDICATE =
        new JsonAdaptedDifficultyIsInRangePredicate(ALL_PREDICATE.getDifficultyPredicate().get());
    private static final JsonAdaptedTagIncludesKeywordsPredicate VALID_ADAPTED_TAG_PREDICATE =
        new JsonAdaptedTagIncludesKeywordsPredicate(ALL_PREDICATE.getTagPredicate().get());

    @Test
    public void toModelType_validPartialDetailsOne_returnsProblemSearchRule() throws IllegalValueException {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(MEDIUM_DIFFICULTY);
        assertEquals(MEDIUM_DIFFICULTY, adaptedProblemSearchRule.toModelType());
    }

    @Test
    public void toModelType_validPartialDetailsTwo_returnsProblemSearchRule() throws IllegalValueException {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(NAME_SEQUENCES);
        assertEquals(NAME_SEQUENCES, adaptedProblemSearchRule.toModelType());
    }

    @Test
    public void toModelType_validFullDetails_returnsProblemSearchRule() throws IllegalValueException {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(ALL_PREDICATE);
        assertEquals(ALL_PREDICATE, adaptedProblemSearchRule.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(INVALID_NAME,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, VALID_ADAPTED_DIFFICULTY_PREDICATE, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(null,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, VALID_ADAPTED_DIFFICULTY_PREDICATE, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

    // Note that all null predicates should only be achievable if the user manually changed the JSON file.
    // In order not to break the entire save file, we choose to accept such "illegal" case.
    @Test
    public void toModelType_allNullPredicates_success() throws IllegalValueException {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(VALID_NAME,
            null, null, null, null, null, null);
        adaptedProblemSearchRule.toModelType();
    }

    @Test
    public void toModelType_zeroLowerBound_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(VALID_NAME,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, INVALID_ADAPTED_DIFFICULTY_PREDICATE_ONE, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

    @Test
    public void toModelType_tooHighLowerBound_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(VALID_NAME,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, INVALID_ADAPTED_DIFFICULTY_PREDICATE_TWO, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

    @Test
    public void toModelType_inversedBounds_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(VALID_NAME,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, INVALID_ADAPTED_DIFFICULTY_PREDICATE_THREE, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

    @Test
    public void toModelType_negativeLowerBound_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(VALID_NAME,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, INVALID_ADAPTED_DIFFICULTY_PREDICATE_FOUR, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

    @Test
    public void toModelType_negativeBounds_throwsIllegalValueException() {
        JsonAdaptedProblemSearchRule adaptedProblemSearchRule = new JsonAdaptedProblemSearchRule(VALID_NAME,
            VALID_ADAPTED_NAME_PREDICATE, VALID_ADAPTED_AUTHOR_PREDICATE, VALID_ADAPTED_DESCRIPTION_PREDICATE,
            VALID_ADAPTED_SOURCE_PREDICATE, INVALID_ADAPTED_DIFFICULTY_PREDICATE_FIVE, VALID_ADAPTED_TAG_PREDICATE);
        String expectedMessage = DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, adaptedProblemSearchRule::toModelType);
    }

}
