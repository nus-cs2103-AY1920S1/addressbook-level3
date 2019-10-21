package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX;
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.DOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.HEIGHT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WEIGHT_DESC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.profile.TypicalProfiles.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exercise.ListExerciseCommand;
import seedu.address.logic.commands.profile.AddProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;
import seedu.address.model.profile.ReadOnlyUserProfile;
import seedu.address.model.profile.person.Person;
import seedu.address.model.recipe.ReadOnlyRecipeBook;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.diary.JsonDiaryStorage;
import seedu.address.storage.exercise.JsonWorkoutPlannerStorage;
import seedu.address.storage.health.JsonHealthRecordsStorage;
import seedu.address.storage.profile.JsonUserProfileStorage;
import seedu.address.storage.recipe.JsonRecipeBookStorage;
import seedu.address.testutil.profile.PersonBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookStorage(temporaryFolder.resolve("recipes.json"));
        JsonUserProfileStorage userProfileStorage =
                new JsonUserProfileStorage(temporaryFolder.resolve("dukecooks.json"));
        JsonHealthRecordsStorage healthRecordsStorage =
                new JsonHealthRecordsStorage(temporaryFolder.resolve("healthrecords.json"));
        JsonWorkoutPlannerStorage workoutPlannerStorage =
                new JsonWorkoutPlannerStorage(temporaryFolder.resolve("exercises.json"));
        JsonDiaryStorage diaryStorage =
                new JsonDiaryStorage(temporaryFolder.resolve("diary.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        //StorageManager storage = new StorageManager(recipeBookStorage, userPrefsStorage);
        StorageManager storage = new StorageManager(userProfileStorage, healthRecordsStorage,
                recipeBookStorage, workoutPlannerStorage, diaryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    //@Test
    //public void execute_recipeCommandExecutionError_throwsCommandException() {
    //    String deleteCommand = "delete recipe 9";
    //    assertCommandException(deleteCommand, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    //}

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_validExerciseCommand_success() throws Exception {
        String listCommand = ListExerciseCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListExerciseCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonRecipeBookIoExceptionThrowingStub
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionRecipeBook.json"));
        // Setup LogicManager with JsonUserProfileIoExceptionThrowingStub
        JsonUserProfileStorage userProfileStorage =
                new JsonUserProfileIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionDukeCooks.json"));
        // Setup LogicManager with JsonDukeCooksIoExceptionThrowingStub
        JsonWorkoutPlannerStorage workoutPlannerStorage =
                new JsonWorkoutPlannerIoExceptionThrowingStub(temporaryFolder
                        .resolve("ioExceptionWorkoutPlanner.json"));
        // Setup LogicManager with JsonDiaryIoExceptionThrowingStub
        JsonDiaryStorage diaryStorage =
                new JsonDiaryIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionDiary.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        //StorageManager storage = new StorageManager(recipeBookStorage, userPrefsStorage);
        StorageManager storage = new StorageManager(userProfileStorage, null,
                recipeBookStorage, workoutPlannerStorage, diaryStorage, userPrefsStorage);
        new JsonUserPrefsStorage(temporaryFolder
                .resolve("ioExceptionUserPrefs.json"));
        logic = new LogicManager(model, storage);

        // Execute add command
        //String addCommand = AddRecipeCommand.COMMAND_WORD + " " + AddRecipeCommand.VARIANT_WORD + " " + NAME_DESC_FISH
        //        + INGREDIENT_DESC_FISH + CALORIES_DESC_FISH + CARBS_DESC_FISH + FATS_DESC_FISH + PROTEIN_DESC_FISH;
        //Recipe expectedRecipe = new RecipeBuilder(FISH).build();
        String addCommand = AddProfileCommand.COMMAND_WORD + NAME_DESC_AMY + DOB_DESC + GENDER_DESC
                + BLOODTYPE_DESC + HEIGHT_DESC + WEIGHT_DESC;
        Person expectedPerson = new PersonBuilder(AMY).withMedicalHistories().build();
        //Exercise expectedExercise = new ExerciseBuilder(PUSHUP)
        //       .withDetails(null, null, null, null, null, null)
        //        .build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        //expectedModel.addRecipe(expectedRecipe);
        //expectedModel.addExercise(expectedExercise);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredExerciseList().remove(0));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredRecipeList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
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
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
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
        Model expectedModel = new ModelManager(model.getUserProfile(), model.getHealthRecords(),
                model.getRecipeBook(), model.getWorkoutPlanner(), model.getDiaryRecords(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
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
    private static class JsonUserProfileIoExceptionThrowingStub extends JsonUserProfileStorage {
        private JsonUserProfileIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveUserProfile(ReadOnlyUserProfile dukeCooks, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
    * A stub class to throw an {@code IOException} when the save method is called.
    */
    private static class JsonWorkoutPlannerIoExceptionThrowingStub extends JsonWorkoutPlannerStorage {

        private JsonWorkoutPlannerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveWorkoutPlanner(ReadOnlyWorkoutPlanner dukeCooks, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonRecipeBookIoExceptionThrowingStub extends JsonRecipeBookStorage {
        private JsonRecipeBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRecipeBook(ReadOnlyRecipeBook recipeBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonDiaryIoExceptionThrowingStub extends JsonDiaryStorage {
        private JsonDiaryIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveDiary(ReadOnlyDiary diary, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
