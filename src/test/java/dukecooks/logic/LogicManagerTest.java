package dukecooks.logic;

import static dukecooks.testutil.recipe.TypicalRecipes.FISH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import dukecooks.commons.core.Messages;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.commands.exercise.ListExerciseCommand;
import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.commands.recipe.ListRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.storage.JsonUserPrefsStorage;
import dukecooks.storage.StorageManager;
import dukecooks.storage.dashboard.JsonDashboardStorage;
import dukecooks.storage.diary.JsonDiaryStorage;
import dukecooks.storage.health.JsonHealthRecordsStorage;
import dukecooks.storage.mealplan.JsonMealPlanBookStorage;
import dukecooks.storage.profile.JsonUserProfileStorage;
import dukecooks.storage.recipe.JsonRecipeBookStorage;
import dukecooks.storage.workout.JsonWorkoutCatalogueStorage;
import dukecooks.storage.workout.exercise.JsonExerciseCatalogueStorage;
import dukecooks.testutil.Assert;
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
        JsonExerciseCatalogueStorage workoutPlannerStorage =
                new JsonExerciseCatalogueStorage(temporaryFolder.resolve("exercises.json"));
        JsonWorkoutCatalogueStorage workoutCatalogueStorage =
                new JsonWorkoutCatalogueStorage(temporaryFolder.resolve("workouts.json"));
        JsonDiaryStorage diaryStorage =
                new JsonDiaryStorage(temporaryFolder.resolve("diary.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        //StorageManager storage = new StorageManager(recipeBookStorage, userPrefsStorage);
        StorageManager storage = new StorageManager(userProfileStorage, healthRecordsStorage,
                recipeBookStorage, mealPlanBookStorage, workoutPlannerStorage, workoutCatalogueStorage, diaryStorage,
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
        JsonExerciseCatalogueStorage workoutPlannerStorage =
                new JsonExerciseCatalogueStorageIoExceptionThrowingStub(temporaryFolder
                        .resolve("ioExceptionWorkoutPlanner.json"));
        JsonWorkoutCatalogueStorage workoutCatalogueStorage =
                new JsonWorkoutCatalogueStorageIoExceptionThrowingStub(temporaryFolder
                        .resolve("ioExceptionWorkouts.json"));
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
                recipeBookStorage, mealPlanBookStorage, workoutPlannerStorage, workoutCatalogueStorage, diaryStorage,
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

        //Exercise expectedExercise = new ExerciseBuilder(PUSHUP)
        //       .withDetails(null, null, null, null, null, null)
        //        .build();
    }

    /**  ------------------------------------  DIARY ---------------------------------------- */

    @Test
    public void getFilteredDiaryList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDiaryList().remove(0));
    }

    @Test
    public void getDiaryFilePath_success() {
        Path expectedDiaryFilePath = Paths.get("data" , "diary.json");
        assertEquals(expectedDiaryFilePath, logic.getDiaryFilePath());
    }

    @Test
    public void getDiaryRecords_success() {
        DiaryRecords expectedDiaryRecords = new DiaryRecords();
        assertEquals(expectedDiaryRecords, model.getDiaryRecords());
    }

    /**  ------------------------------------  PROFILE ---------------------------------------- */

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void getUserProfileFilePath_success() {
        Path expectedUserProfileFilePath = Paths.get("data" , "userprofile.json");
        assertEquals(expectedUserProfileFilePath, logic.getUserProfileFilePath());
    }


    /**  ------------------------------------  RECIPE ---------------------------------------- */

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredRecipeList().remove(0));
    }

    @Test
    public void getRecipeBookFilePath_success() {
        Path expectedRecipeBookFilePath = Paths.get("data" , "recipes.json");
        assertEquals(expectedRecipeBookFilePath, logic.getRecipesFilePath());
    }

    /**  ------------------------------------  MEAL PLANS ---------------------------------------- */

    @Test
    public void getFilteredMealPlanList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredMealPlanList().remove(0));
    }

    @Test
    public void getMealPlanFilePath_success() {
        Path expectedMealPlanFilePath = Paths.get("data" , "mealplans.json");
        assertEquals(expectedMealPlanFilePath, logic.getMealPlansFilePath());
    }

    /**  ------------------------------------  EXERCISE ---------------------------------------- */

    @Test
    public void getFilteredExerciseList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredExerciseList().remove(0));
    }

    /**  ------------------------------------  WORKOUTS ---------------------------------------- */

    @Test
    public void getFilteredWorkoutList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredWorkoutList().remove(0));
    }


    /**  ------------------------------------  DASHBOARD ---------------------------------------- */

    @Test
    public void getFilteredDashboardList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDashboardList().remove(0));
    }

    @Test
    public void getDashboardFilePath_success() {
        Path expectedDashboardFilePath = Paths.get("data" , "dashboard.json");
        assertEquals(expectedDashboardFilePath, logic.getDashboardFilePath());
    }

    /**  ------------------------------------  HEALTH RECORDS ---------------------------------------- */

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredRecordList().remove(0));
    }

    @Test
    public void getHealthRecordFilePath_success() {
        Path expectedHealthRecordFilePath = Paths.get("data" , "healthrecords.json");
        assertEquals(expectedHealthRecordFilePath, logic.getHealthRecordsFilePath());
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
                model.getHealthRecords(), model.getRecipeBook(), model.getMealPlanBook(), model.getExerciseCatalogue(),
                model.getWorkoutCatalogue(), model.getDiaryRecords(), new UserPrefs());
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
    private static class JsonExerciseCatalogueStorageIoExceptionThrowingStub extends JsonExerciseCatalogueStorage {

        private JsonExerciseCatalogueStorageIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExerciseCatalogue(ReadOnlyExerciseCatalogue dukeCooks, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonWorkoutCatalogueStorageIoExceptionThrowingStub extends JsonWorkoutCatalogueStorage {

        private JsonWorkoutCatalogueStorageIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveWorkoutCatalogue(ReadOnlyWorkoutCatalogue dukeCooks, Path filePath) throws IOException {
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
