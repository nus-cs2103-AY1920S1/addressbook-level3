package seedu.address.cashier.logic;

import java.io.File;

import seedu.address.cashier.model.Model;
import seedu.address.cashier.storage.Storage;

public class LogicManagerTest {
    private File iFile;
    private File tFile;
    private Model model;
    private seedu.address.person.model.Model personModel;
    private Storage storage;
    private seedu.address.transaction.storage.Storage transactionStorage;
    private seedu.address.transaction.model.Model transactionModel;
    private seedu.address.inventory.model.Model inventoryModel;
    private Logic logic;
/*
    LogicManagerTest() {
        try {
            model = new ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
            personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            iFile = File.createTempFile("testing", "tempInventory.txt");
            tFile = File.createTempFile("testing", "tempTransaction.txt");
            storage = new StorageManager(iFile, tFile, personModel);

            model.getTransactionList();
            transactionStorage =
                    new seedu.address.transaction.storage.StorageManager(tFile, personModel);
            transactionModel =
                    new seedu.address.inventory.model.ModelManager(
                            inventoryStorage.getReimbursementFromFile(model.getTransactionList()));
            inventoryModel =
                    new seedu.address.inventory.model.ModelManager(
                            inventoryStorage.getReimbursementFromFile(model.getTransactionList()));
            logic =
                    new LogicManager(model, storage, personModel, transactionModel, inventoryModel);
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }

    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, NO_SUCH_COMMAND);
    }
    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 20";
        assertCommandException(deleteCommand, MESSAGE_NO_SUCH_TRANSACTION);
    }
    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = BackCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, "", model);
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredList().delete(0));
    }  */
    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
//    private void assertCommandSuccess(String inputCommand, String expectedMessage,
//                                      Model expectedModel) {
//        try {
//            CommandResult result = logic.execute(inputCommand);
//            assertEquals(expectedMessage, result.getFeedbackToUser());
//            assertEquals(expectedModel, model);
//        } catch (Exception e) {
//            throw new AssertionError("There should not be an error");
//        }
//
//    }
//
//    /**
//     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertParseException(String inputCommand, String expectedMessage) {
//        assertCommandFailure(inputCommand, Exception.class, expectedMessage);
//    }
//    /**
//     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
//     * @see #assertCommandFailure(String, Class, String, seedu.address.transaction.model.Model)
//     */
//    private void assertCommandException(String inputCommand, String expectedMessage) {
//        assertCommandFailure(inputCommand, Exception.class, expectedMessage);
//    }
//    /**
//     * Executes the command, confirms that the exception is thrown and that the result message is correct.
//     * @see #assertCommandFailure(String, Class, String, Model)
//     */
//    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
//                                      String expectedMessage) {
//        Model expectedModel = new seedu.address.transaction.model.ModelManager(model.getTransactionList());
//        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
//    }
//    /**
//     * Executes the command and confirms that
//     * - the {@code expectedException} is thrown <br>
//     * - the resulting error message is equal to {@code expectedMessage} <br>
//     * - the internal model manager state is the same as that in {@code expectedModel} <br>
//     * @see #assertCommandSuccess(String, String, Model)
//     */
//    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
//                                      String expectedMessage, Model expectedModel) {
//        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
//        assertEquals(expectedModel, model);
//    }


}
