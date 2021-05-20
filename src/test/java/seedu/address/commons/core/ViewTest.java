package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;

//@@author {lawncegoh}
public class ViewTest {

    @Test
    public void viewCheck_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.checkView(null));
    }

    @Test
    public void invalidView_ifBlank() {

        //blank View
        assertFalse(ParserUtil.checkView(""));
        assertFalse(ParserUtil.checkView("   "));
    }

    @Test
    public void invalidValidView_withUppercase_parseSuccess() {
        assertFalse(ParserUtil.checkView("Contacts"));
        assertFalse(ParserUtil.checkView("CONTACTS"));
        assertFalse(ParserUtil.checkView("Incomes"));
        assertFalse(ParserUtil.checkView("INCOMES"));
        assertFalse(ParserUtil.checkView("Claims"));
        assertFalse(ParserUtil.checkView("CLAIMS"));
    }

    @Test
    public void invalidView_withWhiteSpace_parseSuccess() {
        assertFalse(ParserUtil.checkView("contacts        "));
        assertFalse(ParserUtil.checkView("incomes       "));
        assertFalse(ParserUtil.checkView("claims  "));
        assertFalse(ParserUtil.checkView("     contacts"));
        assertFalse(ParserUtil.checkView("     incomes"));
        assertFalse(ParserUtil.checkView("      claims"));
    }

    @Test
    public void validViews_parseSuccess() {
        assertTrue(ParserUtil.checkView("contacts"));
        assertTrue(ParserUtil.checkView("claims"));
        assertTrue(ParserUtil.checkView("incomes"));
    }
}
