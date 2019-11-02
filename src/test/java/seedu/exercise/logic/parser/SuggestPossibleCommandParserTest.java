package seedu.exercise.logic.parser;

import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.exercise.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.exercise.logic.parser.predicate.PredicateUtil.predicateShowExercisesWithMuscle;
import static seedu.exercise.testutil.CommonTestData.DESC_OPERATION_TYPE_AND;
import static seedu.exercise.testutil.CommonTestData.DESC_SUGGEST_TYPE_POSSIBLE;
import static seedu.exercise.testutil.CommonTestData.MUSCLE_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.MUSCLE_DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.SuggestCommand;
import seedu.exercise.logic.commands.SuggestPossibleCommand;
import seedu.exercise.logic.parser.predicate.BasePropertyPredicate;
import seedu.exercise.logic.parser.predicate.ExercisePredicate;
import seedu.exercise.model.property.Muscle;
import seedu.exercise.model.resource.Exercise;

public class SuggestPossibleCommandParserTest {
    private SuggestCommandParser parser = new SuggestCommandParser();

    @Test
    public void parsePossible_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE);

        //no operation type for more than one predicate tag
        assertParseFailure(parser, DESC_SUGGEST_TYPE_POSSIBLE + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL,
                expectedMessage);
    }

    @Test
    public void parsePossible_optionalFieldsMissing_success() {
        Set<Muscle> targetMuscles = new HashSet<>();
        targetMuscles.add(new Muscle(VALID_MUSCLE_AEROBICS));
        targetMuscles.add(new Muscle(VALID_MUSCLE_BASKETBALL));
        BasePropertyPredicate predicateMuscleAnd = predicateShowExercisesWithMuscle(targetMuscles, true);

        //no custom properties
        Predicate<Exercise> exercisePredicateMuscleAdd = new ExercisePredicate(true, predicateMuscleAnd);
        assertParseSuccess(parser, DESC_SUGGEST_TYPE_POSSIBLE + DESC_OPERATION_TYPE_AND
            + MUSCLE_DESC_AEROBICS + MUSCLE_DESC_BASKETBALL,
            new SuggestPossibleCommand(exercisePredicateMuscleAdd));
    }

    @Test
    public void parsePossible_optionalOperationTypeMissing_success() {
        Set<Muscle> targetMuscles = new HashSet<>();
        targetMuscles.add(new Muscle(VALID_MUSCLE_AEROBICS));
        BasePropertyPredicate predicateOneMuscleAnd = predicateShowExercisesWithMuscle(targetMuscles, true);
        Predicate<Exercise> exercisePredicateOneMuscleAnd = new ExercisePredicate(true, predicateOneMuscleAnd);
        assertParseSuccess(parser, DESC_SUGGEST_TYPE_POSSIBLE + MUSCLE_DESC_AEROBICS,
                new SuggestPossibleCommand(exercisePredicateOneMuscleAnd));
    }

    @Test
    public void parsePossible_noPredicate_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuggestCommand.MESSAGE_USAGE);

        //no muscles and no custom properties
        assertParseFailure(parser, DESC_SUGGEST_TYPE_POSSIBLE + DESC_OPERATION_TYPE_AND, expectedMessage);
    }

}
