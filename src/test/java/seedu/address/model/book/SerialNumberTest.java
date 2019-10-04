package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_ACTION;
import static seedu.address.logic.commands.CommandTestUtil.GENRE_DESC_FICTION;
import static seedu.address.logic.commands.CommandTestUtil.SERIAL_NUMBER_DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BOOK_2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBooks.BOOK_2;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalog;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SerialNumberGenerator;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.BookBuilder;

public class SerialNumberTest {

    private AddCommandParser parser = new AddCommandParser();
    private Model model = new ModelManager();

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
        assertFalse(SerialNumber.isValidSerialNumber("B91")); // less than 5 numbers
        assertFalse(SerialNumber.isValidSerialNumber("C00001")); // different prefix
        assertFalse(SerialNumber.isValidSerialNumber("phone")); // non-numeric
        assertFalse(SerialNumber.isValidSerialNumber("9011p041")); // alphabets within digits
        assertFalse(SerialNumber.isValidSerialNumber("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(SerialNumber.isValidSerialNumber("B00911")); // exactly 5 numbers
        assertTrue(SerialNumber.isValidSerialNumber("B00001")); // smallest serial number
        assertTrue(SerialNumber.isValidSerialNumber("B99099")); // largest serial number
    }

    @Test
    public void addSerialNumber_notUnique_assertCommandExceptionThrown() {
        Catalog catalog = getTypicalCatalog();
        SerialNumberGenerator.setCatalog(catalog);
        Model model = new ModelManager(getTypicalCatalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());

        //Serial number B0002 is already in typical catalog
        Book validBook2 = new BookBuilder(BOOK_2).build();
        AddCommand addCommand = new AddCommand(validBook2);
        assertCommandExceptionThrown(addCommand, model);
    }

    @Test
    public void addSerialNumber_unique_assertParseSuccess() {
        SerialNumberGenerator.setCatalog(new Catalog());

        Book expectedBook = new BookBuilder(BOOK_2).build();
        //Serial number B0002 of BOOK_2 is not in empty new catalog
        assertParseSuccess(parser, TITLE_DESC_BOOK_2 + SERIAL_NUMBER_DESC_BOOK_2 + AUTHOR_DESC_BOOK_2
                + GENRE_DESC_ACTION + GENRE_DESC_FICTION, new AddCommand(expectedBook));
    }

    @Test
    public void toString_correctStringRepresentation_assertTrue() {
        SerialNumberGenerator.setCatalog(new Catalog());
        assertTrue(SerialNumberGenerator.generateSerialNumber().toString().equals("B00001"));
    }

    @Test
    public void equals_sameSerialNumber_assertTrue() {
        SerialNumber sn1 = new SerialNumber("B00001");
        SerialNumber sn2 = new SerialNumber("B00001");
        assertTrue(sn1.equals(sn2));
    }

    @Test
    public void hashCode_sameSerialNumberSameHashCode_assertTrue() {
        SerialNumber sn1 = new SerialNumber("B00001");
        SerialNumber sn2 = new SerialNumber("B00001");
        assertTrue(sn1.hashCode() == sn2.hashCode());
    }

    /**
     * Asserts if CommandException is being thrown.
     *
     * @param command command to be executed.
     * @param model model to be tested on.
     */
    private void assertCommandExceptionThrown(AddCommand command, Model model) {
        try {
            command.execute(model);
            assertTrue(false);
        } catch (CommandException e) {
            assertTrue(true);
        }
    }
}
