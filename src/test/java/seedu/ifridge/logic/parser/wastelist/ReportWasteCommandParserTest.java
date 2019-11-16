package seedu.ifridge.logic.parser.wastelist;

import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.END_MONTH_EMPTY;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.END_MONTH_INVALID;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.END_MONTH_OCT_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.START_MONTH_EMPTY;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.START_MONTH_INVALID;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.START_MONTH_MAR_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_CURRENT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_CURRENT_MONTH_LAST_YEAR;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_MAR2019;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_MAR2020;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_OCT2018;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_OCT2019;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ifridge.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ifridge.model.waste.WasteMonth.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.wastelist.ReportWasteCommand;

public class ReportWasteCommandParserTest {

    private ReportWasteCommandParser parser = new ReportWasteCommandParser();

    @Test
    public void parse_eitherMonthInvalid_failure() {
        // Start month invalid, end month valid
        assertParseFailure(parser, START_MONTH_INVALID + END_MONTH_OCT_RELAXED, MESSAGE_CONSTRAINTS);
        // Start month valid, end month invalid
        assertParseFailure(parser, START_MONTH_MAR_RELAXED + END_MONTH_INVALID, MESSAGE_CONSTRAINTS);
        // Start month invalid, end month not given
        assertParseFailure(parser, START_MONTH_INVALID + END_MONTH_EMPTY, MESSAGE_CONSTRAINTS);
        // Start month not given, end month invalid
        assertParseFailure(parser, START_MONTH_EMPTY + END_MONTH_INVALID, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_startAndEndBothGivenValid_success() {
        assertParseSuccess(parser, START_MONTH_MAR_RELAXED + END_MONTH_OCT_RELAXED,
                new ReportWasteCommand(WASTE_MONTH_MAR2019, WASTE_MONTH_OCT2019, true, true));
    }

    @Test
    public void parse_startGivenEndNotGiven_success() {
        assertParseSuccess(parser, START_MONTH_MAR_RELAXED,
                new ReportWasteCommand(WASTE_MONTH_MAR2019, WASTE_MONTH_MAR2020, true, false));
    }

    @Test
    public void parse_startNotGivenEndGiven_success() {
        assertParseSuccess(parser, END_MONTH_OCT_RELAXED,
                new ReportWasteCommand(WASTE_MONTH_OCT2018, WASTE_MONTH_OCT2019, false, true));
    }

    @Test
    public void parse_bothNotGiven_success() {
        assertParseSuccess(parser, START_MONTH_EMPTY + END_MONTH_EMPTY,
                new ReportWasteCommand(WASTE_MONTH_CURRENT_MONTH_LAST_YEAR, WASTE_MONTH_CURRENT_MONTH, false, false));
    }

}
