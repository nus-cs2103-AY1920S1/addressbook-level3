package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.dashboard.DashboardRecords;
import seedu.address.model.dashboard.ReadOnlyDashboard;
import seedu.address.model.dashboard.components.Dashboard;
import seedu.address.model.diary.DiaryRecords;
import seedu.address.model.diary.ReadOnlyDiary;
import seedu.address.model.diary.components.Diary;
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;
import seedu.address.model.exercise.WorkoutPlanner;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.model.health.HealthRecords;
import seedu.address.model.health.ReadOnlyHealthRecords;
import seedu.address.model.health.components.Record;
import seedu.address.model.profile.ReadOnlyUserProfile;
import seedu.address.model.profile.UserProfile;
import seedu.address.model.profile.person.Person;
import seedu.address.model.recipe.ReadOnlyRecipeBook;
import seedu.address.model.recipe.RecipeBook;
import seedu.address.model.recipe.components.Recipe;
import seedu.address.model.dashboard.components.Dashboard;

/**
 * Represents the in-memory model of Duke Cooks data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final UserProfile userProfile;
    private final HealthRecords healthRecords;
    private final DiaryRecords diaryRecords;
    private final RecipeBook recipeBook;
    private final WorkoutPlanner workoutPlanner;
    private final DashboardRecords dashboards;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Record> filteredRecords;
    private final FilteredList<Recipe> filteredRecipes;
    private final FilteredList<Exercise> filteredExercises;
    private final FilteredList<Diary> filteredDiaries;
    private final FilteredList<Dashboard> filteredDashboards;

    /**
     * Initializes a ModelManager with the given dukeCooks and userPrefs.
     */
    public ModelManager(ReadOnlyUserProfile dukeCooks, ReadOnlyHealthRecords healthRecords,
                        ReadOnlyRecipeBook recipeBook, ReadOnlyWorkoutPlanner workoutPlanner, ReadOnlyDiary diary,
                        ReadOnlyUserPrefs userPrefs, ReadOnlyDashboard dashboards) {
        super();
        requireAllNonNull(dukeCooks, healthRecords, userPrefs, recipeBook, dashboards);

        logger.fine("Initializing with Duke Cooks: " + dukeCooks
                + "with dashboard " + dashboards
                + "with Health Records: " + healthRecords
                + "with Recipe Book: " + recipeBook
                + "with Diary Records: " + diary
                + "and user prefs " + userPrefs);

        this.userProfile = new UserProfile(dukeCooks);
        this.healthRecords = new HealthRecords(healthRecords);
        this.recipeBook = new RecipeBook(recipeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.workoutPlanner = new WorkoutPlanner(workoutPlanner);
        this.diaryRecords = new DiaryRecords(diary);
        this.dashboards = new DashboardRecords(dashboards);
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
        filteredDashboards = new FilteredList<>(this.dashboards.getDashboardList());
    }

    /**
     * Initializes a RecipeModelManager with the given userProfile and userPrefs.
     */
    public ModelManager(ReadOnlyWorkoutPlanner workoutPlanner, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(workoutPlanner, userPrefs);

        logger.fine("Initializing with Workout Planner: " + workoutPlanner
                + "and user prefs " + userPrefs);

        this.userProfile = null;
        this.healthRecords = null;
        this.recipeBook = null;
        this.userPrefs = new UserPrefs(userPrefs);
        this.workoutPlanner = new WorkoutPlanner(workoutPlanner);
        this.diaryRecords = null;
        this.dashboards = null;
        filteredPersons = null;
        filteredRecords = null;
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredExercises = null;
        filteredDiaries = null;
        filteredDashboards = null;
    }

    public ModelManager() {
        this(new UserProfile(), new HealthRecords(), new RecipeBook(),
                new WorkoutPlanner(), new DiaryRecords(), new UserPrefs(), new DashboardRecords());
    }

    public ModelManager(ReadOnlyRecipeBook recipeBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(recipeBook, userPrefs);

        logger.fine("Initializing with Workout Planner: " + recipeBook
                + "and user prefs " + userPrefs);

        this.userProfile = null;
        this.healthRecords = null;
        this.recipeBook = new RecipeBook(recipeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.workoutPlanner = null;
        this.diaryRecords = null;
        this.dashboards = null;
        filteredPersons = null;
        filteredRecords = null;
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredExercises = null;
        filteredDiaries = null;
        filteredDashboards = null;
    }

    public ModelManager(ReadOnlyDiary diaryRecord, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(diaryRecord, userPrefs);

        logger.fine("Initializing with Diary Record: " + diaryRecord
                + "and user prefs " + userPrefs);

        this.userProfile = null;
        this.healthRecords = null;
        this.recipeBook = null;
        this.workoutPlanner = null;
        this.diaryRecords = new DiaryRecords(diaryRecord);
        this.userPrefs = new UserPrefs(userPrefs);
        this.dashboards = null;
        filteredPersons = null;
        filteredRecords = null;
        filteredRecipes = null;
        filteredExercises = null;
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
        filteredDashboards = null;
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getUserProfileFilePath() {
        return userPrefs.getUserProfileFilePath();
    }

    @Override
    public void setUserProfileFilePath(Path userProfileFilePath) {
        requireNonNull(userProfileFilePath);
        userPrefs.setUserProfileFilePath(userProfileFilePath);
    }

    @Override
    public Path getHealthRecordsFilePath() {
        return userPrefs.getHealthRecordsFilePath();
    }

    @Override
    public void setHealthRecordsFilePath(Path healthRecordsFilePath) {
        requireNonNull(healthRecordsFilePath);
        userPrefs.setHealthRecordsFilePath(healthRecordsFilePath);
    }

    @Override
    public Path getRecipesFilePath() {
        return userPrefs.getRecipesFilePath();
    }

    @Override
    public void setRecipesFilePath(Path recipesFilePath) {
        requireNonNull(recipesFilePath);
        userPrefs.setRecipesFilePath(recipesFilePath);
    }

    @Override
    public Path getWorkoutPlannerFilePath() {
        return userPrefs.getExercisesFilePath();
    }

    @Override
    public void setWorkoutPlannerFilePath(Path dukeCooksFilePath) {
        requireNonNull(dukeCooksFilePath);
        userPrefs.setExercisesFilePath(dukeCooksFilePath);
    }

    @Override
    public Path getDiaryFilePath() {
        return userPrefs.getDiaryFilePath();
    }

    @Override
    public Path getDashboardFilePath() {
        return userPrefs.getDashboardFilePath();
    }

    @Override
    public void setDashboardFilePath(Path dashboardFilePath) {
        requireAllNonNull(dashboardFilePath);
        userPrefs.setDashboardFilePath(dashboardFilePath);
    }

    @Override
    public void setDiaryFilePath(Path diaryFilePath) {
        requireNonNull(diaryFilePath);
        userPrefs.setDiaryFilePath(diaryFilePath);
    }

    //=========== User Profile ================================================================================

    @Override
    public void setUserProfile(ReadOnlyUserProfile userProfile) {
        this.userProfile.resetData(userProfile);
    }

    @Override
    public ReadOnlyUserProfile getUserProfile() {
        return userProfile;
    }

    @Override
    public void addPerson(Person person) {
        userProfile.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        userProfile.setPerson(target, editedPerson);
    }


    //=========== Health Records ============================================================================

    @Override
    public void setHealthRecords(ReadOnlyHealthRecords healthRecords) {
        this.healthRecords.resetData(healthRecords);
    }

    @Override
    public ReadOnlyHealthRecords getHealthRecords() {
        return healthRecords;
    }

    @Override
    public void addRecord(Record record) {
        healthRecords.addRecord(record);
        updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        requireAllNonNull(target, editedRecord);
        healthRecords.setRecord(target, editedRecord);
    }

    //=========== Recipe Book ================================================================================

    @Override
    public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.recipeBook.resetData(recipeBook);
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipeBook.hasRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        recipeBook.removeRecipe(target);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);

        recipeBook.setRecipe(target, editedRecipe);
    }

    //=========== Workout Planner ================================================================================

    @Override
    public void setWorkoutPlanner(ReadOnlyWorkoutPlanner workoutPlanner) {
        this.workoutPlanner.resetData(workoutPlanner);
    }

    @Override
    public ReadOnlyWorkoutPlanner getWorkoutPlanner() {
        return workoutPlanner;
    }

    @Override
    public boolean hasExercise(Exercise exercise) {
        requireNonNull(exercise);
        return workoutPlanner.hasExercise(exercise);
    }

    @Override
    public void addExercise(Exercise exercise) {
        workoutPlanner.addExercise(exercise);
        updateFilteredExerciseList(PREDICATE_SHOW_ALL_EXERCISE);
    }

    @Override
    public void deleteExercise(Exercise target) {
        workoutPlanner.removePerson(target);
    }

    @Override
    public void setExercise(Exercise target, Exercise editedExercise) {
        requireAllNonNull(target, editedExercise);
        workoutPlanner.setExercise(target, editedExercise);
    }

    //=========== Diary Records ================================================================================

    @Override
    public void setDiaryRecords(ReadOnlyDiary diaryRecords) {
        this.diaryRecords.resetData(diaryRecords);
    }

    @Override
    public ReadOnlyDiary getDiaryRecords() {
        return diaryRecords;
    }

    @Override
    public boolean hasDiary(Diary diary) {
        requireNonNull(diary);
        return diaryRecords.hasDiary(diary);
    }

    @Override
    public void deleteDiary(Diary target) {
        diaryRecords.removeDiary(target);
    }

    @Override
    public void addDiary(Diary diary) {
        diaryRecords.addDiary(diary);
        updateFilteredDiaryList(PREDICATE_SHOW_ALL_DIARIES);
    }

    @Override
    public void setDiary(Diary target, Diary editedDiary) {
        requireAllNonNull(target, editedDiary);

        diaryRecords.setDiary(target, editedDiary);
    }

    //=========== Dashboard Records ================================================================================

    @Override
    public void setDashboardRecords(ReadOnlyDashboard dashboardRecords) {
        this.dashboards.resetData(dashboardRecords);
    }

    @Override
    public ReadOnlyDashboard getDashboardRecords() {
        return dashboards;
    }

    @Override
    public boolean hasDashboard(Dashboard dashboard) {
        requireNonNull(dashboard);
        return dashboards.hasDashboard(dashboard);
    }


    @Override
    public void deleteDashboard(Dashboard target) {
        dashboards.removeDashboard(target);
    }

    @Override
    public void addDashboard(Dashboard dashboard) {
        dashboards.addDashboard(dashboard);
        updateFilteredDashboardList(PREDICATE_SHOW_ALL_DASHBOARD);
    }

    @Override
    public void setDashboard(Dashboard target, Dashboard editedDashboard) {
        requireAllNonNull(target, editedDashboard);

        dashboards.setDashboard(target, editedDashboard);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedDukeCooks}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Record List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Record} backed by the internal list of
     * {@code versionedDukeCooks}
     */
    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return filteredRecords;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecords.setPredicate(predicate);
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedRecipeBook}
     */
    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return filteredRecipes;
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    //=========== Filtered Exercise List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedDukeCooks}
     */
    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return filteredExercises;
    }

    @Override
    public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
        requireNonNull(predicate);
        filteredExercises.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userProfile.equals(other.userProfile)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredExercises.equals(other.filteredExercises);
    }

    //=========== Filtered Diary List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Diary} backed by the internal list of
     * {@code versionedDiaries}
     */
    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        return filteredDiaries;
    }

    @Override
    public void updateFilteredDiaryList(Predicate<Diary> predicate) {
        requireNonNull(predicate);
        filteredDiaries.setPredicate(predicate);
    }

    @Override
    public ObservableList<Dashboard> getFilteredDashboardList() {
        return filteredDashboards;
    }

    @Override
    public void updateFilteredDashboardList(Predicate<Dashboard> predicate) {
        requireAllNonNull(predicate);
        filteredDashboards.setPredicate(predicate);
    }
}
