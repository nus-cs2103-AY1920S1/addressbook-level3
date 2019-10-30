package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_TYPE;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.logic.parser.SuggestCommandParser.SUGGEST_TYPE_BASIC;
import static seedu.exercise.logic.parser.SuggestCommandParser.SUGGEST_TYPE_POSSIBLE;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.predicateShowExercisesWithMuscle;
import static seedu.exercise.testutil.CommonTestData.DESC_OPERATION_TYPE_AND;
import static seedu.exercise.testutil.CommonTestData.DESC_SUGGEST_TYPE_BASIC;
import static seedu.exercise.testutil.CommonTestData.DESC_SUGGEST_TYPE_POSSIBLE;
import static seedu.exercise.testutil.CommonTestData.INVALID_DESC_SUGGEST_TYPE;
import static seedu.exercise.testutil.CommonTestData.MUSCLE_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_NON_EMPTY;
import static seedu.exercise.testutil.CommonTestData.PREAMBLE_WHITESPACE;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.SuggestBasicCommand;
import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.logic.parser.predicate.BasePropertyPredicate;
import seedu.exercise.logic.parser.predicate.ExercisePredicate;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;

class SuggestCommandParserTest {
    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    void parse_validValues_success() {
        Set<Muscle> targetMuscles = new HashSet<>();
        targetMuscles.add(new Muscle(VALID_MUSCLE_AEROBICS));
        boolean isStrict = true;
        BasePropertyPredicate predicateMuscleAnd = predicateShowExercisesWithMuscle(targetMuscles, isStrict);
        Predicate<Exercise> exercisePredicate = new ExercisePredicate(isStrict, predicateMuscleAnd);

        //whitespace preamble -> success
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DESC_SUGGEST_TYPE_BASIC, new SuggestBasicCommand());

        //suggest basic type suggest type -> success
        assertParseSuccess(parser, DESC_SUGGEST_TYPE_BASIC, new SuggestBasicCommand());

        //suggest possible type suggest type -> success
        assertParseSuccess(parser, DESC_SUGGEST_TYPE_POSSIBLE + DESC_OPERATION_TYPE_AND
            + MUSCLE_DESC_AEROBICS, new SuggestPossibleCommand(exercisePredicate));
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE);
        String expectedMessageInvalidSuggestType =
            String.format(MESSAGE_INVALID_TYPE, "Suggest type", SUGGEST_TYPE_BASIC, SUGGEST_TYPE_POSSIBLE);

        //non-empty preamble -> failure
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DESC_SUGGEST_TYPE_BASIC, expectedMessage);

        //suggest type not present -> failure
        assertParseFailure(parser, "", expectedMessage);

        //suggest type prefix present but incorrect suggest type -> failure
        assertParseFailure(parser, INVALID_DESC_SUGGEST_TYPE, expectedMessageInvalidSuggestType);
    }

}
