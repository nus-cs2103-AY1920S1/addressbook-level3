package seedu.ezwatchlist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;
import static seedu.ezwatchlist.testutil.TypicalIndexes.INDEX_FIRST_SHOW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.actor.Actor;
import seedu.ezwatchlist.model.show.Date;
import seedu.ezwatchlist.model.show.Name;


public class ParserUtilTest {
    private static final String INVALID_NAME = "";
    private static final String INVALID_DESCRIPTION = "";
    private static final String INVALID_IS_WATCHED = "";
    private static final String INVALID_DATE = "";
    private static final int INVALID_RUNNING_TIME = -1;
    private static final String INVALID_ACTORS = "";

    private static final String VALID_NAME = "Titanic";
    private static final String VALID_DESCRIPTION = "Ship";
    private static final boolean VALID_IS_WATCHED = false;
    private static final String VALID_DATE = "24/9/1997";
    private static final int VALID_RUNNING_TIME = 122;
    private static final String VALID_ACTORS_1 = "Leonardo Di Caprio";
    private static final String VALID_ACTORS_2 = "Kate Winslet";
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
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
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
    public void parseDescription_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseDescription_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_DESCRIPTION));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedDate = new Date("1997-9-24");
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    void parseType() throws ParseException {
        assertThrows(NullPointerException.class, ()-> ParserUtil.parseType(null));
        assertThrows(ParseException.class, ()-> ParserUtil.parseType("wrong type"));
        assertTrue(ParserUtil.parseType("movie").equals("movie"));
    }

    @Test
    void parseDateInvalid() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    void parseDateAddEditCommandInvalidTest() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateAddEditCommand("240997"));
    }

    @Test
    void parseRunningTimeTest() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRunningTime("A"));
        assertThrows(ParseException.class, () -> ParserUtil.parseRunningTime("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseRunningTime("10000000000000000"));
    }

    @Test
    void parseActorTest() throws ParseException {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseActor(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseActor(""));
        assertEquals(new Actor("May"), ParserUtil.parseActor("May"));
    }

    @Test
    void parseActorsTest() throws ParseException {
        final Set<Actor> actorSet = new HashSet<>();
        actorSet.add(new Actor("May"));
        Collection<String> actors = new ArrayList<>();
        actors.add("May");
        assertEquals(actorSet, ParserUtil.parseActors(actors));
    }

    @Test
    void parseNumOfEpisodesWatchedTest() throws ParseException {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNumOfEpisodesWatched(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseNumOfEpisodesWatched("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNumOfEpisodesWatched("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNumOfEpisodesWatched("A"));
        assertEquals(ParserUtil.parseNumOfEpisodesWatched("1"), 1);
    }


    @Test
    void parseNumOfSeasonsWatchedTest() throws ParseException {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNumOfSeasonsWatched(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseNumOfSeasonsWatched("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNumOfSeasonsWatched("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseNumOfSeasonsWatched("A"));
        assertEquals(ParserUtil.parseNumOfSeasonsWatched("1"), 1);
    }

    @Test
    void parseIndexTest() throws ParseException {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddIndex(null));
        assertThrows(ParseException.class, () -> ParserUtil.parseAddIndex("-1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAddIndex("0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseAddIndex("A"));
        assertEquals(ParserUtil.parseAddIndex("1"), 1);
    }

    @Test
    void parseIsWatchedInvalidTest() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIsWatched("INVALID"));
    }
}
