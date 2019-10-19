package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.diary.components.Diary;
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.health.ReadOnlyHealthRecords;
import seedu.address.model.health.components.Record;
import seedu.address.model.profile.ReadOnlyUserProfile;
import seedu.address.model.profile.person.Person;
import seedu.address.model.recipe.ReadOnlyRecipeBook;
import seedu.address.model.recipe.components.Recipe;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    // ==================================== User Profile ============================================================

    @Override
    public Path getUserProfileFilePath() {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void setUserProfileFilePath(Path userProfileFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserProfile(ReadOnlyUserProfile userProfile) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserProfile getUserProfile() {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    // ======================================= Health Records ==============================================
    @Override
    public Path getHealthRecordsFilePath() {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void setHealthRecordsFilePath(Path healthRecordsFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setHealthRecords(ReadOnlyHealthRecords healthRecords) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyHealthRecords getHealthRecords() {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void addRecord(Record record) {
        throw new AssertionError(
                "This method should not be called.");
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Record> getFilteredRecordList() {
        throw new AssertionError("This method should not be called.");
    }

    // ======================================= Recipe Book ==============================================
    @Override
    public Path getRecipesFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRecipesFilePath(Path recipesFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRecipe(Recipe recipe) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRecipe(Recipe target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    // ======================================= Workout Planner ==============================================
    @Override
    public Path getWorkoutPlannerFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setWorkoutPlannerFilePath(Path workoutPlannerFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setWorkoutPlanner(ReadOnlyWorkoutPlanner workoutPlanner) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyWorkoutPlanner getWorkoutPlanner() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addExercise(Exercise exercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteExercise(Exercise exercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    // ======================================= Diary Records ==============================================

    @Override
    public void setDiaryFilePath(Path diaryFilePath) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public Path getDiaryFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addDiary(Diary diary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDiary(Diary diary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDiary(Diary diary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDiary getDiaryRecords() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDiary(Diary target, Diary editedDiary) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDiaryRecords(ReadOnlyDiary diaryRecords) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredDiaryList(Predicate<Diary> predicate) {
        throw new AssertionError("This method should not be called.");
    }


    // ======================================= Dashboard Records ==============================================

    @Override
    public void setDashboardRecords(ReadOnlyDashboard dashboardRecords) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDashboardFilePath(Path dashboardFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getDashboardFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyDashboard getDashboardRecords() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDashboard(Dashboard dashboard) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteDashboard(Dashboard target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addDashboard(Dashboard dashboard) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDashboard(Dashboard target, Dashboard editedDashboard) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Dashboard> getFilteredDashboardList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredDashboardList(Predicate<Dashboard> predicate) {
        throw new AssertionError("This method should not be called.");
    }
}
