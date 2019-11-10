package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_JOINED_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.DATE_OF_BIRTH_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.DESIGNATION_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYMENT_STATUS_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_NUMBER_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_JOINED_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESIGNATION_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYMENT_STATUS_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_XENIA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_NUMBER_ZACH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WORKER_FLAG;
import static seedu.address.testutil.TypicalWorkers.CLARA;
import static seedu.address.testutil.TypicalWorkers.KEYWORD_MATCHING_ZACH;
import static seedu.address.testutil.TypicalWorkers.XENIA;
import static seedu.address.testutil.TypicalWorkers.ZACH;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.UniqueIdentificationNumberMaps;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;
import seedu.address.testutil.WorkerBuilder;
import seedu.address.testutil.WorkerUtil;

//@@author shaoyi1997-reused
//Reused from SE-EDU Address Book Level 4
public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws CommandException {
        Model model = getModel();
        ClearCommand clearCommand = new ClearCommand();
        clearCommand.execute(model);

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a worker to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Worker toAdd = ZACH;
        UniqueIdentificationNumberMaps.clearAllEntries();
        String command = "   " + AddCommand.COMMAND_WORD + "  " + VALID_WORKER_FLAG + "  " + NAME_DESC_ZACH + "  "
                + SEX_DESC_ZACH + " " + PHONE_NUMBER_DESC_ZACH + "   " + DATE_JOINED_DESC_ZACH
                        + "   " + DATE_OF_BIRTH_DESC_ZACH + " " + DESIGNATION_DESC_ZACH + " "
                                + EMPLOYMENT_STATUS_DESC_ZACH + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a worker with all fields same as another worker in the address book except name -> added */
        toAdd = new WorkerBuilder(ZACH).withName(VALID_NAME_XENIA).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + NAME_DESC_XENIA + SEX_DESC_ZACH + PHONE_NUMBER_DESC_ZACH
                + DATE_JOINED_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH + DESIGNATION_DESC_ZACH + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandSuccess(command, toAdd);

        /* Case: add a worker with all fields same as another worker in the address book except dateJoined
         * -> added
         */
        toAdd = new WorkerBuilder(ZACH).withDateJoined(VALID_DATE_JOINED_XENIA)
                .build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = WorkerUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllEntities();
        UniqueIdentificationNumberMaps.clearAllEntries();
        assertCommandSuccess(CLARA);

        /* Case: add a worker, command with parameters in random order -> added */
        toAdd = ZACH;
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + SEX_DESC_ZACH + NAME_DESC_ZACH
                + DATE_JOINED_DESC_ZACH + PHONE_NUMBER_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH
                        + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandSuccess(command, toAdd);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the worker list before adding -> added */
        showWorkersWithName(KEYWORD_MATCHING_ZACH);
        UniqueIdentificationNumberMaps.clearAllEntries();
        assertCommandSuccess(XENIA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate worker -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = WorkerUtil.getAddCommand(XENIA);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ENTITY);

        /* Case: add a duplicate worker except with different phone -> rejected */
        toAdd = new WorkerBuilder(XENIA).withPhone(VALID_PHONE_NUMBER_ZACH).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = WorkerUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ENTITY);

        /* Case: add a duplicate worker except with different email -> rejected */
        toAdd = new WorkerBuilder(XENIA).withPhone(VALID_DESIGNATION_ZACH).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = WorkerUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ENTITY);

        /* Case: add a duplicate worker except with different employment status -> rejected */
        toAdd = new WorkerBuilder(XENIA).withPhone(VALID_EMPLOYMENT_STATUS_ZACH).build();
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = WorkerUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_ENTITY);

        /* Case: missing name -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + SEX_DESC_ZACH
                + DATE_JOINED_DESC_ZACH + PHONE_NUMBER_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH
                        + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing sex -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + NAME_DESC_ZACH
                + DATE_JOINED_DESC_ZACH + PHONE_NUMBER_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH
                        + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + SEX_DESC_ZACH + NAME_DESC_ZACH
                + PHONE_NUMBER_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = "adds " + WorkerUtil.getWorkerDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + SEX_DESC_ZACH
                + INVALID_NAME_DESC + DATE_JOINED_DESC_ZACH + PHONE_NUMBER_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH
                        + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandFailure(command, Name.MESSAGE_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + SEX_DESC_ZACH + NAME_DESC_ZACH
                + DATE_JOINED_DESC_ZACH + INVALID_PHONE_DESC + DATE_OF_BIRTH_DESC_ZACH
                        + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandFailure(command, PhoneNumber.MESSAGE_CONSTRAINTS);

        /* Case: invalid sex -> reject */
        UniqueIdentificationNumberMaps.clearAllEntries();
        command = AddCommand.COMMAND_WORD + VALID_WORKER_FLAG + DESIGNATION_DESC_ZACH + INVALID_SEX_DESC
                + NAME_DESC_ZACH + DATE_JOINED_DESC_ZACH + PHONE_NUMBER_DESC_ZACH + DATE_OF_BIRTH_DESC_ZACH
                        + EMPLOYMENT_STATUS_DESC_ZACH;
        assertCommandFailure(command, Sex.MESSAGE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code WorkerListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Worker toAdd) {
        assertCommandSuccess(WorkerUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Worker)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Worker)
     */
    private void assertCommandSuccess(String command, Worker toAdd) {
        Model expectedModel = getModel();
        expectedModel.addEntity(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, "worker", toAdd.getIdNum());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Worker)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code WorkerListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Worker)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code WorkerListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
//@@author
