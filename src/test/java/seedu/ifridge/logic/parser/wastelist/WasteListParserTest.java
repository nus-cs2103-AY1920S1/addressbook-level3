package seedu.ifridge.logic.parser.wastelist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.END_MONTH_OCT_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.MONTH_SEP_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.START_MONTH_MAR_RELAXED;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_MAR2019;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_OCT2019;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_SEPTEMBER;
import static seedu.ifridge.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.wastelist.FeedbackWasteCommand;
import seedu.ifridge.logic.commands.wastelist.ListWasteCommand;
import seedu.ifridge.logic.commands.wastelist.ReportWasteCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;

public class WasteListParserTest {

    private final WasteListParser parser = new WasteListParser();

    @Test
    public void parseCommand_feedback() throws ParseException {
        String command = FeedbackWasteCommand.COMMAND_WORD;
        FeedbackWasteCommand fwc = new FeedbackWasteCommand();
        assertEquals(fwc, parser.parseCommand(command));
    }

    @Test
    public void parseCommand_list() throws ParseException {
        String command = ListWasteCommand.COMMAND_WORD + MONTH_SEP_RELAXED;
        ListWasteCommand lwc = new ListWasteCommand(WASTE_MONTH_SEPTEMBER);
        assertEquals(lwc, parser.parseCommand(command));
    }

    @Test
    public void parseCommand_report() throws ParseException {
        String command = ReportWasteCommand.COMMAND_WORD + START_MONTH_MAR_RELAXED + END_MONTH_OCT_RELAXED;
        ReportWasteCommand lwc = new ReportWasteCommand(WASTE_MONTH_MAR2019, WASTE_MONTH_OCT2019, true, true);
        assertEquals(lwc, parser.parseCommand(command));
    }

    @Test
    public void parseCommand_unknown_throwsParseException() {
        String command = "unknown";
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(command));
    }

}
