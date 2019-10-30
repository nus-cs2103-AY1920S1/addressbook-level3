package dukecooks.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import dukecooks.commons.core.GuiSettings;
import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.util.CollectionUtil;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.health.components.Record;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.profile.person.Person;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.workout.ReadOnlyWorkoutPlanner;
import dukecooks.model.workout.WorkoutPlanner;
import dukecooks.model.workout.exercise.components.Exercise;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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
    private final MealPlanBook mealPlanBook;
    private final WorkoutPlanner workoutPlanner;
    private final DashboardRecords dashboard;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Record> filteredRecords;
    private final FilteredList<Recipe> filteredRecipes;
    private final FilteredList<MealPlan> filteredMealPlans;
    private final FilteredList<Exercise> filteredExercises;
    private final FilteredList<Diary> filteredDiaries;
    private final FilteredList<Dashboard> filteredDashboard;

    private final UserProfile defaultProfile = new UserProfile();
    private final HealthRecords defaultHealthRecords = new HealthRecords();
    private final DiaryRecords defaultDiaryRecords = new DiaryRecords();
    private final RecipeBook defaultRecipeBook = new RecipeBook();
    private final MealPlanBook defaultMealPlanBook = new MealPlanBook();
    private final WorkoutPlanner defaultWorkoutPlanner = new WorkoutPlanner();
    private final DashboardRecords defaultDashboardRecord = new DashboardRecords();

    /**
     * Initializes a ModelManager with the given dukeCooks and userPrefs.
     */
    public ModelManager(ReadOnlyUserProfile dukeCooks, ReadOnlyDashboard dashboard, ReadOnlyHealthRecords healthRecords,
                        ReadOnlyRecipeBook recipeBook, ReadOnlyMealPlanBook mealPlanBook,
                        ReadOnlyWorkoutPlanner workoutPlanner, ReadOnlyDiary diary, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(dukeCooks, dashboard, healthRecords, userPrefs, recipeBook);

        logger.fine("Initializing with Duke Cooks: " + dukeCooks
                + "with dashboard " + dashboard
                + "with Health Records: " + healthRecords
                + "with Recipe Book: " + recipeBook
                + "with Diary Records: " + diary
                + "and user prefs " + userPrefs);

        this.dashboard = new DashboardRecords(dashboard);
        this.userProfile = new UserProfile(dukeCooks);
        this.healthRecords = new HealthRecords(healthRecords);
        this.recipeBook = new RecipeBook(recipeBook);
        this.mealPlanBook = new MealPlanBook(mealPlanBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.workoutPlanner = new WorkoutPlanner(workoutPlanner);
        this.diaryRecords = new DiaryRecords(diary);
        filteredDashboard = new FilteredList<>(this.dashboard.getDashboardList());
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredMealPlans = new FilteredList<>(this.mealPlanBook.getMealPlanList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
    }

    /**
     * Initializes a WorkoutModelManager with the given userProfile and userPrefs.
     */
    public ModelManager(ReadOnlyWorkoutPlanner workoutPlanner, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(workoutPlanner, userPrefs);

        logger.fine("Initializing with Workout Planner: " + workoutPlanner
                + "and user prefs " + userPrefs);

        this.dashboard = defaultDashboardRecord;
        this.userProfile = defaultProfile;
        this.healthRecords = defaultHealthRecords;
        this.recipeBook = defaultRecipeBook;
        this.mealPlanBook = defaultMealPlanBook;
        this.userPrefs = new UserPrefs(userPrefs);
        this.workoutPlanner = new WorkoutPlanner(workoutPlanner);
        this.diaryRecords = defaultDiaryRecords;
        filteredDashboard = new FilteredList<>(this.dashboard.getDashboardList());
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredMealPlans = new FilteredList<>(this.mealPlanBook.getMealPlanList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
    }

    public ModelManager() {
        this(new UserProfile(), new DashboardRecords(), new HealthRecords(), new RecipeBook(), new MealPlanBook(),
                new WorkoutPlanner(), new DiaryRecords(), new UserPrefs());
    }

    public ModelManager(ReadOnlyRecipeBook recipeBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(recipeBook, userPrefs);

        logger.fine("Initializing with Recipe Book: " + recipeBook
                + "and user prefs " + userPrefs);

        this.dashboard = defaultDashboardRecord;
        this.userProfile = defaultProfile;
        this.healthRecords = defaultHealthRecords;
        this.recipeBook = new RecipeBook(recipeBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.mealPlanBook = defaultMealPlanBook;
        this.workoutPlanner = defaultWorkoutPlanner;
        this.diaryRecords = defaultDiaryRecords;
        filteredDashboard = new FilteredList<>(this.dashboard.getDashboardList());
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredMealPlans = new FilteredList<>(this.mealPlanBook.getMealPlanList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
    }

    public ModelManager(ReadOnlyMealPlanBook mealPlanBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(mealPlanBook, userPrefs);

        logger.fine("Initializing with Meal Plan Book: " + mealPlanBook
                + "and user prefs " + userPrefs);

        this.dashboard = defaultDashboardRecord;
        this.userProfile = defaultProfile;
        this.healthRecords = defaultHealthRecords;
        this.recipeBook = defaultRecipeBook;
        this.mealPlanBook = new MealPlanBook(mealPlanBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.workoutPlanner = defaultWorkoutPlanner;
        this.diaryRecords = defaultDiaryRecords;
        filteredDashboard = new FilteredList<>(this.dashboard.getDashboardList());
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredMealPlans = new FilteredList<>(this.mealPlanBook.getMealPlanList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
    }

    public ModelManager(ReadOnlyDiary diaryRecord, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(diaryRecord, userPrefs);

        logger.fine("Initializing with Diary Record: " + diaryRecord
                + "and user prefs " + userPrefs);

        this.dashboard = defaultDashboardRecord;
        this.userProfile = defaultProfile;
        this.healthRecords = defaultHealthRecords;
        this.recipeBook = defaultRecipeBook;
        this.mealPlanBook = defaultMealPlanBook;
        this.workoutPlanner = defaultWorkoutPlanner;
        this.diaryRecords = new DiaryRecords(diaryRecord);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredDashboard = new FilteredList<>(this.dashboard.getDashboardList());
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredMealPlans = new FilteredList<>(this.mealPlanBook.getMealPlanList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
    }

    public ModelManager(ReadOnlyDashboard dashboardRecord, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(dashboardRecord, userPrefs);

        logger.fine("Initializing with Dashboard Record: " + dashboardRecord
                + "and user prefs " + userPrefs);

        this.dashboard = new DashboardRecords(dashboardRecord);
        this.userProfile = defaultProfile;
        this.healthRecords = defaultHealthRecords;
        this.recipeBook = defaultRecipeBook;
        this.mealPlanBook = defaultMealPlanBook;
        this.workoutPlanner = defaultWorkoutPlanner;
        this.diaryRecords = defaultDiaryRecords;
        this.userPrefs = new UserPrefs(userPrefs);
        filteredDashboard = new FilteredList<>(this.dashboard.getDashboardList());
        filteredPersons = new FilteredList<>(this.userProfile.getUserProfileList());
        filteredRecords = new FilteredList<>(this.healthRecords.getHealthRecordsList());
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        filteredMealPlans = new FilteredList<>(this.mealPlanBook.getMealPlanList());
        filteredExercises = new FilteredList<>(this.workoutPlanner.getExerciseList());
        filteredDiaries = new FilteredList<>(this.diaryRecords.getDiaryList());
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
    public Path getMealPlansFilePath() {
        return userPrefs.getMealPlansFilePath();
    }

    @Override
    public void setMealPlansFilePath(Path mealPlansFilePath) {
        requireNonNull(mealPlansFilePath);
        userPrefs.setMealPlansFilePath(mealPlansFilePath);
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
    public void setDiaryFilePath(Path diaryFilePath) {
        requireNonNull(diaryFilePath);
        userPrefs.setDiaryFilePath(diaryFilePath);
    }

    @Override
    public Path getDashboardFilePath() {
        return userPrefs.getDashboardFilePath();
    }

    @Override
    public void setDashboardFilePath(Path dashboardFilePath) {
        CollectionUtil.requireAllNonNull(dashboardFilePath);
        userPrefs.setDashboardFilePath(dashboardFilePath);
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
        CollectionUtil.requireAllNonNull(target, editedPerson);
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
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return healthRecords.hasRecord(record);
    }

    @Override
    public void deleteRecord(Record record) {
        healthRecords.removeRecord(record);
    }


    @Override
    public void addRecord(Record record) {
        healthRecords.addRecord(record);
    }

    @Override
    public void setRecord(Record target, Record editedRecord) {
        CollectionUtil.requireAllNonNull(target, editedRecord);
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
        CollectionUtil.requireAllNonNull(target, editedRecipe);

        recipeBook.setRecipe(target, editedRecipe);
    }

    @Override
    public Recipe retrieveRecipe(Recipe recipe) {
        requireNonNull(recipe);

        return recipeBook.retrieveRecipe(recipe);
    }

    //=========== Meal Plan Book ================================================================================

    @Override
    public void setMealPlanBook(ReadOnlyMealPlanBook mealPlanBook) {
        this.mealPlanBook.resetData(mealPlanBook);
    }

    @Override
    public ReadOnlyMealPlanBook getMealPlanBook() {
        return mealPlanBook;
    }

    @Override
    public boolean hasMealPlan(MealPlan mealPlan) {
        requireNonNull(mealPlan);
        return mealPlanBook.hasMealPlan(mealPlan);
    }

    @Override
    public void deleteMealPlan(MealPlan target) {
        mealPlanBook.removeMealPlan(target);
    }

    @Override
    public void addMealPlan(MealPlan mealPlan) {
        mealPlanBook.addMealPlan(mealPlan);
        updateFilteredMealPlanList(PREDICATE_SHOW_ALL_MEALPLANS);
    }

    @Override
    public void setMealPlan(MealPlan target, MealPlan editedMealPlan) {
        CollectionUtil.requireAllNonNull(target, editedMealPlan);

        mealPlanBook.setMealPlan(target, editedMealPlan);
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
        CollectionUtil.requireAllNonNull(target, editedExercise);
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
        CollectionUtil.requireAllNonNull(target, editedDiary);

        diaryRecords.setDiary(target, editedDiary);
    }

    //=========== Dashboard Records ================================================================================

    @Override
    public void setDashboardRecords(ReadOnlyDashboard dashboardRecords) {
        this.dashboard.resetData(dashboardRecords);
    }

    @Override
    public ReadOnlyDashboard getDashboardRecords() {
        return dashboard;
    }

    @Override
    public boolean hasDashboard(Dashboard dash) {
        requireNonNull(dashboard);
        return dashboard.hasDashboard(dash);
    }


    @Override
    public void deleteDashboard(Dashboard target) {
        dashboard.removeDashboard(target);
    }

    @Override
    public void addDashboard(Dashboard dash) {
        dashboard.addDashboard(dash);
        updateFilteredDashboardList(PREDICATE_SHOW_ALL_DASHBOARD);
    }

    @Override
    public void setDashboard(Dashboard target, Dashboard editedDashboard) {
        CollectionUtil.requireAllNonNull(target, editedDashboard);

        dashboard.setDashboard(target, editedDashboard);
    }

    @Override
    public void doneDashboard(Dashboard target) {
        dashboard.doneDashboard(target);
    }

    @Override
    public boolean checkForPrize(List<Dashboard> l) {
        return dashboard.checkDashboard(l);
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



    //=========== Filtered Meal Plan List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code MealPlan} backed by the internal list of
     * {@code versionedMealPlanBook}
     */
    @Override
    public ObservableList<MealPlan> getFilteredMealPlanList() {
        return filteredMealPlans;
    }

    @Override
    public void updateFilteredMealPlanList(Predicate<MealPlan> predicate) {
        requireNonNull(predicate);
        filteredMealPlans.setPredicate(predicate);
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
                && dashboard.equals(other.dashboard)
                && userPrefs.equals(other.userPrefs)
                && recipeBook.equals(other.recipeBook)
                && healthRecords.equals(other.healthRecords)
                && diaryRecords.equals(other.diaryRecords)
                && workoutPlanner.equals(other.workoutPlanner)
                && filteredDashboard.equals(other.filteredDashboard)
                && filteredPersons.equals(other.filteredPersons)
                && filteredRecords.equals(other.filteredRecords)
                && filteredRecipes.equals(other.filteredRecipes)
                && filteredExercises.equals(other.filteredExercises)
                && filteredDiaries.equals(other.filteredDiaries);
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

    //=========== Filtered Dashboard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Dashboard} backed by the internal list of
     * {@code versionedDashboard}
     */
    @Override
    public ObservableList<Dashboard> getFilteredDashboardList() {
        return filteredDashboard;
    }

    @Override
    public void updateFilteredDashboardList(Predicate<Dashboard> predicate) {
        CollectionUtil.requireAllNonNull(predicate);
        filteredDashboard.setPredicate(predicate);
    }
}
