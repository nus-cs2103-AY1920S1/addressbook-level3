package dukecooks;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import dukecooks.commons.core.Config;
import dukecooks.commons.core.LogsCenter;
import dukecooks.commons.core.Version;
import dukecooks.commons.exceptions.DataConversionException;
import dukecooks.commons.util.ConfigUtil;
import dukecooks.commons.util.StringUtil;
import dukecooks.logic.Logic;
import dukecooks.logic.LogicManager;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.ReadOnlyUserPrefs;
import dukecooks.model.UserPrefs;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.diary.DiaryRecords;
import dukecooks.model.diary.ReadOnlyDiary;
import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.ReadOnlyHealthRecords;
import dukecooks.model.mealplan.MealPlanBook;
import dukecooks.model.mealplan.ReadOnlyMealPlanBook;
import dukecooks.model.profile.ReadOnlyUserProfile;
import dukecooks.model.profile.UserProfile;
import dukecooks.model.recipe.ReadOnlyRecipeBook;
import dukecooks.model.recipe.RecipeBook;
import dukecooks.model.util.DashboardSampleDataUtil;
import dukecooks.model.util.SampleDataUtil;
import dukecooks.model.util.SampleDiaryDataUtil;
import dukecooks.model.util.SampleMealPlanDataUtil;
import dukecooks.model.util.SamplePersonDataUtil;
import dukecooks.model.util.SampleRecipeDataUtil;
import dukecooks.model.util.SampleRecordDataUtil;
import dukecooks.model.workout.ReadOnlyWorkoutCatalogue;
import dukecooks.model.workout.WorkoutCatalogue;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.storage.JsonUserPrefsStorage;
import dukecooks.storage.Storage;
import dukecooks.storage.StorageManager;
import dukecooks.storage.UserPrefsStorage;
import dukecooks.storage.dashboard.DashboardStorage;
import dukecooks.storage.dashboard.JsonDashboardStorage;
import dukecooks.storage.diary.DiaryStorage;
import dukecooks.storage.diary.JsonDiaryStorage;
import dukecooks.storage.health.HealthRecordsStorage;
import dukecooks.storage.health.JsonHealthRecordsStorage;
import dukecooks.storage.mealplan.JsonMealPlanBookStorage;
import dukecooks.storage.mealplan.MealPlanBookStorage;
import dukecooks.storage.profile.JsonUserProfileStorage;
import dukecooks.storage.profile.UserProfileStorage;
import dukecooks.storage.recipe.JsonRecipeBookStorage;
import dukecooks.storage.recipe.RecipeBookStorage;
import dukecooks.storage.workout.JsonWorkoutCatalogueStorage;
import dukecooks.storage.workout.WorkoutCatalogueStorage;
import dukecooks.storage.workout.exercise.ExerciseCatalogueStorage;
import dukecooks.storage.workout.exercise.JsonExerciseCatalogueStorage;
import dukecooks.ui.Ui;
import dukecooks.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing DukeCooks ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        RecipeBookStorage recipeBookStorage = new JsonRecipeBookStorage(userPrefs.getRecipesFilePath());
        MealPlanBookStorage mealPlanBookStorage = new JsonMealPlanBookStorage(userPrefs.getMealPlansFilePath());
        UserProfileStorage userProfileStorage = new JsonUserProfileStorage(userPrefs.getUserProfileFilePath());
        HealthRecordsStorage healthRecordsStorage = new JsonHealthRecordsStorage(userPrefs.getHealthRecordsFilePath());
        ExerciseCatalogueStorage exerciseCatalogueStorage = new JsonExerciseCatalogueStorage(userPrefs
                .getExercisesFilePath());
        WorkoutCatalogueStorage workoutCatalogueStorage = new JsonWorkoutCatalogueStorage(userPrefs
                .getWorkoutFilePath());
        DiaryStorage diaryStorage = new JsonDiaryStorage(userPrefs.getDiaryFilePath());
        DashboardStorage dashboardStorage = new JsonDashboardStorage(userPrefs.getDashboardFilePath());
        storage = new StorageManager(userProfileStorage, healthRecordsStorage, recipeBookStorage, mealPlanBookStorage,
                exerciseCatalogueStorage, workoutCatalogueStorage, diaryStorage, dashboardStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s duke cooks and {@code userPrefs}. <br>
     * The data from the sample duke cooks will be used instead if {@code storage}'s Duke Cooks is not found,
     * or an empty dukeCooks will be used instead if errors occur when reading {@code storage}'s Duke Cooks.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyUserProfile initialDukeCooks;
        initialDukeCooks = initUserProfile(storage);

        ReadOnlyRecipeBook initialRecipeBook;
        initialRecipeBook = initRecipeBook(storage);

        ReadOnlyMealPlanBook initialMealPlanBook;
        initialMealPlanBook = initMealPlanBook(storage);

        ReadOnlyHealthRecords initialHealthRecords;
        initialHealthRecords = initHealthRecords(storage);

        ReadOnlyExerciseCatalogue initialExerciseCatalogue;
        initialExerciseCatalogue = initExerciseCatalogue(storage);

        ReadOnlyWorkoutCatalogue initialWorkoutCatalogue;
        initialWorkoutCatalogue = initWorkoutCatalogue(storage);

        ReadOnlyDiary initialDiary;
        initialDiary = initDiary(storage);

        ReadOnlyDashboard initialDashboard;
        initialDashboard = initDashboard(storage);

        return new ModelManager(initialDukeCooks, initialDashboard, initialHealthRecords, initialRecipeBook,
                initialMealPlanBook, initialExerciseCatalogue, initialWorkoutCatalogue, initialDiary, userPrefs);
    }

    /**
     * Returns a {@code ReadOnlyUserProfile} with the data from {@code storage}'s UserProfile. <br>
     * The data from the sample UserProfile will be used instead if {@code storage}'s persons is not found,
     * or an empty DukeCook will be used instead if errors occur when reading {@code storage}'s UserProfile.
     */
    private ReadOnlyUserProfile initUserProfile(Storage storage) {
        Optional<ReadOnlyUserProfile> dukeCooksOptional;
        ReadOnlyUserProfile initialData;

        try {
            dukeCooksOptional = storage.readUserProfile();
            if (!dukeCooksOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample UserProfile");
            }
            initialData = dukeCooksOptional.orElseGet(SamplePersonDataUtil::getSampleUserProfile);
            //initialData = recipeBookOptional.orElseGet(SampleRecipeDataUtil::getSampleRecipeBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new UserProfile();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new UserProfile();
        }

        return initialData;
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s duke cooks and {@code userPrefs}. <br>
     * The data from the sample duke cooks will be used instead if {@code storage}'s Duke Cooks is not found,
     * or an empty dukeCooks will be used instead if errors occur when reading {@code storage}'s Duke Cooks.
     */
    private ReadOnlyExerciseCatalogue initExerciseCatalogue(Storage storage) {
        Optional<ReadOnlyExerciseCatalogue> exerciseCatalogueOptional;
        ReadOnlyExerciseCatalogue initialData;

        try {
            exerciseCatalogueOptional = storage.readExerciseCatalogue();
            if (!exerciseCatalogueOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample DukeCooks");
            }
            initialData = exerciseCatalogueOptional.orElseGet(SampleDataUtil::getSampleExerciseCatalogue);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new ExerciseCatalogue();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new ExerciseCatalogue();
        }

        return initialData;
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s duke cooks and {@code userPrefs}. <br>
     * The data from the sample duke cooks will be used instead if {@code storage}'s Duke Cooks is not found,
     * or an empty dukeCooks will be used instead if errors occur when reading {@code storage}'s Duke Cooks.
     */
    private ReadOnlyWorkoutCatalogue initWorkoutCatalogue(Storage storage) {
        Optional<ReadOnlyWorkoutCatalogue> workoutCatalogueOptional;
        ReadOnlyWorkoutCatalogue initialData;

        try {
            workoutCatalogueOptional = storage.readWorkoutCatalogue();
            if (!workoutCatalogueOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample Workout Catalogue");
            }
            initialData = workoutCatalogueOptional.orElseGet(SampleDataUtil::getSampleWorkoutCatalogue);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new WorkoutCatalogue();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new WorkoutCatalogue();
        }

        return initialData;
    }

    /**
     * Returns a {@code ReadOnlyRecipeBook} with the data from {@code storage}'s UserProfile. <br>
     * The data from the sample Recipe Book will be used instead if {@code storage}'s persons is not found,
     * or an empty RecipeBook will be used instead if errors occur when reading {@code storage}'s RecipeBook.
     */
    private ReadOnlyRecipeBook initRecipeBook(Storage storage) {
        Optional<ReadOnlyRecipeBook> recipeBookOptional;
        ReadOnlyRecipeBook initialData;

        try {
            recipeBookOptional = storage.readRecipeBook();
            if (!recipeBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample RecipeBook");
            }
            initialData = recipeBookOptional.orElseGet(SampleRecipeDataUtil::getSampleRecipeBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new RecipeBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new RecipeBook();
        }

        return initialData;
    }

    /**
     * Returns a {@code ReadOnlyMealPlanBook} with the data from {@code storage}'s UserProfile. <br>
     * The data from the sample MealPlanBook will be used instead if {@code storage}'s persons is not found,
     * or an empty MealPlanBook will be used instead if errors occur when reading {@code storage}'s MealPlanBook.
     */
    private ReadOnlyMealPlanBook initMealPlanBook(Storage storage) {
        Optional<ReadOnlyMealPlanBook> mealPlanBookOptional;
        ReadOnlyMealPlanBook initialData;

        try {
            mealPlanBookOptional = storage.readMealPlanBook();
            if (!mealPlanBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample MealPlanBook");
            }
            initialData = mealPlanBookOptional.orElseGet(SampleMealPlanDataUtil::getSampleMealPlanBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new MealPlanBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new MealPlanBook();
        }

        return initialData;
    }

    /**
     * Returns a {@code ReadOnlyHealthRecords} with the data from {@code storage}'s health records. <br>
     * The data from the sample health records will be used instead if {@code storage}'s Health records is not found,
     * or an empty healthRecords will be used instead if errors occur when reading {@code storage}'s Health Records.
     */
    private ReadOnlyHealthRecords initHealthRecords(Storage storage) {
        Optional<ReadOnlyHealthRecords> healthRecordsOptional;
        ReadOnlyHealthRecords initialData;

        try {
            healthRecordsOptional = storage.readHealthRecords();
            if (!healthRecordsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample Health Records");
            }
            initialData = healthRecordsOptional.orElseGet(SampleRecordDataUtil::getSampleHealthRecords);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Health Records");
            initialData = new HealthRecords();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Health Records");
            initialData = new HealthRecords();
        }

        return initialData;
    }

    /**
     * Returns a {@code ReadOnlyDiary} with the data from {@code storage}'s Diary Records. <br>
     * The data from the sample diary will be used instead if {@code storage}'s diary records are not found,
     * or an empty Diary Record will be used instead if errors occur when reading {@code storage}'s Diary Records.
     */
    private ReadOnlyDiary initDiary(Storage storage) {
        Optional<ReadOnlyDiary> diaryRecordsOptional;
        ReadOnlyDiary initialData;

        try {
            diaryRecordsOptional = storage.readDiary();
            if (!diaryRecordsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample DiaryRecords");
            }
            initialData = diaryRecordsOptional.orElseGet(SampleDiaryDataUtil::getSampleDiaryRecords);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new DiaryRecords();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new DiaryRecords();
        }

        return initialData;
    }

    /**
     * Returns a {@code ReadOnlyDashboard} with the data from {@code storage}'s Dashboard Records. <br>
     * The data from the sample dashboard will be used instead if {@code storage}'s dashboard records are not found,
     * or an empty Dashboard Record will be used instead if errors occur when reading
     * {@code storage}'s Dashboard Records.
     */
    private ReadOnlyDashboard initDashboard(Storage storage) {
        Optional<ReadOnlyDashboard> dashboardRecordsOptional;
        ReadOnlyDashboard initialData;

        try {
            dashboardRecordsOptional = storage.readDashboard();
            if (!dashboardRecordsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample Dashboard");
            }
            initialData = dashboardRecordsOptional.orElseGet(DashboardSampleDataUtil::getSampleDashboardRecords);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new DashboardRecords();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new DashboardRecords();
        }

        return initialData;
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting DukeCooks " + MainApp.VERSION);
        primaryStage.setFullScreen(true);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Duke Cooks ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
