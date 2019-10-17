package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.*;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_TYPE = "tv show";
    private static final String INVALID_IS_WATCHED = "";
    private static final String INVALID_DATE = "";
    private static final int INVALID_RUNNING_TIME = -1;
    private static final String INVALID_ACTOR = "";


    private static final String VALID_NAME = "Titanic";
    private static final String VALID_TYPE = "movie";
    private static final String VALID_DESCRIPTION = "Ship";
    private static final boolean VALID_IS_WATCHED = false;
    private static final String VALID_DATE = "24 September 1997";
    private static final int VALID_RUNNING_TIME = 122;
    private static final String VALID_ACTOR_1 = "Leonardo Di Caprio";
    private static final String VALID_ACTOR_2 = "Kate Winslet";
    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_SHOW, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_SHOW, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        //assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseType_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseType((String) null));
    }

    @Test
    public void parseType_invalidValue_throwsParseException() {
        //assertThrows(ParseException.class, () -> ParserUtil.parseType(INVALID_TYPE));
    }

    @Test
    public void parseType_validValueWithoutWhitespace_returnsType() throws Exception {
        String expectedType = VALID_TYPE;
        assertEquals(expectedType, ParserUtil.parseType(VALID_TYPE));
    }

    @Test
    public void parseType_validValueWithWhitespace_returnsTrimmedType() throws Exception {
        String typeWithWhitespace = WHITESPACE + VALID_TYPE + WHITESPACE;
        String expectedType = VALID_TYPE;
        //assertEquals(expectedType, ParserUtil.parseType(typeWithWhitespace));
    }

    @Test
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithoutWhitespace_returnsDescription() throws Exception {
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(VALID_DESCRIPTION));
    }

    @Test
    public void parseDescription_validValueWithWhitespace_returnsTrimmedDescription() throws Exception {
        String descriptionWithWhitespace = WHITESPACE + VALID_DESCRIPTION + WHITESPACE;
        Description expectedDescription = new Description(VALID_DESCRIPTION);
        assertEquals(expectedDescription, ParserUtil.parseDescription(descriptionWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseIsWatched_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseIsWatched((String) null));
    }

    @Test
    public void parseIsWatched_invalidValue_throwsParseException() {
        //assertThrows(ParseException.class, () -> ParserUtil.parseIsWatched(INVALID_IS_WATCHED));
    }

    @Test
    public void parseIsWatched_validValueWithoutWhitespace_returnsIsWatched() throws Exception {
        IsWatched expectedIsWatched = new IsWatched(VALID_IS_WATCHED);
        assertEquals(expectedIsWatched, ParserUtil.parseIsWatched(Boolean.toString(VALID_IS_WATCHED)));
    }

    @Test
    public void parseIsWatched_validValueWithWhitespace_returnsTrimmedIsWatched() throws Exception {
        String isWatchedWithWhitespace = WHITESPACE + VALID_IS_WATCHED + WHITESPACE;
        IsWatched expectedIsWatched = new IsWatched(VALID_IS_WATCHED);
        assertEquals(expectedIsWatched, ParserUtil.parseIsWatched(isWatchedWithWhitespace));
    }

    @Test
    public void parseRunningTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRunningTime((String) null));
    }

    @Test
    public void parseRunningTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRunningTime(Integer.toString(INVALID_RUNNING_TIME)));
    }

    @Test
    public void parseRunningTime_validValueWithoutWhitespace_returnsRunningTime() throws Exception {
        RunningTime expectedRunningTime = new RunningTime(VALID_RUNNING_TIME);
        //assertEquals(expectedRunningTime, ParserUtil.parseRunningTime(Integer.toString(INVALID_RUNNING_TIME)));
    }

    @Test
    public void parseRunningTime_validValueWithWhitespace_returnsTrimmedRunningTime() throws Exception {
        String runningTimeWithWhitespace = WHITESPACE + VALID_RUNNING_TIME + WHITESPACE;
        RunningTime expectedRunningTime = new RunningTime(VALID_RUNNING_TIME);
        assertEquals(expectedRunningTime, ParserUtil.parseRunningTime(runningTimeWithWhitespace));
    }

    @Test
    public void parseActor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseActor(null));
    }

    @Test
    public void parseActor_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseActor(INVALID_ACTOR));
    }

    @Test
    public void parseActor_validValueWithoutWhitespace_returnsActor() throws Exception {
        Actor expectedActor = new Actor(VALID_ACTOR_1);
        assertEquals(expectedActor, ParserUtil.parseActor(VALID_ACTOR_1));
    }

    @Test
    public void parseActor_validValueWithWhitespace_returnsTrimmedActor() throws Exception {
        String actorWithWhitespace = WHITESPACE + VALID_ACTOR_1 + WHITESPACE;
        Actor expectedActor = new Actor(VALID_ACTOR_1);
        assertEquals(expectedActor, ParserUtil.parseActor(actorWithWhitespace));
    }

    @Test
    public void parseActors_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseActors(null));
    }

    @Test
    public void parseActors_collectionWithInvalidActors_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseActors(Arrays.asList(VALID_ACTOR_1, INVALID_ACTOR)));
    }

    @Test
    public void parseActors_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseActors(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseActors_collectionWithValidActors_returnsActorSet() throws Exception {
        Set<Actor> actualActorSet = ParserUtil.parseActors(Arrays.asList(VALID_ACTOR_1, VALID_ACTOR_2));
        Set<Actor> expectedActorSet = new HashSet<Actor>(Arrays.asList(new Actor(VALID_ACTOR_1), new Actor(VALID_ACTOR_2)));

        assertEquals(expectedActorSet, actualActorSet);
    }
}
