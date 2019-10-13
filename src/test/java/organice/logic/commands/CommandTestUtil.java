package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;
import static organice.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import organice.commons.core.index.Index;
import organice.logic.commands.exceptions.CommandException;
import organice.model.AddressBook;
import organice.model.Model;
import organice.model.person.NameContainsKeywordsPredicate;
import organice.model.person.Person;
import organice.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_IRENE = "Irene";
    public static final String VALID_NAME_JOHN = "John";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";

    public static final String VALID_PHONE_IRENE = "85355255";
    public static final String VALID_PHONE_JOHN = "81230942";
    public static final String VALID_NRIC_AMY = "S1111111A";
    public static final String VALID_NRIC_BOB = "G2222222B";
    public static final String VALID_NRIC_IRENE = "S1111111A";
    public static final String VALID_NRIC_JOHN = "T1312123P";
    public static final String VALID_TYPE_AMY = "doctor";
    public static final String VALID_TYPE_BOB = "patient";
    public static final String VALID_TYPE_IRENE = "patient";
    public static final String VALID_TYPE_JOHN = "donor";
    public static final String VALID_AGE_IRENE = "21";
    public static final String VALID_AGE_BOB = "21";
    public static final String VALID_AGE_JOHN = "60";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String TYPE_DESC_AMY = " " + PREFIX_TYPE + VALID_TYPE_AMY;
    public static final String TYPE_DESC_BOB = " " + PREFIX_TYPE + VALID_TYPE_BOB;
    public static final String AGE_DESC_BOB = " " + PREFIX_AGE + VALID_AGE_BOB;
    public static final String NAME_DESC_IRENE = " " + PREFIX_NAME + VALID_NAME_IRENE;
    public static final String PHONE_DESC_IRENE = " " + PREFIX_PHONE + VALID_PHONE_IRENE;
    public static final String NRIC_DESC_IRENE = " " + PREFIX_NRIC + VALID_NRIC_IRENE;
    public static final String TYPE_DESC_IRENE = " " + PREFIX_TYPE + VALID_TYPE_IRENE;
    public static final String AGE_DESC_IRENE = " " + PREFIX_AGE + VALID_AGE_IRENE;
    public static final String NAME_DESC_JOHN = " " + PREFIX_NAME + VALID_NAME_JOHN;
    public static final String PHONE_DESC_JOHN = " " + PREFIX_PHONE + VALID_PHONE_JOHN;
    public static final String NRIC_DESC_JOHN = " " + PREFIX_NRIC + VALID_NRIC_JOHN;
    public static final String TYPE_DESC_JOHN = " " + PREFIX_TYPE + VALID_TYPE_JOHN;
    public static final String AGE_DESC_JOHN = " " + PREFIX_AGE + VALID_AGE_JOHN;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "G123A"; // need exactly 7 numbers in nrics
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "student"; // only allow 'patient','donor',
    // 'doctor'
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + "-9";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withType("doctor").withNric(VALID_NRIC_AMY)
                .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withType("patient").withNric(VALID_NRIC_BOB)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(actualModel, expectedModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
