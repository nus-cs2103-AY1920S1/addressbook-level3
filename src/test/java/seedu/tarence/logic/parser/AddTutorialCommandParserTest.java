package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_WEEKS_RANGE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_DAY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_DAY_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_DUR;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_DUR_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_START_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_START_HHMMSS;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEKS_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEKS_LIST;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEKS_ODD_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEKS_ODD_LIST;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEKS_RANGE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEKS_RANGE_LIST;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.logic.parser.ParserMessages.MESSAGE_INVALID_WEEK_RANGE;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.AddTutorialCommand;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;
import seedu.tarence.testutil.TimeTableBuilder;
import seedu.tarence.testutil.TutorialBuilder;

public class AddTutorialCommandParserTest {
    private AddTutorialCommandParser parser = new AddTutorialCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        // list of tutorial weeks specified
        final Set<Week> validWeeksSet = new TreeSet<>();
        for (int w : VALID_WEEKS_LIST) {
            validWeeksSet.add(new Week(w));
        }
        TimeTable weeksListTimeTable = new TimeTableBuilder().withWeeks(validWeeksSet)
                .withDuration(VALID_TUTORIAL_DUR).withStartTime(VALID_TUTORIAL_START_HHMMSS)
                .withDayOfWeek(VALID_TUTORIAL_DAY).build();
        // for some reason the builder here sometimes contains default students. Remove them for this test to work.
        TutorialBuilder.DEFAULT_STUDENTS.clear();
        Tutorial weeksListTutorial = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTORIAL_NAME)
                .withTimeTable(weeksListTimeTable).build();
        assertParseSuccess(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_DAY_DESC
                + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC + VALID_WEEKS_DESC,
                new AddTutorialCommand(weeksListTutorial));

        // range of tutorial weeks specified
        final Set<Week> validWeeksRangeSet = new TreeSet<>();
        for (int w : VALID_WEEKS_RANGE_LIST) {
            validWeeksRangeSet.add(new Week(w));
        }
        TimeTable weeksRangeTimeTable = new TimeTableBuilder().withWeeks(validWeeksRangeSet)
                .withDuration(VALID_TUTORIAL_DUR).withStartTime(VALID_TUTORIAL_START_HHMMSS)
                .withDayOfWeek(VALID_TUTORIAL_DAY).build();
        Tutorial weeksRangeTutorial = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTORIAL_NAME)
                .withTimeTable(weeksRangeTimeTable).build();
        assertParseSuccess(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_DAY_DESC
                 + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC + VALID_WEEKS_RANGE_DESC,
                 new AddTutorialCommand(weeksRangeTutorial));

        // "odd" specified for tutorial weeks
        final Set<Week> validWeeksOddSet = new TreeSet<>();
        for (int w : VALID_WEEKS_ODD_LIST) {
            validWeeksOddSet.add(new Week(w));
        }
        TimeTable weeksOddTimeTable = new TimeTableBuilder().withWeeks(validWeeksOddSet)
                .withDuration(VALID_TUTORIAL_DUR).withStartTime(VALID_TUTORIAL_START_HHMMSS)
                .withDayOfWeek(VALID_TUTORIAL_DAY).build();
        Tutorial weeksOddTutorial = new TutorialBuilder().withModCode(VALID_MODCODE).withTutName(VALID_TUTORIAL_NAME)
                .withTimeTable(weeksOddTimeTable).build();
        assertParseSuccess(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_DAY_DESC
                        + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC + VALID_WEEKS_ODD_DESC,
                new AddTutorialCommand(weeksOddTutorial));

        // no tutorial weeks specified- default to weeks 3-13
        TimeTable weeksDefaultTimeTable = new TimeTableBuilder().withDuration(VALID_TUTORIAL_DUR)
                .withStartTime(VALID_TUTORIAL_START_HHMMSS).withDayOfWeek(VALID_TUTORIAL_DAY).build();
        Tutorial weeksDefaultTutorial = new TutorialBuilder().withModCode(VALID_MODCODE)
                .withTutName(VALID_TUTORIAL_NAME).withTimeTable(weeksDefaultTimeTable).build();
        assertParseSuccess(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_DAY_DESC
                        + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC,
                new AddTutorialCommand(weeksDefaultTutorial));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTutorialCommand.MESSAGE_USAGE);

        // missing module prefix
        assertParseFailure(parser, VALID_MODCODE + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_DAY_DESC
                        + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC + VALID_WEEKS_DESC,
                expectedMessage);

        // missing tutorial name prefix
        assertParseFailure(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME + VALID_TUTORIAL_DAY_DESC
                        + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC + VALID_WEEKS_DESC,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid range of tutorial weeks
        assertParseFailure(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_DAY_DESC
                        + VALID_TUTORIAL_START_DESC + VALID_TUTORIAL_DUR_DESC + INVALID_WEEKS_RANGE_DESC,
                MESSAGE_INVALID_WEEK_RANGE);
    }
}
