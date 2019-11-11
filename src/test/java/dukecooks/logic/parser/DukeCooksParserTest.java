package dukecooks.logic.parser;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static dukecooks.testutil.Assert.assertThrows;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DASHBOARD;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DIARY;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_MEALPLAN;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.AddCommand;
import dukecooks.logic.commands.ClearCommand;
import dukecooks.logic.commands.CreateCommand;
import dukecooks.logic.commands.DeleteCommand;
import dukecooks.logic.commands.EditCommand;
import dukecooks.logic.commands.ExitCommand;
import dukecooks.logic.commands.FindCommand;
import dukecooks.logic.commands.GotoCommand;
import dukecooks.logic.commands.HelpCommand;
import dukecooks.logic.commands.ListCommand;
import dukecooks.logic.commands.ViewCommand;
import dukecooks.logic.commands.dashboard.AddTaskCommand;
import dukecooks.logic.commands.dashboard.DeleteTaskCommand;
import dukecooks.logic.commands.dashboard.DoneTaskCommand;
import dukecooks.logic.commands.dashboard.EditTaskCommand;
import dukecooks.logic.commands.dashboard.EditTaskCommand.EditTaskDescriptor;
import dukecooks.logic.commands.dashboard.FindTaskCommand;
import dukecooks.logic.commands.dashboard.GotoTaskCommand;
import dukecooks.logic.commands.dashboard.ListTaskDoneCommand;
import dukecooks.logic.commands.dashboard.ListTaskNotDoneCommand;
import dukecooks.logic.commands.diary.AddDiaryCommand;
import dukecooks.logic.commands.diary.AddPageCommand;
import dukecooks.logic.commands.diary.CreatePageCommand;
import dukecooks.logic.commands.diary.DeleteDiaryCommand;
import dukecooks.logic.commands.diary.DeletePageCommand;
import dukecooks.logic.commands.diary.EditDiaryCommand;
import dukecooks.logic.commands.diary.EditDiaryCommand.EditDiaryDescriptor;
import dukecooks.logic.commands.diary.EditPageCommand;
import dukecooks.logic.commands.diary.EditPageCommand.EditPageDescriptor;
import dukecooks.logic.commands.diary.FindDiaryCommand;
import dukecooks.logic.commands.diary.GotoDiaryCommand;
import dukecooks.logic.commands.diary.ListDiaryCommand;
import dukecooks.logic.commands.diary.ViewDiaryCommand;
import dukecooks.logic.commands.exercise.AddExerciseCommand;
import dukecooks.logic.commands.exercise.ClearExerciseCommand;
import dukecooks.logic.commands.exercise.GotoExerciseCommand;
import dukecooks.logic.commands.exercise.ListExerciseCommand;
import dukecooks.logic.commands.health.GotoHealthCommand;
import dukecooks.logic.commands.health.ListHealthCommand;
import dukecooks.logic.commands.mealplan.AddMealPlanCommand;
import dukecooks.logic.commands.mealplan.ClearMealPlanCommand;
import dukecooks.logic.commands.mealplan.DeleteMealPlanCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand;
import dukecooks.logic.commands.mealplan.EditMealPlanCommand.EditMealPlanDescriptor;
import dukecooks.logic.commands.mealplan.FindMealPlanCommand;
import dukecooks.logic.commands.mealplan.GotoMealPlanCommand;
import dukecooks.logic.commands.mealplan.ListMealPlanCommand;
import dukecooks.logic.commands.mealplan.ViewMealPlanCommand;
import dukecooks.logic.commands.profile.EditProfileCommand;
import dukecooks.logic.commands.recipe.AddRecipeCommand;
import dukecooks.logic.commands.recipe.ClearRecipeCommand;
import dukecooks.logic.commands.recipe.DeleteRecipeCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand;
import dukecooks.logic.commands.recipe.EditRecipeCommand.EditRecipeDescriptor;
import dukecooks.logic.commands.recipe.FindRecipeCommand;
import dukecooks.logic.commands.recipe.GotoRecipeCommand;
import dukecooks.logic.commands.recipe.ListRecipeCommand;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardNameContainsKeywordsPredicate;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;
import dukecooks.model.diary.components.Page;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.mealplan.components.MealPlanNameContainsKeywordsPredicate;
import dukecooks.model.profile.person.Person;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.recipe.components.RecipeNameContainsKeywordsPredicate;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.testutil.dashboard.DashboardBuilder;
import dukecooks.testutil.dashboard.DashboardUtil;
import dukecooks.testutil.dashboard.EditDashboardDescriptorBuilder;
import dukecooks.testutil.diary.DiaryBuilder;
import dukecooks.testutil.diary.DiaryUtil;
import dukecooks.testutil.diary.EditDiaryDescriptorBuilder;
import dukecooks.testutil.diary.EditPageDescriptorBuilder;
import dukecooks.testutil.diary.PageBuilder;
import dukecooks.testutil.diary.TypicalDiaries;
import dukecooks.testutil.exercise.ExerciseBuilder;
import dukecooks.testutil.exercise.ExerciseUtil;
import dukecooks.testutil.mealplan.EditMealPlanDescriptorBuilder;
import dukecooks.testutil.mealplan.MealPlanBuilder;
import dukecooks.testutil.mealplan.MealPlanUtil;
import dukecooks.testutil.profile.EditPersonDescriptorBuilder;
import dukecooks.testutil.profile.PersonBuilder;
import dukecooks.testutil.profile.PersonUtil;
import dukecooks.testutil.recipe.EditRecipeDescriptorBuilder;
import dukecooks.testutil.recipe.RecipeBuilder;
import dukecooks.testutil.recipe.RecipeUtil;

public class DukeCooksParserTest {

    private final DukeCooksParser parser = new DukeCooksParser();

    /**  ------------------------------------  DASHBOARD -------------------------------------- */

    @Test
    public void parseCommand_listTaskDone() throws Exception {
        assertTrue(parser.parseCommand(ListTaskDoneCommand.COMMAND_WORD
                + " " + ListTaskDoneCommand.VARIANT_WORD) instanceof ListTaskDoneCommand);
    }

    @Test
    public void parseCommand_listTaskNotDone() throws Exception {
        assertTrue(parser.parseCommand(ListTaskNotDoneCommand.COMMAND_WORD
                + " " + ListTaskNotDoneCommand.VARIANT_WORD) instanceof ListTaskNotDoneCommand);
    }

    @Test
    public void parseCommand_addTask() throws Exception {
        Dashboard dashboard = new DashboardBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(DashboardUtil.getAddTaskCommand(dashboard));
        assertEquals(new AddTaskCommand(dashboard).toString(), command.toString());
    }

    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + DeleteTaskCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_DASHBOARD.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_DASHBOARD), command);
    }

    @Test
    public void parseCommand_editTask() throws Exception {
        Dashboard dashboard = new DashboardBuilder().build();
        EditTaskDescriptor descriptor = new EditDashboardDescriptorBuilder(dashboard).build();
        EditTaskCommand command = (EditTaskCommand) parser.parseCommand(EditTaskCommand.COMMAND_WORD
                + " " + EditTaskCommand.VARIANT_WORD + " " + INDEX_FIRST_DASHBOARD.getOneBased() + " "
                + DashboardUtil.getEditDashboardDescriptorDetails(descriptor));
        assertEquals(new EditTaskCommand(INDEX_FIRST_DASHBOARD, descriptor).toString(), command.toString());
    }

    @Test
    public void parseCommand_findTask() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindTaskCommand command = (FindTaskCommand) parser.parseCommand(
                FindTaskCommand.COMMAND_WORD + " " + FindTaskCommand.VARIANT_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindTaskCommand(new DashboardNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_gotoDashboard() throws Exception {
        assertTrue(parser.parseCommand(GotoTaskCommand.COMMAND_WORD
                + " " + GotoTaskCommand.VARIANT_WORD) instanceof GotoTaskCommand);
    }

    @Test
    public void parseCommand_doneTask() throws Exception {
        DoneTaskCommand command = (DoneTaskCommand) parser.parseCommand(
                DoneTaskCommand.COMMAND_WORD + " " + DoneTaskCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_DASHBOARD.getOneBased());
        assertEquals(new DoneTaskCommand(INDEX_FIRST_DASHBOARD), command);
    }

    /**  ------------------------------------  RECIPE ---------------------------------------- */

    @Test
    public void parseCommand_listRecipe() throws Exception {
        assertTrue(parser.parseCommand(ListRecipeCommand.COMMAND_WORD
                + " " + ListRecipeCommand.VARIANT_WORD) instanceof ListRecipeCommand);
        assertTrue(parser.parseCommand(ListRecipeCommand.COMMAND_WORD
                + " " + ListRecipeCommand.VARIANT_WORD + " 3") instanceof ListRecipeCommand);
    }

    @Test
    public void parseCommand_addRecipe() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        AddRecipeCommand command = (AddRecipeCommand) parser.parseCommand(RecipeUtil.getAddRecipeCommand(recipe));
        assertEquals(new AddRecipeCommand(recipe), command);
    }

    @Test
    public void parseCommand_deleteRecipe() throws Exception {
        DeleteRecipeCommand command = (DeleteRecipeCommand) parser.parseCommand(
                DeleteRecipeCommand.COMMAND_WORD + " " + DeleteRecipeCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_RECIPE.getOneBased());
        assertEquals(new DeleteRecipeCommand(INDEX_FIRST_RECIPE), command);
    }

    @Test
    public void parseCommand_editRecipe() throws Exception {
        Recipe recipe = new RecipeBuilder().build();
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder(recipe).build();
        EditRecipeCommand command = (EditRecipeCommand) parser.parseCommand(EditRecipeCommand.COMMAND_WORD
                + " " + EditRecipeCommand.VARIANT_WORD + " " + INDEX_FIRST_RECIPE.getOneBased() + " "
                + RecipeUtil.getEditRecipeDescriptorDetails(descriptor));
        assertEquals(new EditRecipeCommand(INDEX_FIRST_RECIPE, descriptor), command);
    }

    @Test
    public void parseCommand_findRecipe() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindRecipeCommand command = (FindRecipeCommand) parser.parseCommand(
                FindRecipeCommand.COMMAND_WORD + " " + DeleteRecipeCommand.VARIANT_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindRecipeCommand(new RecipeNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_clearRecipe() throws Exception {
        assertTrue(parser.parseCommand(ClearRecipeCommand.COMMAND_WORD
                + " " + ClearRecipeCommand.VARIANT_WORD) instanceof ClearRecipeCommand);
        assertTrue(parser.parseCommand(ClearRecipeCommand.COMMAND_WORD
                + " " + ClearRecipeCommand.VARIANT_WORD
                + " 3") instanceof ClearRecipeCommand);
    }

    @Test
    public void parseCommand_gotoRecipe() throws Exception {
        assertTrue(parser.parseCommand(GotoRecipeCommand.COMMAND_WORD
                + " " + GotoRecipeCommand.VARIANT_WORD) instanceof GotoRecipeCommand);
    }

    /**  ------------------------------------  MEAL PLAN ---------------------------------------- */

    @Test
    public void parseCommand_listMealPlan() throws Exception {
        assertTrue(parser.parseCommand(ListMealPlanCommand.COMMAND_WORD
                + " " + ListMealPlanCommand.VARIANT_WORD) instanceof ListMealPlanCommand);
        assertTrue(parser.parseCommand(ListMealPlanCommand.COMMAND_WORD
                + " " + ListMealPlanCommand.VARIANT_WORD + " 3") instanceof ListMealPlanCommand);
    }

    @Test
    public void parseCommand_viewMealPlan() throws Exception {
        assertTrue(parser.parseCommand(ViewMealPlanCommand.COMMAND_WORD
                + " " + ViewMealPlanCommand.VARIANT_WORD + " 3") instanceof ViewMealPlanCommand);
    }

    @Test
    public void parseCommand_addMealPlan() throws Exception {
        MealPlan recipe = new MealPlanBuilder().build();
        AddMealPlanCommand command = (AddMealPlanCommand) parser.parseCommand(MealPlanUtil
                .getAddMealPlanCommand(recipe));
        assertEquals(new AddMealPlanCommand(recipe), command);
    }

    @Test
    public void parseCommand_deleteMealPlan() throws Exception {
        DeleteMealPlanCommand command = (DeleteMealPlanCommand) parser.parseCommand(
                DeleteMealPlanCommand.COMMAND_WORD + " " + DeleteMealPlanCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_MEALPLAN.getOneBased());
        assertEquals(new DeleteMealPlanCommand(INDEX_FIRST_MEALPLAN), command);
    }

    @Test
    public void parseCommand_editMealPlan() throws Exception {
        MealPlan recipe = new MealPlanBuilder().build();
        EditMealPlanDescriptor descriptor = new EditMealPlanDescriptorBuilder(recipe).build();
        EditMealPlanCommand command = (EditMealPlanCommand) parser.parseCommand(EditMealPlanCommand.COMMAND_WORD
                + " " + EditMealPlanCommand.VARIANT_WORD + " " + INDEX_FIRST_MEALPLAN.getOneBased() + " "
                + MealPlanUtil.getEditMealPlanDescriptorDetails(descriptor));
        assertEquals(new EditMealPlanCommand(INDEX_FIRST_MEALPLAN, descriptor), command);
    }

    @Test
    public void parseCommand_findMealPlan() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindMealPlanCommand command = (FindMealPlanCommand) parser.parseCommand(
                FindMealPlanCommand.COMMAND_WORD + " " + DeleteMealPlanCommand.VARIANT_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindMealPlanCommand(new MealPlanNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_clearMealPlan() throws Exception {
        assertTrue(parser.parseCommand(ClearMealPlanCommand.COMMAND_WORD
                + " " + ClearMealPlanCommand.VARIANT_WORD) instanceof ClearMealPlanCommand);
        assertTrue(parser.parseCommand(ClearMealPlanCommand.COMMAND_WORD
                + " " + ClearMealPlanCommand.VARIANT_WORD
                + " 3") instanceof ClearMealPlanCommand);
    }

    @Test
    public void parseCommand_gotoMealPlan() throws Exception {
        assertTrue(parser.parseCommand(GotoMealPlanCommand.COMMAND_WORD
                + " " + GotoMealPlanCommand.VARIANT_WORD) instanceof GotoMealPlanCommand);
    }

    /**  ------------------------------------  PROFILE ----------------------------------------- */

    @Test
    public void parseCommand_editProfile() throws Exception {
        Person person = new PersonBuilder().build();
        EditProfileCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditProfileCommand command = (EditProfileCommand) parser.parseCommand(EditProfileCommand.COMMAND_WORD
                + " " + EditProfileCommand.VARIANT_WORD
                + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditProfileCommand(descriptor, false, false), command);
    }

    /**  ------------------------------------  PROFILE ----------------------------------------- */

    //

    /**  ------------------------------------  EXERCISE ---------------------------------------- */

    @Test
    public void parseCommand_listExercise() throws Exception {
        assertTrue(parser.parseCommand(ListExerciseCommand.COMMAND_WORD
                + " " + ListExerciseCommand.VARIANT_WORD) instanceof ListExerciseCommand);
        assertTrue(parser.parseCommand(ListExerciseCommand.COMMAND_WORD
                + " " + ListExerciseCommand.VARIANT_WORD + " 3") instanceof ListExerciseCommand);
    }

    @Test
    public void parseCommand_clearExercise() throws Exception {
        assertTrue(parser.parseCommand(ClearExerciseCommand.COMMAND_WORD
                + " " + ClearExerciseCommand.VARIANT_WORD) instanceof ClearExerciseCommand);
        assertTrue(parser.parseCommand(ClearExerciseCommand.COMMAND_WORD
                + " " + ClearExerciseCommand.VARIANT_WORD
                + " 3") instanceof ClearExerciseCommand);
    }

    @Test
    public void parseCommand_addExercise() throws Exception {
        Exercise exercise = new ExerciseBuilder().withEmptyHistory().build();
        AddExerciseCommand command = (AddExerciseCommand) parser.parseCommand(ExerciseUtil
                .getAddExerciseCommand(exercise));
        assertEquals(new AddExerciseCommand(exercise), command);
    }

    @Test
    public void parseCommand_gotoExercise() throws Exception {
        assertTrue(parser.parseCommand(GotoExerciseCommand.COMMAND_WORD
                + " " + GotoExerciseCommand.VARIANT_WORD) instanceof GotoExerciseCommand);
    }

    /**  ------------------------------------  DIARY ---------------------------------------- */

    @Test
    public void parseCommand_listDiary() throws Exception {
        assertTrue(parser.parseCommand(ListDiaryCommand.COMMAND_WORD
                + " " + ListDiaryCommand.VARIANT_WORD) instanceof ListDiaryCommand);
        assertTrue(parser.parseCommand(ListDiaryCommand.COMMAND_WORD
                + " " + ListDiaryCommand.VARIANT_WORD + " 3") instanceof ListDiaryCommand);
    }

    @Test
    public void parseCommand_findDiary() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindDiaryCommand command = (FindDiaryCommand) parser.parseCommand(
                FindDiaryCommand.COMMAND_WORD + " " + FindDiaryCommand.VARIANT_WORD
                        + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindDiaryCommand(new DiaryNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_addDiary() throws Exception {
        Diary recipe = new DiaryBuilder().build();
        AddDiaryCommand command = (AddDiaryCommand) parser.parseCommand(DiaryUtil.getAddCommand(recipe));
        assertEquals(new AddDiaryCommand(recipe), command);
    }

    @Test
    public void parseCommand_deleteDiary() throws Exception {
        DeleteDiaryCommand command = (DeleteDiaryCommand) parser.parseCommand(
                DeleteDiaryCommand.COMMAND_WORD + " " + DeleteDiaryCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_DIARY.getOneBased());
        assertEquals(new DeleteDiaryCommand(INDEX_FIRST_DIARY), command);
    }

    @Test
    public void parseCommand_editDiary() throws Exception {
        Diary diary = new DiaryBuilder().build();
        EditDiaryDescriptor descriptor = new EditDiaryDescriptorBuilder(diary).build();
        EditDiaryCommand command = (EditDiaryCommand) parser.parseCommand(EditDiaryCommand.COMMAND_WORD
                + " " + EditDiaryCommand.VARIANT_WORD + " " + INDEX_FIRST_DIARY.getOneBased() + " "
                + DiaryUtil.getEditDiaryDescriptorDetails(descriptor));
        assertEquals(new EditDiaryCommand(INDEX_FIRST_DIARY, descriptor), command);
    }

    @Test
    public void parseCommand_viewDiary() throws Exception {
        assertTrue(parser.parseCommand(ViewDiaryCommand.COMMAND_WORD
                + " " + ViewDiaryCommand.VARIANT_WORD + " 3") instanceof ViewDiaryCommand);
    }

    @Test
    public void parseCommand_gotoDiary() throws Exception {
        assertTrue(parser.parseCommand(GotoDiaryCommand.COMMAND_WORD
                + " " + GotoDiaryCommand.VARIANT_WORD) instanceof GotoDiaryCommand);
    }

    /**  ------------------------------------  PAGE ------------------------------------------ */

    @Test
    public void parseCommand_createPage() throws Exception {
        assertTrue(parser.parseCommand(CreatePageCommand.COMMAND_WORD
                + " " + CreatePageCommand.VARIANT_WORD + " n/ hello t/ whatever tp/ health desc/ hello123 i/ "
                + "/images/pho.jpg") instanceof CreatePageCommand);
    }

    @Test
    public void parseCommand_deletePage() throws Exception {
        DeletePageCommand command = (DeletePageCommand) parser.parseCommand(
                DeletePageCommand.COMMAND_WORD + " " + DeletePageCommand.VARIANT_WORD
                        + " " + INDEX_FIRST_DIARY.getOneBased() + " n/ " + TypicalDiaries.ALL_MEAT.getDiaryName());
        assertEquals(new DeletePageCommand(INDEX_FIRST_DIARY, TypicalDiaries.ALL_MEAT.getDiaryName()), command);
    }

    @Test
    public void parseCommand_addPage() throws Exception {
        assertTrue(parser.parseCommand(AddPageCommand.COMMAND_WORD
                + " " + AddPageCommand.VARIANT_WORD) instanceof AddPageCommand);
    }

    @Test
    public void parseCommand_editPage() throws Exception {
        Page page = new PageBuilder().build();
        EditPageDescriptor descriptor = new EditPageDescriptorBuilder(page).build();
        EditPageCommand command = (EditPageCommand) parser.parseCommand(EditPageCommand.COMMAND_WORD
                + " " + EditPageCommand.VARIANT_WORD + " " + INDEX_FIRST_DIARY.getOneBased() + " n/ "
                + TypicalDiaries.ALL_MEAT.getDiaryName() + " "
                + DiaryUtil.getEditPageDescriptorDetails(descriptor));
        assertEquals(new EditPageCommand(INDEX_FIRST_DIARY,
                TypicalDiaries.ALL_MEAT.getDiaryName(), descriptor), command);
    }

    /**  ------------------------------------  HEALTH ---------------------------------------- */

    @Test
    public void parseCommand_listHealth() throws Exception {
        assertTrue(parser.parseCommand(ListHealthCommand.COMMAND_WORD
                + " " + ListHealthCommand.VARIANT_WORD) instanceof ListHealthCommand);
    }

    @Test
    public void parseCommand_gotoHealth() throws Exception {
        assertTrue(parser.parseCommand(GotoHealthCommand.COMMAND_WORD
                + " " + GotoHealthCommand.VARIANT_WORD) instanceof GotoHealthCommand);
    }

    /**  ------------------------------------  COMMON ---------------------------------------- */

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_addCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("add"));
    }

    @Test
    public void parseCommand_addCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("add CS2103"));
    }

    @Test
    public void parseCommand_clearCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("clear"));
    }

    @Test
    public void parseCommand_clearCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("clear CS2103"));
    }

    @Test
    public void parseCommand_createCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("create"));
    }

    @Test
    public void parseCommand_createCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("create CS2103"));
    }

    @Test
    public void parseCommand_deleteCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE), () -> parser.parseCommand("delete"));
    }

    @Test
    public void parseCommand_deleteCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE), () -> parser.parseCommand("delete CS2103"));
    }

    @Test
    public void parseCommand_editCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("edit"));
    }

    @Test
    public void parseCommand_editCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("edit CS2103"));
    }

    @Test
    public void parseCommand_findCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("find"));
    }

    @Test
    public void parseCommand_findCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("find CS2103"));
    }

    @Test
    public void parseCommand_gotoCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("goto"));
    }

    @Test
    public void parseCommand_gotoCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, GotoCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("goto CS2103"));
    }

    @Test
    public void parseCommand_listCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), () ->
                        parser.parseCommand("list"));
    }

    @Test
    public void parseCommand_listCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("list CS2103"));
    }

    @Test
    public void parseCommand_viewCommandNoVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("view"));
    }

    @Test
    public void parseCommand_viewCommandNonExistentVariant_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("view CS2103"));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }
}
