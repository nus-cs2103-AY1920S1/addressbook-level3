package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NON_UNIQUE_SERIAL_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOOK_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.AddCommandParser;
import seedu.address.model.Catalog;
import seedu.address.model.SerialNumberGenerator;

public class SerialNumberTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SerialNumber(null));
    }

    @Test
    public void constructor_invalidSerialNumber_throwsIllegalArgumentException() {
        String invalidSerialNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new SerialNumber(invalidSerialNumber));
    }

    @Test
    public void isValidSerialNumber() {
        // null phone number
        assertThrows(NullPointerException.class, () -> SerialNumber.isValidSerialNumber(null));

        // invalid phone numbers
        assertFalse(SerialNumber.isValidSerialNumber("")); // empty string
        assertFalse(SerialNumber.isValidSerialNumber(" ")); // spaces only
        assertFalse(SerialNumber.isValidSerialNumber("B91")); // less than 4 numbers
        assertFalse(SerialNumber.isValidSerialNumber("C0001")); // different prefix
        assertFalse(SerialNumber.isValidSerialNumber("phone")); // non-numeric
        assertFalse(SerialNumber.isValidSerialNumber("9011p041")); // alphabets within digits
        assertFalse(SerialNumber.isValidSerialNumber("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(SerialNumber.isValidSerialNumber("B0911")); // exactly 4 numbers
        assertTrue(SerialNumber.isValidSerialNumber("B0001")); // smallest serial number
        assertTrue(SerialNumber.isValidSerialNumber("B9999")); // largest serial number
    }

    @Test
    public void addSerialNumber_notUnique_assertParseFailure() {
        Catalog catalog = getTypicalCatalog();
        SerialNumberGenerator.setCatalog(catalog);

        assertParseFailure(parser, TITLE_DESC_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_1 + AUTHOR_DESC_BOOK_2
                + GENRE_DESC_ACTION + GENRE_DESC_FICTION, MESSAGE_NON_UNIQUE_SERIAL_NUMBER);

    }
}
