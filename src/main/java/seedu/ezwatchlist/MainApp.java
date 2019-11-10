package seedu.ezwatchlist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.ezwatchlist.commons.core.Config;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.commons.core.Version;
import seedu.ezwatchlist.commons.exceptions.DataConversionException;
import seedu.ezwatchlist.commons.util.ConfigUtil;
import seedu.ezwatchlist.commons.util.StringUtil;
import seedu.ezwatchlist.logic.Logic;
import seedu.ezwatchlist.logic.LogicManager;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.ModelManager;
import seedu.ezwatchlist.model.ReadOnlyUserPrefs;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.UserPrefs;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.util.DataBaseUtil;
import seedu.ezwatchlist.model.util.SampleDataUtil;
import seedu.ezwatchlist.statistics.Statistics;
import seedu.ezwatchlist.storage.*;
import seedu.ezwatchlist.ui.Ui;
import seedu.ezwatchlist.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected Statistics statistics;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing WatchList ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        WatchListStorage watchListStorage = new JsonWatchListStorage(userPrefs.getWatchListFilePath());
        DatabaseStorage databaseStorage = new JsonDatabaseStorage(userPrefs.getDatabaseFilePath());
        storage = new StorageManager(watchListStorage, databaseStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        statistics = new Statistics(model);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, statistics);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s watchlist and {@code userPrefs}. <br>
     * The data from the sample watchlist will be used instead if {@code storage}'s watchlist is not found,
     * or an empty watchlist will be used instead if errors occur when reading {@code storage}'s watchlist.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyWatchList> watchListOptional;
        Optional<ReadOnlyWatchList> databaseOptional;
        ReadOnlyWatchList initialData;
        ReadOnlyWatchList database;
        try {
            watchListOptional = storage.readWatchList();
            if (!watchListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample WatchList");
            }
            initialData = watchListOptional.orElseGet(SampleDataUtil::getSampleWatchList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty WatchList");
            initialData = new WatchList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty WatchList");
            initialData = new WatchList();
        }

        try {
            databaseOptional = storage.readDatabase();
            if (!databaseOptional.isPresent()) {
                logger.info("Database not found. Will be starting with a new database");
            }
            database = databaseOptional.orElseGet(DataBaseUtil::getShowDatabaseList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty database");
            database = new WatchList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty database");
            database = new WatchList();
        }

        return new ModelManager(initialData, database, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty WatchList");
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
        logger.info("Starting WatchList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping WatchList ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
