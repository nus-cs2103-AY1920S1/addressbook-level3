package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CardBook;
import seedu.address.model.FileBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PasswordBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.file.EncryptedFile;
import seedu.address.testutil.EncryptedFileBuilder;
import seedu.address.testutil.TestUtil;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class EncryptFileCommandIntegrationTest {

    private static final String FILENAME = "Test File.txt";
    private static final String ENCRYPTED_FILENAME = "[LOCKED] Test File.txt";
    private static final String PASSWORD = "password1";

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
        try {
            Files.write(TestUtil.getFilePathInSandboxFolder(FILENAME),
                    "This is test file content.".getBytes());
        } catch (IOException e) {
            throw new AssertionError("Error during testing.");
        }
    }

    @Test
    public void execute_newFile_success() {
        EncryptedFile validFile = new EncryptedFileBuilder().build();

        Model expectedModel = new ModelManager(getTypicalAddressBook(), model.getFileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
        expectedModel.addFile(validFile);

        assertCommandSuccess(new EncryptFileCommand(validFile, PASSWORD), model,
                String.format(EncryptFileCommand.MESSAGE_SUCCESS, validFile), expectedModel);
    }

    @Test
    public void execute_duplicateFile_throwsCommandException() {
        EncryptedFile validFile = new EncryptedFileBuilder().build();
        try {
            new EncryptFileCommand(validFile, PASSWORD).execute(model);
        } catch (CommandException e) {
            throw new AssertionError("Error during testing.");
        }
        EncryptedFile fileInList = model.getFileBook().getFileList().get(0);
        assertCommandFailure(new EncryptFileCommand(fileInList, PASSWORD), model,
                EncryptFileCommand.MESSAGE_DUPLICATE_FILE);
    }

    @AfterEach
    public void cleanUp() {
        try {
            Files.deleteIfExists(TestUtil.getFilePathInSandboxFolder(FILENAME));
            Files.deleteIfExists(TestUtil.getFilePathInSandboxFolder(ENCRYPTED_FILENAME));
        } catch (IOException e) {
            throw new AssertionError("Error during testing.");
        }
    }

}
