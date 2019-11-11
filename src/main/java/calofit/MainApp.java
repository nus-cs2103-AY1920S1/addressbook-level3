package calofit;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.Stage;

import calofit.commons.core.Config;
import calofit.commons.core.LogsCenter;
import calofit.commons.core.Timer;
import calofit.commons.core.Version;
import calofit.commons.exceptions.DataConversionException;
import calofit.commons.util.ConfigUtil;
import calofit.commons.util.StringUtil;
import calofit.logic.Logic;
import calofit.logic.LogicManager;
import calofit.model.CalorieBudget;
import calofit.model.Model;
import calofit.model.ModelManager;
import calofit.model.ReadOnlyUserPrefs;
import calofit.model.UserPrefs;
import calofit.model.dish.DishDatabase;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.MealLog;
import calofit.model.meal.ReadOnlyMealLog;
import calofit.model.util.SampleDataUtil;
import calofit.storage.CalorieBudgetStorage;
import calofit.storage.DishDatabaseStorage;
import calofit.storage.JsonCalorieBudgetStorage;
import calofit.storage.JsonDishDatabaseStorage;
import calofit.storage.JsonMealLogStorage;
import calofit.storage.JsonUserPrefsStorage;
import calofit.storage.MealLogStorage;
import calofit.storage.Storage;
import calofit.storage.StorageManager;
import calofit.storage.UserPrefsStorage;
import calofit.ui.Ui;
import calofit.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Duration TIME_UPDATE_PERIOD = Duration.ofSeconds(10);
    public static final Version VERSION = new Version(1, 2, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    private Timer timer = new Timer(Platform::runLater);
    private SimpleObjectProperty<LocalDateTime> nowProperty = new SimpleObjectProperty<>(LocalDateTime.now());

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing DishDatabase ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
                config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        DishDatabaseStorage dishDatabaseStorage = new JsonDishDatabaseStorage(userPrefs.getDishDatabaseFilePath());
        MealLogStorage mealLogStorage = new JsonMealLogStorage(userPrefs.getMealLogFilePath());
        CalorieBudgetStorage calorieBudgetStorage = new JsonCalorieBudgetStorage(userPrefs.getCalorieBudgetFilePath());
        storage = new StorageManager(dishDatabaseStorage, mealLogStorage, userPrefsStorage, calorieBudgetStorage);

        initLogging(config);

        timer.registerPeriodic(TIME_UPDATE_PERIOD, () -> {
            nowProperty.set(LocalDateTime.now());
        });

        model = initModelManager(storage, userPrefs);
        model.nowProperty().bind(nowProperty);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, timer);
    }

    /**
     * Read the current dish database from its saved file.
     * @param storage Storage handler object
     * @param userPrefs User preference object
     * @return Dish database loaded from file, or a new instance if file is missing or invalid.
     */
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

    /**
     * Loads the meal log from its saved file.
     * @param storage Storage handler object
     * @param userPrefs User preference object
     * @return Meal loaded from file, or a new instance if file is missing or invalidn
     */
    private ReadOnlyMealLog loadMealLog(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyMealLog> mealLogOptional;
        ReadOnlyMealLog initialData;
        try {
            mealLogOptional = storage.readMealLog();
            if (!mealLogOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a empty MealLog");
            }
            return mealLogOptional.orElseGet(SampleDataUtil::getNewMealLog);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty MealLog");
            return new MealLog();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty MealLog");
            return new MealLog();
        }
    }

    /**
     * Loads the meal log from its saved file.
     * @param storage Storage handler object
     * @param userPrefs User preference object
     * @return Meal loaded from file, or a new instance if file is missing or invalidn
     */
    private CalorieBudget loadCalorieBudget(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<CalorieBudget> calorieBudgetOptional;
        try {
            calorieBudgetOptional = storage.readCalorieBudget();
            if (!calorieBudgetOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a default budget");
            }
            return calorieBudgetOptional.orElseGet(SampleDataUtil::getSampleBudget);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an default calorie budget");
            return new CalorieBudget();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty calorie budget");
            return new CalorieBudget();
        }
    }
    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s dish database and {@code userPrefs}. <br>
     * The data from the sample dish database will be used instead if {@code storage}'s dish database is not found,
     * or an empty dish database will be used instead if errors occur when reading {@code storage}'s dish database.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyDishDatabase dishDb = loadDishDatabase(storage, userPrefs);
        ReadOnlyMealLog mealLog = loadMealLog(storage, userPrefs);
        CalorieBudget budget = loadCalorieBudget(storage, userPrefs);
        return new ModelManager(mealLog, dishDb, userPrefs, budget);
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
        logger.info("============================ [ Stopping Timers ] =============================");
        timer.stop();
    }
}
