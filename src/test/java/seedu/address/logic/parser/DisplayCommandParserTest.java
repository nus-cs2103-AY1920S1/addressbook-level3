package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_FORMAT_DESC_BARCHART;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_FORMAT_DESC_LINECHART;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_FORMAT_DESC_PIECHART;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_INDICATOR_DESC_AGE_GROUP_BREAKDOWN;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_INDICATOR_DESC_GENDER_BREAKDOWN;
import static seedu.address.logic.commands.CommandTestUtil.DISPLAY_INDICATOR_DESC_POLICY_POPULARITY_BREAKDOWN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISPLAY_FORMAT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISPLAY_INDICATOR_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.visual.DisplayFormat.BARCHART;
import static seedu.address.model.visual.DisplayFormat.LINECHART;
import static seedu.address.model.visual.DisplayFormat.PIECHART;
import static seedu.address.model.visual.DisplayIndicator.AGE_GROUP_BREAKDOWN;
import static seedu.address.model.visual.DisplayIndicator.GENDER_BREAKDOWN;
import static seedu.address.model.visual.DisplayIndicator.POLICY_POPULARITY_BREAKDOWN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DisplayCommand;
import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;

class DisplayCommandParserTest {
    private DisplayCommandParser parser = new DisplayCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DisplayIndicator expectedDisplayIndicator = new DisplayIndicator(AGE_GROUP_BREAKDOWN);
        DisplayFormat expectedDisplayFormat = new DisplayFormat(BARCHART);
        assertParseSuccess(parser,
            DISPLAY_INDICATOR_DESC_AGE_GROUP_BREAKDOWN
                + DISPLAY_FORMAT_DESC_BARCHART,
            new DisplayCommand(expectedDisplayIndicator, expectedDisplayFormat));

        expectedDisplayIndicator = new DisplayIndicator(GENDER_BREAKDOWN);
        expectedDisplayFormat = new DisplayFormat(LINECHART);
        assertParseSuccess(parser,
            DISPLAY_INDICATOR_DESC_GENDER_BREAKDOWN
                + DISPLAY_FORMAT_DESC_LINECHART,
            new DisplayCommand(expectedDisplayIndicator, expectedDisplayFormat));

        expectedDisplayIndicator = new DisplayIndicator(POLICY_POPULARITY_BREAKDOWN);
        expectedDisplayFormat = new DisplayFormat(PIECHART);
        assertParseSuccess(parser,
            DISPLAY_INDICATOR_DESC_POLICY_POPULARITY_BREAKDOWN
                + DISPLAY_FORMAT_DESC_PIECHART,
            new DisplayCommand(expectedDisplayIndicator, expectedDisplayFormat));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayCommand.MESSAGE_USAGE);

        // missing indicator prefix
        assertParseFailure(parser,
            AGE_GROUP_BREAKDOWN
                + " "
                + DISPLAY_FORMAT_DESC_BARCHART,
            expectedMessage);

        // missing format prefix
        assertParseFailure(parser,
            DISPLAY_INDICATOR_DESC_GENDER_BREAKDOWN
                + PIECHART,
            expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AGE_GROUP_BREAKDOWN + " " + PIECHART,
            expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid indicator
        assertParseFailure(parser,
            INVALID_DISPLAY_INDICATOR_DESC + DISPLAY_FORMAT_DESC_LINECHART,
            DisplayIndicator.getMessageConstraints());

        // invalid format
        assertParseFailure(parser,
            DISPLAY_INDICATOR_DESC_POLICY_POPULARITY_BREAKDOWN + INVALID_DISPLAY_FORMAT_DESC,
            DisplayFormat.getMessageConstraints());
    }
}
