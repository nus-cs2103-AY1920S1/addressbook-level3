package calofit;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import calofit.commons.core.Config;
import calofit.commons.core.LogsCenter;
import calofit.commons.core.Version;
import calofit.commons.exceptions.DataConversionException;
import calofit.commons.util.ConfigUtil;
import calofit.commons.util.StringUtil;
import calofit.logic.Logic;
import calofit.logic.LogicManager;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.MealLog;
import calofit.model.util.SampleDataUtil;
import calofit.storage.DishDatabaseStorage;
import calofit.storage.JsonDishDatabaseStorage;
import calofit.storage.JsonUserPrefsStorage;
import calofit.storage.Storage;
import calofit.storage.StorageManager;
import calofit.storage.UserPrefsStorage;
import calofit.ui.Ui;
import calofit.ui.UiManager;

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
        logger.info("=============================[ Initializing DishDatabase ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        DishDatabaseStorage dishDatabaseStorage = new JsonDishDatabaseStorage(userPrefs.getDishDatabaseFilePath());
        storage = new StorageManager(dishDatabaseStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    private ReadOnlyDishDatabase loadDishDatabase(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyDishDatabase> dishDatabaseOptional;
        ReadOnlyDishDatabase initialData;
        try {
            dishDatabaseOptional = storage.readDishDatabase();
            if (!dishDatabaseOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample DishDatabase");
            }
            return dishDatabaseOptional.orElseGet(SampleDataUtil::getSampleDishDatabase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty DishDatabase");
            return new DishDatabase();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty DishDatabase");
            return new DishDatabase();
        }
    }

    private MealLog loadMealLog(Storage storage, ReadOnlyUserPrefs userPrefs) {
        MealLog mealLog = new MealLog();
        return mealLog;
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s dish database and {@code userPrefs}. <br>
     * The data from the sample dish database will be used instead if {@code storage}'s dish database is not found,
     * or an empty dish database will be used instead if errors occur when reading {@code storage}'s dish database.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyDishDatabase dishDb = loadDishDatabase(storage, userPrefs);
        MealLog mealLog = loadMealLog(storage, userPrefs);
        return new ModelManager(mealLog, dishDb, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty DishDatabase");
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
        logger.info("Starting DishDatabase " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Dish database ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
