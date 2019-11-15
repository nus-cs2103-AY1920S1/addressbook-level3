package dukecooks.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import dukecooks.commons.core.GuiSettings;
import dukecooks.commons.core.LogsCenter;
import dukecooks.logic.commands.Command;
import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.logic.parser.DukeCooksParser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.Model;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.health.components.Record;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.mealplan.components.MealPlan;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.person.Person;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.components.Recipe;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.Workout;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.storage.Storage;
import javafx.collections.ObservableList;


/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final DukeCooksParser dukeCooksParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        dukeCooksParser = new DukeCooksParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = dukeCooksParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveExerciseCatalogue(model.getExerciseCatalogue());
            storage.saveWorkoutCatalogue(model.getWorkoutCatalogue());
            storage.saveUserProfile(model.getUserProfile());
            storage.saveRecipeBook(model.getRecipeBook());
            storage.saveMealPlanBook(model.getMealPlanBook());
            storage.saveHealthRecords(model.getHealthRecords());
            storage.saveDiary(model.getDiaryRecords());
            storage.saveDashboard(model.getDashboardRecords());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public void updateWorkoutStorage() {
        try {
            storage.saveWorkoutCatalogue(model.getWorkoutCatalogue());
            storage.saveExerciseCatalogue(model.getExerciseCatalogue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReadOnlyExerciseCatalogue getDukeCooks() {
        return model.getExerciseCatalogue();
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return model.getRecipeBook();
    }

    @Override
    public ReadOnlyMealPlanBook getMealPlanBook() {
        return model.getMealPlanBook();
    }

    @Override
    public ReadOnlyDashboard getDashboardRecords() {
        return model.getDashboardRecords();
    }

    @Override
    public ReadOnlyUserProfile getUserProfile() {
        return model.getUserProfile();
    }

    @Override
    public ReadOnlyDiary getDiaryRecords() {
        return model.getDiaryRecords();
    }

    @Override
    public ReadOnlyWorkoutCatalogue getWorkoutCatalogue() {
        return model.getWorkoutCatalogue();
    }

    @Override
    public ObservableList<Exercise> getFilteredExerciseList() {
        return model.getFilteredExerciseList();
    }

    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return model.getFilteredWorkoutList();
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return model.getFilteredRecipeList();
    }

    @Override
    public ObservableList<MealPlan> getFilteredMealPlanList() {
        return model.getFilteredMealPlanList();
    }

    @Override
    public ObservableList<Record> getFilteredRecordList() {
        return model.getFilteredRecordList();
    }

    @Override
    public ObservableList<Dashboard> getFilteredDashboardList() {
        return model.getFilteredDashboardList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Diary> getFilteredDiaryList() {
        return model.getFilteredDiaryList();
    }

    @Override
    public Path getUserProfileFilePath() {
        return model.getUserProfileFilePath();
    }

    @Override
    public Path getHealthRecordsFilePath() {
        return model.getHealthRecordsFilePath();
    }

    @Override
    public Path getRecipesFilePath() {
        return model.getRecipesFilePath();
    }

    @Override
    public Path getMealPlansFilePath() {
        return model.getMealPlansFilePath();
    }

    @Override
    public Path getDiaryFilePath() {
        return model.getDiaryFilePath();
    }

    @Override
    public Path getDashboardFilePath() {
        return model.getDashboardFilePath();
    }

    @Override
    public Path getWorkoutCatalogueFilePath() {
        return model.getWorkoutPlannerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
