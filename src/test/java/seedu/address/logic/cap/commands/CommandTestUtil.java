package seedu.address.logic.cap.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_CREDIT;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.Model;
import seedu.address.model.common.Module;



/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_MODULE_CODE_CS2103 = "CS2103";
    public static final String VALID_MODULE_CODE_CS2100 = "CS2100";
    public static final String VALID_TITLE_CS2103 = "Software Engineering";
    public static final String VALID_TITLE_CS2100 = "Computer Organisation";
    public static final String VALID_SEMESTER_CS2103 = "1920S1";
    public static final String VALID_SEMESTER_CS2100 = "1920S1";
    public static final int VALID_CREDIT_CS2103 = 4;
    public static final int VALID_CREDIT_CS2100 = 4;
    public static final String VALID_GRADE_CS2103 = "A";
    public static final String VALID_GRADE_CS2100 = "A";

    public static final String MODULE_CODE_DESC_CS2103 = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS2103;
    public static final String MODULE_CODE_DESC_CS2100 = " " + PREFIX_MODULE_CODE + VALID_MODULE_CODE_CS2100;
    public static final String TITLE_DESC_CS2103 = " " + PREFIX_TITLE + VALID_TITLE_CS2103;
    public static final String TITLE_DESC_CS2100 = " " + PREFIX_TITLE + VALID_TITLE_CS2100;
    public static final String SEMESTER_DESC_CS2103 = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2103;
    public static final String SEMESTER_DESC_CS2100 = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2100;
    public static final String GRADE_DESC_CS2103 = " " + PREFIX_GRADE + VALID_GRADE_CS2103;
    public static final String GRADE_DESC_CS2100 = " " + PREFIX_GRADE + VALID_GRADE_CS2100;
    public static final String CREDIT_DESC_CS2103 = " " + PREFIX_CREDIT + VALID_CREDIT_CS2103;
    public static final String CREDIT_DESC_CS2100 = " " + PREFIX_CREDIT + VALID_CREDIT_CS2100;

    public static final String INVALID_MODULE_CODE_DESC = " " + PREFIX_MODULE_CODE + "James&";
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "911a";
    public static final String INVALID_SEMESTER_DESC = " " + PREFIX_SEMESTER + "bob!yahoo";
    public static final String INVALID_CREDIT_DESC = " " + PREFIX_CREDIT;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    //Student names
    public static final String VALID_MODULE_TITLE = "Software Engineering";

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
        CapLog expectedAddressBook = new CapLog(actualModel.getCapLog());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getCapLog());
        assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
    }

}
