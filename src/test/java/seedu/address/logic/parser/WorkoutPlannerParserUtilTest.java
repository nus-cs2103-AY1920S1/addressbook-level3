package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.WorkoutPlannerParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXERCISE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.details.Distance;
import seedu.address.model.details.ExerciseDetail;
import seedu.address.model.details.unit.DistanceUnit;
import seedu.address.model.exercise.ExerciseName;

public class WorkoutPlannerParserUtilTest {
    private static final String INVALID_NAME = "Revers* Lunge";
    private static final String INVALID_DISTANCE = "400 kg";

    private static final String VALID_NAME = "Reverse Lunge";
    private static final String VALID_DISTANCE_1 = "400 m";
    private static final String VALID_DISTANCE_2 = "2.4 km";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> WorkoutPlannerParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> WorkoutPlannerParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_EXERCISE, WorkoutPlannerParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_EXERCISE, WorkoutPlannerParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> WorkoutPlannerParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> WorkoutPlannerParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        ExerciseName expectedExerciseName = new ExerciseName(VALID_NAME);
        assertEquals(expectedExerciseName, WorkoutPlannerParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseExerciseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        ExerciseName expectedExerciseName = new ExerciseName(VALID_NAME);
        assertEquals(expectedExerciseName, WorkoutPlannerParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseDistance_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> WorkoutPlannerParserUtil.parseDistance(null));
    }

    @Test
    public void parseDistance_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> WorkoutPlannerParserUtil.parseDistance(INVALID_DISTANCE));
    }

    @Test
    public void parseDistance_validValueWithoutWhitespace_returnsDistance() throws Exception {
        ExerciseDetail expectedExerciseDetail = new Distance((float) 400.0, DistanceUnit.METER);
        assertEquals(expectedExerciseDetail, WorkoutPlannerParserUtil.parseDistance(VALID_DISTANCE_1));
    }

    @Test
    public void parseDistance_validValueWithWhitespace_returnsDistance() throws Exception {
        String distanceWithWhitespace = WHITESPACE + VALID_DISTANCE_1 + WHITESPACE;
        ExerciseDetail expectedExerciseDetail = new Distance((float) 400.0, DistanceUnit.METER);
        assertEquals(expectedExerciseDetail, WorkoutPlannerParserUtil.parseDistance(distanceWithWhitespace));
    }
}
