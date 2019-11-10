package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;

//@@author {lawncegoh}
public class FilterTest {

    @Test
    public void filterCheck_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.checkFilter(null));
    }

    @Test
    public void invalidFilter_ifBlank_parseSuccess() {
        //blank View
        assertFalse(ParserUtil.checkFilter(""));
        assertFalse(ParserUtil.checkFilter("   "));
    }

    @Test
    public void invalidValidFilter_withUppercase_parseSuccess() {
        assertFalse(ParserUtil.checkFilter("NAME"));
        assertFalse(ParserUtil.checkFilter("Name"));
        assertFalse(ParserUtil.checkFilter("Date"));
        assertFalse(ParserUtil.checkFilter("DATE"));
        assertFalse(ParserUtil.checkFilter("STATUS"));
        assertFalse(ParserUtil.checkFilter("Status"));
    }

    @Test
    public void invalidView_withWhiteSpace_parseSuccess() {
        assertFalse(ParserUtil.checkFilter("name        "));
        assertFalse(ParserUtil.checkFilter("date       "));
        assertFalse(ParserUtil.checkFilter("status  "));
        assertFalse(ParserUtil.checkFilter("     name"));
        assertFalse(ParserUtil.checkFilter("     date"));
        assertFalse(ParserUtil.checkFilter("      status"));
    }

    @Test
    public void validViews_parseSuccess() {
        assertTrue(ParserUtil.checkFilter("name"));
        assertTrue(ParserUtil.checkFilter("date"));
        assertTrue(ParserUtil.checkFilter("status"));
    }
}
