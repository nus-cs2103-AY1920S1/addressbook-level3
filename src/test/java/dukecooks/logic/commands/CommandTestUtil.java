package dukecooks.logic.commands;

import static dukecooks.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CALORIES;
import static dukecooks.logic.parser.CliSyntax.PREFIX_CARBS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DOB;
import static dukecooks.logic.parser.CliSyntax.PREFIX_FATS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_GENDER;
import static dukecooks.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_INTENSITY;
import static dukecooks.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static dukecooks.logic.parser.CliSyntax.PREFIX_NAME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PRIMARY_MUSCLE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEINGREDIENT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_SETS;
import static dukecooks.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.dashboard.EditTaskCommand;
import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.commands.exercise.EditExerciseCommand;
import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.model.Model;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;
import dukecooks.model.profile.person.NameContainsKeywordsPredicate;
import dukecooks.model.profile.person.Person;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeNameContainsKeywordsPredicate;
import dukecooks.model.workout.WorkoutPlanner;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseNameContainsKeywordsPredicate;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.components.MusclesTrained;
import dukecooks.testutil.dashboard.EditDashboardDescriptorBuilder;
import dukecooks.testutil.diary.EditDiaryDescriptorBuilder;
import dukecooks.testutil.exercise.EditExerciseDescriptorBuilder;
import dukecooks.testutil.profile.EditPersonDescriptorBuilder;
import dukecooks.testutil.recipe.EditRecipeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DASHBOARDNAME_YOGA = "Yoga";
    public static final String VALID_DASHBOARDNAME_RUN = "Run";
    public static final String VALID_DASHBOARDNAME_WRITE = "Write";
    public static final String VALID_DASHBOARDNAME_BAKE = "Bake cupcakes";
    public static final String VALID_TASKDATE1 = "12/12/2019";
    public static final String VALID_TASKDATE2 = "13/12/2019";
    public static final String VALID_TASKDATE3 = "14/12/2019";
    public static final String VALID_TASKSTATUS_COMPLETE = "COMPLETE";
    public static final String VALID_TASKSTATUS_INCOMPLETE = "NOT COMPLETE";


    public static final String VALID_NAME_FISH = "Fish and Chips";
    public static final String VALID_NAME_BURGER = "Cheese Burger";
    public static final String VALID_INGREDIENT_FISH = "Dory Fish";
    public static final String VALID_INGREDIENT_BURGER = "Beef Patty";
    public static final String VALID_CALORIES_FISH = "600";
    public static final String VALID_CALORIES_BURGER = "610";
    public static final String VALID_CARBS_FISH = "59";
    public static final String VALID_CARBS_BURGER = "57";
    public static final String VALID_FATS_FISH = "25";
    public static final String VALID_FATS_BURGER = "31";
    public static final String VALID_PROTEIN_FISH = "35";
    public static final String VALID_PROTEIN_BURGER = "28";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_AMY_DIARY = "Amy Diary";
    public static final String VALID_NAME_BOB_DIARY = "Bob Diary";
    public static final String VALID_BLOODTYPE = "A+";
    public static final String VALID_GENDER = "male";
    public static final String VALID_DOB = "30/08/1995";
    public static final String VALID_HEIGHT = "180";
    public static final String VALID_WEIGHT = "60";
    public static final String VALID_HISTORY_DENGUE = "dengue";
    public static final String VALID_HISTORY_STROKE = "stroke";
    public static final String VALID_NAME_PUSHUP = "Pushup";
    public static final String VALID_NAME_SITUP = "Situp";
    public static final String VALID_MUSCLE_ABS = "Abs";
    public static final String VALID_MUSCLE_CHEST = "Chest";
    public static final String VALID_INTENSITY_MEDIUM = "medium";
    public static final String VALID_INTENSITY_HIGH = "high";
    public static final MusclesTrained VALID_MUSCLES_TRAINED = new MusclesTrained(new MuscleType("Chest"),
            new ArrayList<MuscleType>());
    public static final Intensity VALID_INTENSITY_NAME = Intensity.MEDIUM;
    public static final Integer VALID_REPS_SIXTY = 60;
    public static final Integer VALID_SETS_FIVE = 5;

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_AMY_DIARY = " " + PREFIX_DIARY_NAME + VALID_NAME_AMY_DIARY;
    public static final String NAME_DESC_BOB_DIARY = " " + PREFIX_DIARY_NAME + VALID_NAME_BOB_DIARY;
    public static final String BLOODTYPE_DESC = " " + PREFIX_BLOODTYPE + VALID_BLOODTYPE;
    public static final String GENDER_DESC = " " + PREFIX_GENDER + VALID_GENDER;
    public static final String DOB_DESC = " " + PREFIX_DOB + VALID_DOB;
    public static final String HEIGHT_DESC = " " + PREFIX_HEIGHT + VALID_HEIGHT;
    public static final String WEIGHT_DESC = " " + PREFIX_WEIGHT + VALID_WEIGHT;
    public static final String HISTORY_DESC_STROKE = " " + PREFIX_MEDICALHISTORY + VALID_HISTORY_STROKE;
    public static final String HISTORY_DESC_DENGUE = " " + PREFIX_MEDICALHISTORY + VALID_HISTORY_DENGUE;
    public static final String NAME_DESC_FISH = " " + PREFIX_NAME + VALID_NAME_FISH;
    public static final String NAME_DESC_BURGER = " " + PREFIX_NAME + VALID_NAME_BURGER;
    public static final String INGREDIENT_DESC_FISH = " " + PREFIX_INGREDIENT + VALID_INGREDIENT_FISH;
    public static final String INGREDIENT_DESC_BURGER = " " + PREFIX_INGREDIENT + VALID_INGREDIENT_BURGER;
    public static final String REMOVEINGREDIENT_DESC_FISH = " " + PREFIX_REMOVEINGREDIENT + VALID_INGREDIENT_FISH;
    public static final String REMOVEINGREDIENT_DESC_BURGER = " " + PREFIX_REMOVEINGREDIENT + VALID_INGREDIENT_BURGER;
    public static final String CALORIES_DESC_FISH = " " + PREFIX_CALORIES + VALID_CALORIES_FISH;
    public static final String CALORIES_DESC_BURGER = " " + PREFIX_CALORIES + VALID_CALORIES_BURGER;
    public static final String CARBS_DESC_FISH = " " + PREFIX_CARBS + VALID_CARBS_FISH;
    public static final String CARBS_DESC_BURGER = " " + PREFIX_CARBS + VALID_CARBS_BURGER;
    public static final String FATS_DESC_FISH = " " + PREFIX_FATS + VALID_FATS_FISH;
    public static final String FATS_DESC_BURGER = " " + PREFIX_FATS + VALID_FATS_BURGER;
    public static final String PROTEIN_DESC_FISH = " " + PREFIX_PROTEIN + VALID_PROTEIN_FISH;
    public static final String PROTEIN_DESC_BURGER = " " + PREFIX_PROTEIN + VALID_PROTEIN_BURGER;

    public static final String NAME_DESC_PUSHUP = " " + PREFIX_NAME + VALID_NAME_PUSHUP;
    public static final String NAME_DESC_SITUP = " " + PREFIX_NAME + VALID_NAME_SITUP;
    public static final String MUSCLE_DESC_ABS = " " + PREFIX_PRIMARY_MUSCLE + VALID_MUSCLE_ABS;
    public static final String MUSCLE_DESC_CHEST = " " + PREFIX_PRIMARY_MUSCLE + VALID_MUSCLE_CHEST;
    public static final String INTENSITY_DESC_MEDIUM = " " + PREFIX_INTENSITY + VALID_INTENSITY_MEDIUM;
    public static final String INTENSITY_DESC_HIGH = " " + PREFIX_INTENSITY + VALID_INTENSITY_HIGH;
    public static final String SETS_DESC_FIVE = " " + PREFIX_SETS + VALID_SETS_FIVE;
    public static final String REPS_DESC_SIXTY = " " + PREFIX_REPETITIONS + VALID_REPS_SIXTY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Pushup&"; // '&' not allowed in names
    public static final String INVALID_SETS_DESC = " " + PREFIX_SETS + "0;;*"; // '*' not allowed in tags
    public static final String INVALID_FOOD_NAME_DESC = " " + PREFIX_NAME + "Fish & Chips"; // '&' not allowed in names
    public static final String INVALID_INGREDIENT_DESC = " " + PREFIX_INGREDIENT
            + "Cheese*Burger"; // '*' not allowed in ingredient names
    public static final String INVALID_CALORIES_DESC = " " + PREFIX_CALORIES + "1a"; // 'a' not allowed in calories
    public static final String INVALID_CARBS_DESC = " " + PREFIX_CARBS + "1a"; // 'a' not allowed in carbs
    public static final String INVALID_FATS_DESC = " " + PREFIX_FATS + "1a"; // 'a' not allowed in fats
    public static final String INVALID_PROTEIN_DESC = " " + PREFIX_PROTEIN + "1a"; // 'a' not allowed in protein
    public static final String INVALID_TAG_DESC = " " + PREFIX_MEDICALHISTORY + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskCommand.EditTaskDescriptor DESC_HW;
    public static final EditTaskCommand.EditTaskDescriptor DESC_PLAY;
    public static final EditExerciseCommand.EditExerciseDescriptor DESC_PUSHUP;
    public static final EditExerciseCommand.EditExerciseDescriptor DESC_SITUP;
    public static final EditRecipeCommand.EditRecipeDescriptor DESC_FISH;
    public static final EditRecipeCommand.EditRecipeDescriptor DESC_BURGER;
    public static final EditProfileCommand.EditPersonDescriptor DESC_AMY;
    public static final EditProfileCommand.EditPersonDescriptor DESC_BOB;
    public static final EditDiaryCommand.EditDiaryDescriptor DESC_AMY_DIARY;
    public static final EditDiaryCommand.EditDiaryDescriptor DESC_BOB_DIARY;

    static {
        DESC_HW = new EditDashboardDescriptorBuilder().withDashboardName(VALID_DASHBOARDNAME_YOGA)
                .withTaskDate(VALID_TASKDATE1).build();
        DESC_PLAY = new EditDashboardDescriptorBuilder().withDashboardName(VALID_DASHBOARDNAME_RUN)
                .withTaskDate(VALID_TASKDATE2).build();
        DESC_PUSHUP = new EditExerciseDescriptorBuilder().withName(VALID_NAME_PUSHUP)
                .withDetails(null, null, null, null, null,
                        VALID_SETS_FIVE).build();
        DESC_SITUP = new EditExerciseDescriptorBuilder().withName(VALID_NAME_SITUP)
                .withDetails(null, null, null, null, VALID_SETS_FIVE,
                        VALID_REPS_SIXTY).build();
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withMedicalHistories(VALID_HISTORY_STROKE).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withMedicalHistories(VALID_HISTORY_DENGUE, VALID_HISTORY_STROKE).build();
        DESC_AMY_DIARY = new EditDiaryDescriptorBuilder().withName(VALID_NAME_AMY_DIARY).build();
        DESC_BOB_DIARY = new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB_DIARY).build();
        DESC_FISH = new EditRecipeDescriptorBuilder().withRecipeName(VALID_NAME_FISH)
                .withIngredientsToAdd(VALID_INGREDIENT_FISH)
                .withCalories(VALID_CALORIES_FISH).withCarbs(VALID_CARBS_FISH)
                .withFats(VALID_FATS_FISH).withProtein(VALID_PROTEIN_FISH)
                .build();
        DESC_BURGER = new EditRecipeDescriptorBuilder().withRecipeName(VALID_NAME_BURGER)
                .withIngredientsToAdd(VALID_INGREDIENT_BURGER, VALID_INGREDIENT_FISH)
                .withCalories(VALID_CALORIES_BURGER).withCarbs(VALID_CARBS_BURGER)
                .withFats(VALID_FATS_BURGER).withProtein(VALID_PROTEIN_BURGER)
                .build();
    }

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
     * - RecipeBook, filtered recipe list and selected recipe in {@code actualModel} remain unchanged
     */
    public static void assertRecipeCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        RecipeBook expectedRecipeBook = new RecipeBook(actualModel.getRecipeBook());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredRecipeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedRecipeBook, actualModel.getRecipeBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredRecipeList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - Duke Cooks, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertExerciseCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WorkoutPlanner expectedWorkoutPlanner = (WorkoutPlanner) actualModel.getWorkoutPlanner();
        List<Exercise> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExerciseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedWorkoutPlanner, actualModel.getWorkoutPlanner());
        assertEquals(expectedFilteredList, actualModel.getFilteredExerciseList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - Diary Record, filtered diary list and selected diary in {@code actualModel} remain unchanged
     */
    public static void assertDiaryCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DiaryRecords expectedDiaryRecord = new DiaryRecords(actualModel.getDiaryRecords());
        List<Diary> expectedDiaryList = new ArrayList<>(actualModel.getFilteredDiaryList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedDiaryRecord, actualModel.getDiaryRecords());
        assertEquals(expectedDiaryList, actualModel.getFilteredDiaryList());
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - Dashboard Records, filtered dashboard list and selected dashboard in {@code actualModel} remain unchanged
     */
    public static void assertDashboardCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DashboardRecords expectedDashboardRecord = new DashboardRecords(actualModel.getDashboardRecords());
        List<Dashboard> expectedDashboardList = new ArrayList<>(actualModel.getFilteredDashboardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedDashboardRecord, actualModel.getDashboardRecords());
        assertEquals(expectedDashboardList, actualModel.getFilteredDashboardList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s Duke Cooks.
     */
    public static void showExerciseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExerciseList().size());

        Exercise exercise = model.getFilteredExerciseList().get(targetIndex.getZeroBased());
        final String[] splitName = exercise.getExerciseName().exerciseName.split("\\s+");
        model.updateFilteredExerciseList(new ExerciseNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredExerciseList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s Duke Cooks.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code model}'s RecipeBook.
     */
    public static void showRecipeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredRecipeList().size());

        Recipe recipe = model.getFilteredRecipeList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().fullName.split("\\s+");
        model.updateFilteredRecipeList(new RecipeNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredRecipeList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the diary at the given {@code targetIndex} in the
     * {@code model}'s Duke Cooks.
     */
    public static void showDiaryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDiaryList().size());

        Diary diary = model.getFilteredDiaryList().get(targetIndex.getZeroBased());
        final String[] splitName = diary.getDiaryName().fullName.split("\\s+");
        model.updateFilteredDiaryList(new DiaryNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDiaryList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the dashboard at the given {@code targetIndex} in the
     * {@code model}'s DukeCooks.
     */
    public static void showDashboardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDashboardList().size());

        Dashboard dashboard = model.getFilteredDashboardList().get(targetIndex.getZeroBased());
        final String[] splitName = dashboard.getDashboardName().fullName.split("\\s+");
        model.updateFilteredDashboardList(new DashboardNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDashboardList().size());
    }
}
