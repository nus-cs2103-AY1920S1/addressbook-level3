package seedu.address.logic.parser.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.gui.ExpandCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

class ExpandCommandParserTest {
    private static final String NON_EXISTENT_SEMESTER_NAME = "notexistingsemestername";

    @Test
    public void parse_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpandCommandParser().parse(null));
    }

    @Test
    public void parse_invalidSemesterName_throwsParseException() {
        assertThrows(ParseException.class, () -> new ExpandCommandParser().parse(NON_EXISTENT_SEMESTER_NAME));
        assertThrows(ParseException.class, () -> new ExpandCommandParser().parse(""));
    }

    @Test
    public void parse_helpWithCommand_success() throws ParseException {
        ExpandCommand expectedExpandCommand = new ExpandCommand(SemesterName.Y1S1);
        assertEquals(new ExpandCommandParser().parse(SemesterName.Y1S1.name()), expectedExpandCommand);

        expectedExpandCommand = new ExpandCommand(SemesterName.Y1ST1);
        assertEquals(new ExpandCommandParser().parse(SemesterName.Y1ST1.name()), expectedExpandCommand);

        expectedExpandCommand = new ExpandCommand(SemesterName.Y4S1);
        assertEquals(new ExpandCommandParser().parse(SemesterName.Y4S1.name()), expectedExpandCommand);

        expectedExpandCommand = new ExpandCommand(SemesterName.Y5S2);
        assertEquals(new ExpandCommandParser().parse(SemesterName.Y5S2.name()), expectedExpandCommand);
    }
}
