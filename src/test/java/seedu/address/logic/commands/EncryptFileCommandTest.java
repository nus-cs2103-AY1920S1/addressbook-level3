package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.TargetFileExistException;
import seedu.address.commons.util.EncryptionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.file.EncryptedFile;
import seedu.address.testutil.EncryptedFileBuilder;
import seedu.address.testutil.TestUtil;

public class EncryptFileCommandTest {
    private static final String FILENAME = "Test File.txt";
    private static final String NON_EXIST_FILENAME = "Text File.txt";
    private static final String ENCRYPTED_FILENAME = "[LOCKED] Test File.txt";
    private static final String FOLDER_NAME = "Test Folder";
    private static final String PASSWORD = "password1";
    private static final String PASSWORD2 = "password2";

    @BeforeEach
    public void setUp() {
        try {
            Files.write(TestUtil.getFilePathInSandboxFolder(FILENAME),
                    "This is test file content.".getBytes());
        } catch (IOException e) {
            throw new AssertionError("Error during testing.");
        }
    }

    @Test
    public void constructor_nullFile_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EncryptFileCommand(null, PASSWORD));
    }

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        EncryptedFile validFile = new EncryptedFileBuilder().build();
        assertThrows(NullPointerException.class, () -> new EncryptFileCommand(validFile, null));
    }

    @Test
    public void execute_fileAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFileAdded modelStub = new ModelStubAcceptingFileAdded();
        EncryptedFile validFile = new EncryptedFileBuilder().build();

        CommandResult commandResult = new EncryptFileCommand(validFile, PASSWORD).execute(modelStub);

        assertEquals(String.format(EncryptFileCommand.MESSAGE_SUCCESS, validFile), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFile), modelStub.filesAdded);
    }

    @Test
    public void execute_duplicateFile_throwsCommandException() {
        EncryptedFile validFile = new EncryptedFileBuilder().build();
        EncryptFileCommand addCommand = new EncryptFileCommand(validFile, PASSWORD);
        ModelStub modelStub = new ModelStubWithEncryptedFile(validFile);

        assertThrows(CommandException.class,
                EncryptFileCommand.MESSAGE_DUPLICATE_FILE, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_fileNotExist_throwsCommandException() {
        ModelStubAcceptingFileAdded modelStub = new ModelStubAcceptingFileAdded();
        EncryptedFile invalidFile = new EncryptedFileBuilder().withFileName(NON_EXIST_FILENAME).build();
        EncryptFileCommand addCommand = new EncryptFileCommand(invalidFile, PASSWORD);

        assertThrows(CommandException.class,
                EncryptFileCommand.MESSAGE_FILE_NOT_FOUND, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_fileAlreadyEncrypted_throwsCommandException() {
        try {
            EncryptionUtil.encryptFile(
                    TestUtil.getFilePathInSandboxFolder(FILENAME).toString(),
                    TestUtil.getFilePathInSandboxFolder(ENCRYPTED_FILENAME).toString(),
                    PASSWORD
            );

            ModelStubAcceptingFileAdded modelStub = new ModelStubAcceptingFileAdded();
            EncryptedFile invalidFile = new EncryptedFileBuilder().withFileName(ENCRYPTED_FILENAME).build();
            EncryptFileCommand addCommand = new EncryptFileCommand(invalidFile, PASSWORD);

            assertThrows(CommandException.class,
                    EncryptFileCommand.MESSAGE_ENCRYPTED_FILE, () -> addCommand.execute(modelStub));

            EncryptionUtil.decryptFile(
                    TestUtil.getFilePathInSandboxFolder(ENCRYPTED_FILENAME).toString(),
                    TestUtil.getFilePathInSandboxFolder(FILENAME).toString(),
                    PASSWORD
            );
        } catch (IOException | TargetFileExistException | GeneralSecurityException e) {
            throw new AssertionError("Error during testing.");
        }
    }

    @Test
    public void execute_fileIsDirectory_throwsCommandException() {
        try {
            Files.createDirectories(TestUtil.getFilePathInSandboxFolder(FOLDER_NAME));

            ModelStubAcceptingFileAdded modelStub = new ModelStubAcceptingFileAdded();
            EncryptedFile invalidFile = new EncryptedFileBuilder().withFileName(FOLDER_NAME).build();
            EncryptFileCommand addCommand = new EncryptFileCommand(invalidFile, PASSWORD);

            assertThrows(CommandException.class,
                    EncryptFileCommand.MESSAGE_IS_DIRECTORY, () -> addCommand.execute(modelStub));

            Files.deleteIfExists(TestUtil.getFilePathInSandboxFolder(FOLDER_NAME));
        } catch (IOException e) {
            throw new AssertionError("Error during testing.");
        }
    }

    @Test
    public void execute_targetFileExists_throwsCommandException() {
        try {
            Files.write(TestUtil.getFilePathInSandboxFolder(ENCRYPTED_FILENAME), "Test".getBytes());

            ModelStubAcceptingFileAdded modelStub = new ModelStubAcceptingFileAdded();
            EncryptedFile validFile = new EncryptedFileBuilder().withFileName(FILENAME).build();
            EncryptFileCommand addCommand = new EncryptFileCommand(validFile, PASSWORD);

            assertThrows(CommandException.class,
                    String.format(EncryptFileCommand.MESSAGE_TARGET_FILE_EXISTS,
                            TestUtil.getFilePathInSandboxFolder(ENCRYPTED_FILENAME)), () ->
                            addCommand.execute(modelStub));

            Files.deleteIfExists(TestUtil.getFilePathInSandboxFolder(ENCRYPTED_FILENAME));
        } catch (IOException e) {
            throw new AssertionError("Error during testing.");
        }
    }

    @Test
    public void equals() {
        EncryptedFile testFile = new EncryptedFileBuilder().withFileName(FILENAME).build();
        EncryptedFile textFile = new EncryptedFileBuilder().withFileName(NON_EXIST_FILENAME).build();
        EncryptFileCommand encryptTestCommand = new EncryptFileCommand(testFile, PASSWORD);
        EncryptFileCommand encryptTextCommand = new EncryptFileCommand(textFile, PASSWORD);

        // same object -> returns true
        assertTrue(encryptTestCommand.equals(encryptTestCommand));

        // same values -> returns true
        EncryptFileCommand encryptTestCommandCopy = new EncryptFileCommand(testFile, PASSWORD);
        assertTrue(encryptTestCommandCopy.equals(encryptTestCommandCopy));

        // different types -> returns false
        assertFalse(encryptTestCommand.equals(1));

        // null -> returns false
        assertFalse(encryptTestCommand.equals(null));

        // different file -> returns false
        assertFalse(encryptTestCommand.equals(encryptTextCommand));

        // different password -> returns false
        assertFalse(encryptTestCommand.equals(new EncryptFileCommand(testFile, PASSWORD2)));
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
