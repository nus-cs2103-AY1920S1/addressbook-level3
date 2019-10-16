package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARBS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIARY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FATS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICALHISTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROTEIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPETITIONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SETS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.DiaryRecords;
import seedu.address.model.RecipeBook;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.DiaryNameContainsKeywordsPredicate;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.exercise.ExerciseNameContainsKeywordsPredicate;
import seedu.address.model.exercise.Intensity;
import seedu.address.model.exercise.MuscleType;
import seedu.address.model.exercise.MusclesTrained;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.recipe.RecipeNameContainsKeywordsPredicate;
import seedu.address.testutil.EditDiaryDescriptorBuilder;
import seedu.address.testutil.EditExerciseDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditRecipeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

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
    public static final String SETS_DESC_FIVE = " " + PREFIX_REPETITIONS + VALID_SETS_FIVE;
    public static final String REPS_DESC_SIXTY = " " + PREFIX_REPETITIONS + VALID_REPS_SIXTY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Pushup&"; // '&' not allowed in names
    public static final String INVALID_SETS_DESC = " " + PREFIX_SETS + "5*"; // '*' not allowed in tags
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

    public static final EditExerciseCommand.EditExerciseDescriptor DESC_PUSHUP;
    public static final EditExerciseCommand.EditExerciseDescriptor DESC_SITUP;
    public static final EditRecipeCommand.EditRecipeDescriptor DESC_FISH;
    public static final EditRecipeCommand.EditRecipeDescriptor DESC_BURGER;
    public static final EditProfileCommand.EditPersonDescriptor DESC_AMY;
    public static final EditProfileCommand.EditPersonDescriptor DESC_BOB;
    public static final EditDiaryCommand.EditDiaryDescriptor DESC_AMY_DIARY;
    public static final EditDiaryCommand.EditDiaryDescriptor DESC_BOB_DIARY;

    static {
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
        DESC_FISH = new EditRecipeDescriptorBuilder().withName(VALID_NAME_FISH)
                .withIngredients(VALID_INGREDIENT_FISH)
                .withCalories(VALID_CALORIES_FISH).withCarbs(VALID_CARBS_FISH)
                .withFats(VALID_FATS_FISH).withProtein(VALID_PROTEIN_FISH)
                .build();
        DESC_BURGER = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BURGER)
                .withIngredients(VALID_INGREDIENT_BURGER, VALID_INGREDIENT_FISH)
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
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WorkoutPlanner expectedWorkoutPlanner = (WorkoutPlanner) actualModel.getWorkoutPlanner();
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedWorkoutPlanner, actualModel.getWorkoutPlanner());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
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
}
