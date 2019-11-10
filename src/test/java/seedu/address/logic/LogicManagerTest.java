package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_STUDY_PLAN_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.storage.CreateStudyPlanCommand;
import seedu.address.logic.commands.storage.ListAllStudyPlansCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulePlanner;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.Title;
import seedu.address.storage.JsonModulePlannerStorage;
import seedu.address.storage.JsonModulesInfoStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.ModulesInfoStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.StudyPlanBuilder;

/**
 * A test class for {@code LogicManager}.
 */
public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;
    private ModulesInfo modulesInfo;

    @BeforeEach
    public void setUp() {
        JsonModulePlannerStorage modulePlannerStorage =
                new JsonModulePlannerStorage(temporaryFolder.resolve("modulePlanner.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonModulesInfoStorage jsonModulesInfoStorage =
                new JsonModulesInfoStorage(temporaryFolder.resolve("modulesInfo.json"));
        StorageManager storage = new StorageManager(modulePlannerStorage, userPrefsStorage, jsonModulesInfoStorage);

        ModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(Paths.get("modules_cs.json"));
        modulesInfo = initModulesInfo(modulesInfoStorage);
        model = new ModelManager(new ModulePlanner(), new UserPrefs(), modulesInfo);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteStudyPlanCommand = "removeplan 9";
        assertCommandException(deleteStudyPlanCommand, MESSAGE_INVALID_STUDY_PLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        model.addStudyPlan(new StudyPlanBuilder().build()); // so that there's something to list
        String listAllStudyPlansCommand = ListAllStudyPlansCommand.COMMAND_WORD;
        assertCommandSuccess(listAllStudyPlansCommand,
                ListAllStudyPlansCommand.MESSAGE_SUCCESS + "[ID: 1] Title: Test Study Plan\n", model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonModulePlannerIoExceptionThrowingStub
        JsonModulePlannerStorage modulePlannerStorage =
                new JsonModulePlannerIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionModulePlanner.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonModulesInfoStorage modulesInfoStorage =
                new JsonModulesInfoStorage(temporaryFolder.resolve("ioExceptionModulesInfo.json"));
        StorageManager storage = new StorageManager(modulePlannerStorage, userPrefsStorage, modulesInfoStorage);
        logic = new LogicManager(model, storage);

        // Execute create study plan command to create one without a title
        String createStudyPlanCommand = CreateStudyPlanCommand.COMMAND_WORD;
        StudyPlan expectedStudyPlan = new StudyPlanBuilder().withTitle(new Title("")).build();
        ModelManager expectedModel = new ModelManager(new ModulePlanner(), new UserPrefs(), modulesInfo);
        expectedModel.addStudyPlan(expectedStudyPlan);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;

        assertThrows(CommandException.class, expectedMessage, () -> logic.execute(createStudyPlanCommand));
    }

    @Test
    public void getFilteredStudyPlanList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredStudyPlanList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getModulePlanner(), new UserPrefs(), modulesInfo);
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonModulePlannerIoExceptionThrowingStub extends JsonModulePlannerStorage {
        private JsonModulePlannerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveModulePlanner(ReadOnlyModulePlanner modulePlanner, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * Initialises modules info from storage.
     */
    protected ModulesInfo initModulesInfo(ModulesInfoStorage storage) {
        ModulesInfo initializedModulesInfo;
        try {
            Optional<ModulesInfo> prefsOptional = storage.readModulesInfo();
            initializedModulesInfo = prefsOptional.orElse(new ModulesInfo());
        } catch (DataConversionException | IOException e) {
            initializedModulesInfo = new ModulesInfo();
        }
        return initializedModulesInfo;
    }

}
