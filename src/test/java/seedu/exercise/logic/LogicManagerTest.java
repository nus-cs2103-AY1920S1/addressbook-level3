package seedu.exercise.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.commons.core.Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX;
import static seedu.exercise.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.CommonTestData.CALORIES_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.CATEGORY_DESC_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.DATE_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.EXERCISE_BOOK_FILE_NAME;
import static seedu.exercise.testutil.CommonTestData.NAME_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.QUANTITY_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.REGIME_BOOK_FILE_NAME;
import static seedu.exercise.testutil.CommonTestData.UNIT_DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_LIST_TYPE_EXERCISE;
import static seedu.exercise.testutil.CommonTestData.VALID_PREFIX_NAME_CARDIO;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.AEROBICS;
import static seedu.exercise.ui.ListResourceType.LIST_TYPE_EXERCISE;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.State;
import seedu.exercise.logic.commands.AddExerciseCommand;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.ListCommand;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.commands.UndoCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.exceptions.ParseException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.storage.JsonPropertyBookStorage;
import seedu.exercise.storage.JsonUserPrefsStorage;
import seedu.exercise.storage.Storage;
import seedu.exercise.storage.StorageBook;
import seedu.exercise.storage.bookstorage.JsonExerciseBookStorage;
import seedu.exercise.storage.bookstorage.JsonRegimeBookStorage;
import seedu.exercise.storage.bookstorage.JsonScheduleBookStorage;
import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.testutil.typicalutil.TypicalConflict;

public class LogicManagerTest {

    private static final Path exerciseBookFilePath = Paths.get("data", EXERCISE_BOOK_FILE_NAME);
    private static final Path regimeBookFilePath = Paths.get("data", REGIME_BOOK_FILE_NAME);

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Storage storage;
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonExerciseBookStorage jsonExerciseBookStorage =
            new JsonExerciseBookStorage(temporaryFolder.resolve(CommonTestData.EXERCISE_BOOK_FILE_NAME));
        JsonRegimeBookStorage jsonRegimeBookStorage =
            new JsonRegimeBookStorage(temporaryFolder.resolve(CommonTestData.REGIME_BOOK_FILE_NAME));
        JsonExerciseBookStorage allJsonExerciseDatabase =
            new JsonExerciseBookStorage(temporaryFolder.resolve(CommonTestData.EXERCISE_DATABASE_FILE_NAME));
        new JsonRegimeBookStorage(temporaryFolder.resolve(CommonTestData.REGIME_BOOK_FILE_NAME));
        JsonScheduleBookStorage jsonScheduleBookStorage =
            new JsonScheduleBookStorage(temporaryFolder.resolve(CommonTestData.SCHEDULE_BOOK_FILE_NAME));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
            temporaryFolder.resolve(CommonTestData.USER_PREFS_FILE_NAME));
        JsonPropertyBookStorage propertyBookStorage =
            new JsonPropertyBookStorage(temporaryFolder.resolve(CommonTestData.PROPERTY_BOOK_FILE_NAME));
        storage = new StorageBook(jsonExerciseBookStorage, allJsonExerciseDatabase,
            jsonRegimeBookStorage, jsonScheduleBookStorage, userPrefsStorage, propertyBookStorage);
        MainApp.setState(State.NORMAL);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete t/exercise i/9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD + " " + PREFIX_CATEGORY + LIST_TYPE_EXERCISE;
        assertCommandSuccess(listCommand,
            String.format(ListCommand.MESSAGE_SUCCESS, LIST_TYPE_EXERCISE),
            model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonExerciseBookIoExceptionThrowingStub
        JsonExerciseBookStorage jsonExerciseBookStorage =
            new JsonExerciseBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionExerciseBook.json"));
        JsonRegimeBookStorage jsonRegimeBookStorage =
            new JsonRegimeBookStorage(temporaryFolder.resolve("ioExceptionRegimeBook.json"));
        JsonScheduleBookStorage jsonScheduleBookStorage =
            new JsonScheduleBookStorage(temporaryFolder.resolve("ioExceptionScheduleBook.json"));
        JsonExerciseBookStorage allJsonExerciseBookStorage =
            new JsonExerciseBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAllExerciseBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonPropertyBookStorage propertyBookStorage =
            new JsonPropertyBookStorage(temporaryFolder.resolve("ioExceptionPropertyBook.json"));
        StorageBook storage = new StorageBook(jsonExerciseBookStorage, allJsonExerciseBookStorage,
            jsonRegimeBookStorage, jsonScheduleBookStorage, userPrefsStorage, propertyBookStorage);

        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddExerciseCommand.COMMAND_WORD + CATEGORY_DESC_EXERCISE + NAME_DESC_AEROBICS
            + DATE_DESC_AEROBICS + CALORIES_DESC_AEROBICS + QUANTITY_DESC_AEROBICS + UNIT_DESC_AEROBICS;
        Exercise expectedExercise = new ExerciseBuilder(AEROBICS).withMuscles().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addExercise(expectedExercise);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getSortedExerciseList().remove(0));
    }

    @Test
    public void execute_incorrectState_throwsCommandException() {
        //Resolve command in NORMAL state
        assertThrows(CommandException.class, () -> logic.execute(ResolveCommand.COMMAND_WORD
            + VALID_PREFIX_NAME_CARDIO));

        //Any non resolve command in conflict state
        MainApp.setState(State.IN_CONFLICT);
        assertThrows(CommandException.class, () -> logic.execute(ListCommand.COMMAND_WORD
            + VALID_PREFIX_LIST_TYPE_EXERCISE));
        assertThrows(CommandException.class, () -> logic.execute(UndoCommand.COMMAND_WORD));
    }

    @Test
    public void getConflict_incorrectState_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> logic.getConflict());
    }

    @Test
    public void getMethods_defaultValues_success() {
        assertEquals(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR), logic.getExerciseBook());
        assertEquals(model.getSortedExerciseList(), logic.getSortedExerciseList());
        assertEquals(new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR), logic.getRegimeBook());
        assertEquals(model.getSortedRegimeList(), logic.getSortedRegimeList());
        assertEquals(model.getSortedScheduleList(), logic.getSortedScheduleList());
        assertEquals(model.getSuggestedExerciseList(), logic.getSuggestedExerciseList());
        assertEquals(model.getGuiSettings(), logic.getGuiSettings());

        //GuiSettings to be checked
        GuiSettings toBeSet = new GuiSettings(1010, 1010, 1, 1);
        logic.setGuiSettings(toBeSet);
        assertEquals(toBeSet, logic.getGuiSettings());

        assertEquals(model.getStatistic(), logic.getStatistic());

        //MainApp has to be in conflict in order for conflict methods to work
        MainApp.setState(State.IN_CONFLICT);
        Conflict conflict = TypicalConflict.VALID_CONFLICT;
        model.setConflict(conflict);
        assertEquals(conflict, logic.getConflict());
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
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getExerciseBookData(),
            new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
            new UserPrefs());
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
    private static class JsonExerciseBookIoExceptionThrowingStub extends JsonExerciseBookStorage {
        private JsonExerciseBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        public void saveResourceBook(ReadOnlyResourceBook<Exercise> exerciseBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
