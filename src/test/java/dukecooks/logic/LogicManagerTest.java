package dukecooks.logic;

import static dukecooks.testutil.profile.TypicalProfiles.AMY;
import static dukecooks.testutil.recipe.TypicalRecipes.FISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.commands.exercise.ListExerciseCommand;
import dukecooks.logic.commands.profile.AddProfileCommand;
import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.commands.recipe.ListRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.person.Person;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.workout.ReadOnlyWorkoutPlanner;
import dukecooks.storage.JsonUserPrefsStorage;
import dukecooks.storage.StorageManager;
import dukecooks.storage.dashboard.JsonDashboardStorage;
import dukecooks.storage.diary.JsonDiaryStorage;
import dukecooks.storage.exercise.JsonWorkoutPlannerStorage;
import dukecooks.storage.health.JsonHealthRecordsStorage;
import dukecooks.storage.mealplan.JsonMealPlanBookStorage;
import dukecooks.storage.profile.JsonUserProfileStorage;
import dukecooks.storage.recipe.JsonRecipeBookStorage;
import dukecooks.testutil.Assert;
import dukecooks.testutil.profile.PersonBuilder;
import dukecooks.testutil.recipe.RecipeBuilder;


public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonDashboardStorage dashboardStorage =
                new JsonDashboardStorage(temporaryFolder.resolve("dashboard.json"));
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookStorage(temporaryFolder.resolve("recipes.json"));
        JsonMealPlanBookStorage mealPlanBookStorage =
                new JsonMealPlanBookStorage(temporaryFolder.resolve("mealplans.json"));
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
                recipeBookStorage, mealPlanBookStorage, workoutPlannerStorage, diaryStorage,
                dashboardStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_recipeCommandExecutionError_throwsCommandException() {
        String deleteCommand = "delete recipe 9";
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete exercise 9";
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validRecipeCommand_success() throws Exception {
        String listCommand = ListRecipeCommand.COMMAND_WORD + " " + ListRecipeCommand.VARIANT_WORD;
        assertCommandSuccess(listCommand, ListRecipeCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_validExerciseCommand_success() throws Exception {
        String listCommand = ListExerciseCommand.COMMAND_WORD + " " + ListExerciseCommand.VARIANT_WORD;
        assertCommandSuccess(listCommand, ListExerciseCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonRecipeBookIoExceptionThrowingStub
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionRecipeBook.json"));
        // Setup LogicManager with JsonMealPlanBookIoExceptionThrowingStub
        JsonMealPlanBookStorage mealPlanBookStorage =
                new JsonMealPlanBookStorage(temporaryFolder.resolve("mealplans.json"));
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
        // Setup LogicManager with JsonDashboardIoExceptionThrowingStub
        JsonDashboardStorage dashboardStorage =
                new JsonDashboardIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionDashboard.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        //StorageManager storage = new StorageManager(recipeBookStorage, userPrefsStorage);
        StorageManager storage = new StorageManager(userProfileStorage, null,
                recipeBookStorage, mealPlanBookStorage, workoutPlannerStorage, diaryStorage,
                dashboardStorage, userPrefsStorage);
        new JsonUserPrefsStorage(temporaryFolder
                .resolve("ioExceptionUserPrefs.json"));
        logic = new LogicManager(model, storage);


        ModelManager expectedModel = new ModelManager();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;

        // Execute add command
        String addCommand = AddRecipeCommand.COMMAND_WORD + " " + AddRecipeCommand.VARIANT_WORD
                + " " + CommandTestUtil.NAME_DESC_FISH
                + CommandTestUtil.INGREDIENT_DESC_FISH + CommandTestUtil.CALORIES_DESC_FISH
                + CommandTestUtil.CARBS_DESC_FISH + CommandTestUtil.FATS_DESC_FISH
                + CommandTestUtil.PROTEIN_DESC_FISH;
        Recipe expectedRecipe = new RecipeBuilder(FISH).build();

        expectedModel.addRecipe(expectedRecipe);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);

        addCommand = AddProfileCommand.COMMAND_WORD + " " + AddProfileCommand.VARIANT_WORD
                + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.DOB_DESC
                + CommandTestUtil.GENDER_DESC + CommandTestUtil.BLOODTYPE_DESC
                + CommandTestUtil.HEIGHT_DESC + CommandTestUtil.WEIGHT_DESC;
        Person expectedPerson = new PersonBuilder(AMY).withMedicalHistories().build();
        //Exercise expectedExercise = new ExerciseBuilder(PUSHUP)
        //       .withDetails(null, null, null, null, null, null)
        //        .build();
        expectedModel.addPerson(expectedPerson);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredExerciseList().remove(0));
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredRecipeList().remove(0));
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
        Model expectedModel = new ModelManager(model.getUserProfile(), model.getDashboardRecords(),
                model.getHealthRecords(), model.getRecipeBook(), model.getMealPlanBook(), model.getWorkoutPlanner(),
                model.getDiaryRecords(), new UserPrefs());
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
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
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

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonDashboardIoExceptionThrowingStub extends JsonDashboardStorage {
        private JsonDashboardIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveDashboard(ReadOnlyDashboard diary, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
