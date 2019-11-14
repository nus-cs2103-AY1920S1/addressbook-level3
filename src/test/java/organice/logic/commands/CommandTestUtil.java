package organice.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static organice.logic.parser.CliSyntax.PREFIX_AGE;
import static organice.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_DOCTOR_IN_CHARGE;
import static organice.logic.parser.CliSyntax.PREFIX_NAME;
import static organice.logic.parser.CliSyntax.PREFIX_NRIC;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN;
import static organice.logic.parser.CliSyntax.PREFIX_ORGAN_EXPIRY_DATE;
import static organice.logic.parser.CliSyntax.PREFIX_PHONE;
import static organice.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static organice.logic.parser.CliSyntax.PREFIX_TISSUE_TYPE;
import static organice.logic.parser.CliSyntax.PREFIX_TYPE;
import static organice.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import organice.commons.core.index.Index;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.ArgumentMultimap;
import organice.logic.parser.ArgumentTokenizer;
import organice.logic.parser.MatchCommandParser;
import organice.logic.parser.exceptions.ParseException;
import organice.model.AddressBook;
import organice.model.Model;
import organice.model.person.Person;
import organice.model.person.PersonContainsPrefixesPredicate;
import organice.model.person.Status;
import organice.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_DOCTOR_AMY = "Amy Bee";
    public static final String VALID_NAME_DONOR_JOHN = "John";
    public static final String VALID_NAME_PATIENT_BOB = "Bob Choo";
    public static final String VALID_NAME_PATIENT_IRENE = "Irene";

    public static final String VALID_PHONE_DOCTOR_AMY = "11111111";
    public static final String VALID_PHONE_DONOR_JOHN = "81230942";
    public static final String VALID_PHONE_PATIENT_BOB = "22222222";
    public static final String VALID_PHONE_PATIENT_IRENE = "85355255";

    public static final String VALID_NRIC_DOCTOR_AMY = "S8078981E";
    public static final String VALID_NRIC_DONOR_JOHN = "S6488870F";
    public static final String VALID_NRIC_PATIENT_BOB = "S7044112H";
    public static final String VALID_NRIC_PATIENT_IRENE = "S9605440H";
    public static final String VALID_NRIC_DONOR_IRENE_DONOR = "S9155102J";

    public static final String VALID_TYPE_DOCTOR_AMY = "doctor";
    public static final String VALID_TYPE_DONOR_JOHN = "donor";
    public static final String VALID_TYPE_PATIENT_BOB = "patient";
    public static final String VALID_TYPE_PATIENT_IRENE = "patient";

    public static final String VALID_AGE_DONOR_JOHN = "60";
    public static final String VALID_AGE_PATIENT_BOB = "20";
    public static final String VALID_AGE_PATIENT_IRENE = "21";

    public static final String VALID_PRIORITY_PATIENT_IRENE = "high";
    public static final String VALID_PRIORITY_PATIENT_BOB = "medium";

    public static final String VALID_BLOOD_TYPE_DONOR_JOHN = "A+";
    public static final String VALID_BLOOD_TYPE_PATIENT_BOB = "B-";
    public static final String VALID_BLOOD_TYPE_PATIENT_IRENE = "O+";

    public static final String VALID_TISSUE_TYPE_DONOR_JOHN = "1,2,3,4,5,6";
    public static final String VALID_TISSUE_TYPE_PATIENT_BOB = "7,8,9,10,11,12";
    public static final String VALID_TISSUE_TYPE_PATIENT_IRENE = "1,4,7,10,11,12";

    public static final String COMPATIBLE_TISSUE_TYPE_IRENE = "1,4,5,6,10,11";
    public static final String INCOMPATIBLE_TISSUE_TYPE_IRENE = "9,1,2,3,5,6";

    public static final String VALID_ORGAN_DONOR_JOHN = "kidney";
    public static final String VALID_ORGAN_PATIENT_BOB = "kidney";
    public static final String VALID_ORGAN_PATIENT_IRENE = "kidney";

    public static final String VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN = "20-Jan-2020";
    public static final String VALID_ORGAN_EXPIRY_DATE_DONOR_JOHNY = "21-Jan-2020";

    public static final String VALID_DOCTOR_IN_CHARGE_PATIENT_BOB = "S8162183G";
    public static final String VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE = "F1289064T";

    public static final String VALID_STATUS_DONOR_JOHN = Status.STATUS_NOT_PROCESSING;
    public static final String VALID_STATUS_PATIENT_IRENE = Status.STATUS_NOT_PROCESSING;

    public static final String NAME_DESC_DOCTOR_AMY = " " + PREFIX_NAME + VALID_NAME_DOCTOR_AMY;
    public static final String NAME_DESC_DONOR_JOHN = " " + PREFIX_NAME + VALID_NAME_DONOR_JOHN;
    public static final String NAME_DESC_PATIENT_BOB = " " + PREFIX_NAME + VALID_NAME_PATIENT_BOB;
    public static final String NAME_DESC_PATIENT_IRENE = " " + PREFIX_NAME + VALID_NAME_PATIENT_IRENE;

    public static final String PHONE_DESC_DOCTOR_AMY = " " + PREFIX_PHONE + VALID_PHONE_DOCTOR_AMY;
    public static final String PHONE_DESC_DONOR_JOHN = " " + PREFIX_PHONE + VALID_PHONE_DONOR_JOHN;
    public static final String PHONE_DESC_PATIENT_BOB = " " + PREFIX_PHONE + VALID_PHONE_PATIENT_BOB;
    public static final String PHONE_DESC_PATIENT_IRENE = " " + PREFIX_PHONE + VALID_PHONE_PATIENT_IRENE;

    public static final String NRIC_DESC_DOCTOR_AMY = " " + PREFIX_NRIC + VALID_NRIC_DOCTOR_AMY;
    public static final String NRIC_DESC_DONOR_JOHN = " " + PREFIX_NRIC + VALID_NRIC_DONOR_JOHN;
    public static final String NRIC_DESC_PATIENT_BOB = " " + PREFIX_NRIC + VALID_NRIC_PATIENT_BOB;
    public static final String NRIC_DESC_PATIENT_IRENE = " " + PREFIX_NRIC + VALID_NRIC_PATIENT_IRENE;


    public static final String TYPE_DESC_DOCTOR_AMY = " " + PREFIX_TYPE + VALID_TYPE_DOCTOR_AMY;
    public static final String TYPE_DESC_DONOR_JOHN = " " + PREFIX_TYPE + VALID_TYPE_DONOR_JOHN;
    public static final String TYPE_DESC_PATIENT_BOB = " " + PREFIX_TYPE + VALID_TYPE_PATIENT_BOB;
    public static final String TYPE_DESC_PATIENT_IRENE = " " + PREFIX_TYPE + VALID_TYPE_PATIENT_IRENE;

    public static final String AGE_DESC_DONOR_JOHN = " " + PREFIX_AGE + VALID_AGE_DONOR_JOHN;
    public static final String AGE_DESC_PATIENT_BOB = " " + PREFIX_AGE + VALID_AGE_PATIENT_BOB;
    public static final String AGE_DESC_PATIENT_IRENE = " " + PREFIX_AGE + VALID_AGE_PATIENT_IRENE;

    public static final String PRIORITY_DESC_PATIENT_IRENE = " " + PREFIX_PRIORITY + VALID_PRIORITY_PATIENT_IRENE;
    public static final String PRIORITY_DESC_PATIENT_BOB = " " + PREFIX_PRIORITY + VALID_PRIORITY_PATIENT_BOB;

    public static final String BLOOD_TYPE_DESC_DONOR_JOHN = " " + PREFIX_BLOOD_TYPE + VALID_BLOOD_TYPE_DONOR_JOHN;
    public static final String BLOOD_TYPE_DESC_PATIENT_BOB = " " + PREFIX_BLOOD_TYPE + VALID_BLOOD_TYPE_PATIENT_BOB;
    public static final String BLOOD_TYPE_DESC_PATIENT_IRENE = " " + PREFIX_BLOOD_TYPE + VALID_BLOOD_TYPE_PATIENT_IRENE;

    public static final String TISSUE_TYPE_DESC_DONOR_JOHN = " " + PREFIX_TISSUE_TYPE + VALID_TISSUE_TYPE_DONOR_JOHN;
    public static final String TISSUE_TYPE_DESC_PATIENT_BOB = " " + PREFIX_TISSUE_TYPE
            + VALID_TISSUE_TYPE_PATIENT_BOB;
    public static final String TISSUE_TYPE_DESC_PATIENT_IRENE = " " + PREFIX_TISSUE_TYPE
            + VALID_TISSUE_TYPE_PATIENT_IRENE;

    public static final String ORGAN_DESC_DONOR_JOHN = " " + PREFIX_ORGAN + VALID_ORGAN_DONOR_JOHN;
    public static final String ORGAN_DESC_PATIENT_BOB = " " + PREFIX_ORGAN + VALID_ORGAN_PATIENT_BOB;
    public static final String ORGAN_DESC_PATIENT_IRENE = " " + PREFIX_ORGAN + VALID_ORGAN_PATIENT_IRENE;

    public static final String ORGAN_EXPIRY_DATE_DESC_DONOR_JOHN = " " + PREFIX_ORGAN_EXPIRY_DATE
            + VALID_ORGAN_EXPIRY_DATE_DONOR_JOHN;
    public static final String ORGAN_EXPIRY_DATE_DESC_DONOR_JOHNY = " " + PREFIX_ORGAN_EXPIRY_DATE
            + VALID_ORGAN_EXPIRY_DATE_DONOR_JOHNY;

    public static final String DOCTOR_IN_CHARGE_DESC_PATIENT_BOB = " " + PREFIX_DOCTOR_IN_CHARGE
            + VALID_DOCTOR_IN_CHARGE_PATIENT_BOB;
    public static final String DOCTOR_IN_CHARGE_DESC_PATIENT_IRENE = " " + PREFIX_DOCTOR_IN_CHARGE
            + VALID_DOCTOR_IN_CHARGE_PATIENT_IRENE;

    public static final String STATUS_DESC_DONOR_JOHN = VALID_STATUS_DONOR_JOHN;
    public static final String STATUS_DESC_PATIENT_IRENE = VALID_STATUS_PATIENT_IRENE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "G123A"; // need exactly 7 numbers in nrics
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "student"; // only allow 'patient','donor',
    // 'doctor'
    public static final String INVALID_AGE_DESC = " " + PREFIX_AGE + "-9";
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "med";
    public static final String INVALID_BLOOD_TYPE_DESC = " " + PREFIX_BLOOD_TYPE + "ABC";
    public static final String INVALID_TISSUE_TYPE_DESC = " " + PREFIX_TISSUE_TYPE + "1,2,12,13,14,4";
    public static final String INVALID_ORGAN_DESC = " " + PREFIX_ORGAN + "heart";
    public static final String INVALID_DOCTOR_IN_CHARGE_DESC = " " + PREFIX_DOCTOR_IN_CHARGE + "S123B";
    public static final String INVALID_ORGAN_EXPIRY_DATE_DESC = " " + PREFIX_ORGAN_EXPIRY_DATE + "20.01.2020";
    public static final String INVALID_STATUS_DESC = "procesin"; //typo spelling mistake

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String VALID_MATCHCOMMAND_ALL = " " + PREFIX_NRIC + MatchCommandParser.ALL;
    public static final String INVALID_MATCHCOMMAND_ALL = " " + PREFIX_NRIC + "al";

    public static final EditCommand.EditPersonDescriptor DESC_DOCTOR_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_PATIENT_BOB;

    static {
        DESC_DOCTOR_AMY = new EditPersonDescriptorBuilder().withType("doctor").withNric(VALID_NRIC_DOCTOR_AMY)
                .withName(VALID_NAME_DOCTOR_AMY).withPhone(VALID_PHONE_DOCTOR_AMY).build();
        DESC_PATIENT_BOB = new EditPersonDescriptorBuilder().withType("patient").withNric(VALID_NRIC_PATIENT_BOB)
                .withName(VALID_NAME_PATIENT_BOB).withPhone(VALID_PHONE_PATIENT_BOB).build();
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
        } catch (CommandException | ParseException ce) {
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
        final ArgumentMultimap searchParams = ArgumentTokenizer
                .tokenize(ExactFindCommand.COMMAND_WORD + " n/" + person.getName().fullName, PREFIX_NAME);

        model.updateFilteredPersonList(new PersonContainsPrefixesPredicate(searchParams));
        assertEquals(1, model.getFilteredPersonList().size());
    }
}
