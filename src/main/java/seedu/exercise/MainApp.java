package seedu.exercise;

import static seedu.exercise.model.util.DefaultPropertyManagerUtil.getDefaultPropertyManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.exercise.commons.core.Config;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.Version;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.util.ConfigUtil;
import seedu.exercise.commons.util.StringUtil;
import seedu.exercise.logic.Logic;
import seedu.exercise.logic.LogicManager;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.RegimeBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.exercise.PropertyManager;
import seedu.exercise.model.util.DefaultPropertyManagerUtil;
import seedu.exercise.model.util.SampleDataUtil;
import seedu.exercise.storage.ExerciseBookStorage;
import seedu.exercise.storage.JsonExerciseBookStorage;
import seedu.exercise.storage.JsonPropertyManagerStorage;
import seedu.exercise.storage.JsonRegimeBookStorage;
import seedu.exercise.storage.JsonUserPrefsStorage;
import seedu.exercise.storage.PropertyManagerStorage;
import seedu.exercise.storage.RegimeBookStorage;
import seedu.exercise.storage.Storage;
import seedu.exercise.storage.StorageManager;
import seedu.exercise.storage.UserPrefsStorage;
import seedu.exercise.ui.Ui;
import seedu.exercise.ui.UiManager;

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
        logger.info("=============================[ Initializing ExerciseBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ExerciseBookStorage exerciseBookStorage = new JsonExerciseBookStorage(userPrefs.getExerciseBookFilePath());
        RegimeBookStorage regimeBookStorage = new JsonRegimeBookStorage(userPrefs.getRegimeBookFilePath());
        PropertyManagerStorage propertyManagerStorage =
            new JsonPropertyManagerStorage(userPrefs.getPropertyManagerFilePath());
        storage = new StorageManager(exerciseBookStorage, regimeBookStorage, userPrefsStorage, propertyManagerStorage);


        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s exercise book and {@code userPrefs}. <br>
     * The data from the sample exercise book will be used instead if {@code storage}'s exercise book is not found,
     * or an empty exercise book will be used instead if errors occur when reading {@code storage}'s exercise book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyExerciseBook initialExerciseData = getInitialExerciseData(storage);
        ReadOnlyRegimeBook initialRegimeData = getInitialRegimeData(storage);
        PropertyManager initialPropertyManager = getInitialPropertyManager(storage);
        return new ModelManager(initialExerciseData, initialRegimeData, userPrefs, initialPropertyManager);
    }

    private ReadOnlyExerciseBook getInitialExerciseData(Storage storage) {
        Optional<ReadOnlyExerciseBook> exerciseBookOptional;
        ReadOnlyExerciseBook initialExerciseData;

        try {
            exerciseBookOptional = storage.readExerciseBook();
            if (exerciseBookOptional.isEmpty()) {
                logger.info("Data file for ExerciseBook not found. Will be starting with a sample ExerciseBook");
            }
            initialExerciseData = exerciseBookOptional.orElseGet(SampleDataUtil::getSampleExerciseBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ExerciseBook");
            initialExerciseData = new ExerciseBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExerciseBook");
            initialExerciseData = new ExerciseBook();
        }
        return initialExerciseData;
    }

    private ReadOnlyRegimeBook getInitialRegimeData(Storage storage) {
        Optional<ReadOnlyRegimeBook> regimeBookOptional;
        ReadOnlyRegimeBook initialRegimeData;

        try {
            regimeBookOptional = storage.readRegimeBook();
            if (regimeBookOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample RegimeBook");
            }
            initialRegimeData = regimeBookOptional.orElseGet(SampleDataUtil::getSampleRegimeBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RegimeBook");
            initialRegimeData = new RegimeBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RegimeBook");
            initialRegimeData = new RegimeBook();
        }
        return initialRegimeData;
    }

    private PropertyManager getInitialPropertyManager(Storage storage) {
        Optional<PropertyManager> propertyManagerOptional;
        PropertyManager initialPropertyManager;

        try {
            propertyManagerOptional = storage.readPropertyManager();
            if (propertyManagerOptional.isEmpty()) {
                logger.info("Data for PropertyManager not found. Will be starting with a"
                    + " default PropertyManager");
            }
            initialPropertyManager =
                propertyManagerOptional.orElseGet(DefaultPropertyManagerUtil::getDefaultPropertyManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with "
                + " a default PropertyManager");
            initialPropertyManager = getDefaultPropertyManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with "
                + "a default PropertyManager");
            initialPropertyManager = getDefaultPropertyManager();
        }
        return initialPropertyManager;
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ExerciseBook");
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
        logger.info("Starting ExerciseBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Exercise Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
