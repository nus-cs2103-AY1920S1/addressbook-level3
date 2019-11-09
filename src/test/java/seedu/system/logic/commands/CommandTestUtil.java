package seedu.system.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.system.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.system.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.system.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.system.logic.parser.CliSyntax.PREFIX_RANK_METHOD;
import static seedu.system.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.system.commons.core.index.Index;
import seedu.system.logic.commands.exceptions.CommandException;
import seedu.system.logic.commands.outofsession.EditPersonCommand;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.person.NameContainsKeywordsPredicate;
import seedu.system.model.person.Person;
import seedu.system.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DOB_AMY = "11/02/1995";
    public static final String VALID_DOB_BOB = "13/01/1995";
    public static final String VALID_GENDER_AMY = "female";
    public static final String VALID_GENDER_BOB = "male";
    public static final String VALID_RANK_METHOD = "deadlift";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DOB_DESC_AMY = " " + PREFIX_DOB + VALID_DOB_AMY;
    public static final String DOB_DESC_BOB = " " + PREFIX_DOB + VALID_DOB_BOB;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String DEADLIFT_RANK_METHOD_DESC = " " + PREFIX_RANK_METHOD + VALID_RANK_METHOD;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DOB_DESC = " " + PREFIX_DOB + "911a"; // does not follow DD/MM/YYYY format
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "shemale"; // only male or female allowed
    public static final String INVALID_RANK_METHOD_DESC = " " + PREFIX_RANK_METHOD + "shoulder press";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDateOfBirth(VALID_DOB_AMY).withGender(VALID_GENDER_AMY).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDateOfBirth(VALID_DOB_BOB).withGender(VALID_GENDER_BOB).build();
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
            assertEquals(expectedModel, actualModel);
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
        Data expectedAddressBook = new Data(actualModel.getPersons());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getPersons());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().toString().split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
