package seedu.address.logic.parser.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.gui.CollapseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.semester.SemesterName;

class CollapseCommandParserTest {
    private static final String NON_EXISTENT_SEMESTER_NAME = "notexistingsemestername";

    @Test
    public void parse_nullSemesterName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CollapseCommandParser().parse(null));
    }

    @Test
    public void parse_invalidSemesterName_throwsParseException() {
        assertThrows(ParseException.class, () -> new CollapseCommandParser().parse(NON_EXISTENT_SEMESTER_NAME));
        assertThrows(ParseException.class, () -> new CollapseCommandParser().parse(""));
    }

    @Test
    public void parse_helpWithCommand_success() throws ParseException {
        CollapseCommand expectedCollapseCommand = new CollapseCommand(SemesterName.Y1S1);
        assertEquals(new CollapseCommandParser().parse(SemesterName.Y1S1.name()), expectedCollapseCommand);

        expectedCollapseCommand = new CollapseCommand(SemesterName.Y1ST1);
        assertEquals(new CollapseCommandParser().parse(SemesterName.Y1ST1.name()), expectedCollapseCommand);

        expectedCollapseCommand = new CollapseCommand(SemesterName.Y4S1);
        assertEquals(new CollapseCommandParser().parse(SemesterName.Y4S1.name()), expectedCollapseCommand);

        expectedCollapseCommand = new CollapseCommand(SemesterName.Y5S2);
        assertEquals(new CollapseCommandParser().parse(SemesterName.Y5S2.name()), expectedCollapseCommand);
    }
}
