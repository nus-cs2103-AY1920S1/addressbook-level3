package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.HealthRecords;
import seedu.address.model.DiaryRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyHealthRecords;
import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.ReadOnlyWorkoutPlanner;
import seedu.address.model.ReadOnlyDiary;
import seedu.address.model.RecipeBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserProfile;
import seedu.address.model.WorkoutPlanner;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleRecipeDataUtil;
import seedu.address.model.util.DiarySampleDataUtil;
import seedu.address.storage.HealthRecordsStorage;
import seedu.address.storage.DiaryStorage;
import seedu.address.storage.JsonHealthRecordsStorage;
import seedu.address.storage.JsonRecipeBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.JsonUserProfileStorage;
import seedu.address.storage.JsonWorkoutPlannerStorage;
import seedu.address.storage.JsonDiaryStorage;
import seedu.address.storage.RecipeBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.UserProfileStorage;
import seedu.address.storage.WorkoutPlannerStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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
        UserProfileStorage userProfileStorage = new JsonUserProfileStorage(userPrefs.getUserProfileFilePath());
        HealthRecordsStorage healthRecordsStorage = new JsonHealthRecordsStorage(userPrefs.getHealthRecordsFilePath());
        WorkoutPlannerStorage workoutPlannerStorage = new JsonWorkoutPlannerStorage(userPrefs.getExercisesFilePath());
        DiaryStorage diaryStorage = new JsonDiaryStorage(userPrefs.getDiaryFilePath());
        storage = new StorageManager(userProfileStorage, healthRecordsStorage, recipeBookStorage,
                workoutPlannerStorage, diaryStorage, userPrefsStorage);

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

        ReadOnlyHealthRecords initialHealthRecords;
        initialHealthRecords = initHealthRecords(storage);

        ReadOnlyWorkoutPlanner initialWorkoutPlanner;
        initialWorkoutPlanner = initWorkoutPlanner(storage);

        ReadOnlyDiary initialDiary;
        initialDiary = initDiary(storage);

        return new ModelManager(initialDukeCooks, initialHealthRecords, initialRecipeBook, initialWorkoutPlanner,
                initialDiary, userPrefs);
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
            initialData = dukeCooksOptional.orElseGet(SampleDataUtil::getSampleUserProfile);
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
    private ReadOnlyWorkoutPlanner initWorkoutPlanner(Storage storage) {
        Optional<ReadOnlyWorkoutPlanner> workoutPlannerOptional;
        ReadOnlyWorkoutPlanner initialData;

        try {
            workoutPlannerOptional = storage.readWorkoutPlanner();
            if (!workoutPlannerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with sample DukeCooks");
            }
            initialData = workoutPlannerOptional.orElseGet(SampleDataUtil::getSampleWorkoutPlanner);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new WorkoutPlanner();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new WorkoutPlanner();
        }

        return initialData;
    }

    /**
     * Returns a {@code ReadOnlyUserProfile} with the data from {@code storage}'s UserProfile. <br>
     * The data from the sample UserProfile will be used instead if {@code storage}'s persons is not found,
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
            initialData = healthRecordsOptional.orElseGet(SampleDataUtil::getSampleHealthRecords);
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
            initialData = diaryRecordsOptional.orElseGet(DiarySampleDataUtil::getSampleDiaryRecords);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DukeCooks");
            initialData = new DiaryRecords();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DukeCooks");
            initialData = new DiaryRecords();
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
