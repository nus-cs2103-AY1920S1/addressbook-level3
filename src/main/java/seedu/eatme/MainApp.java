package seedu.eatme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import seedu.eatme.commons.core.Config;
import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.commons.core.Version;
import seedu.eatme.commons.exceptions.DataConversionException;
import seedu.eatme.commons.util.ConfigUtil;
import seedu.eatme.commons.util.StringUtil;
import seedu.eatme.logic.Logic;
import seedu.eatme.logic.LogicManager;
import seedu.eatme.model.EateryList;
import seedu.eatme.model.FeedList;
import seedu.eatme.model.Model;
import seedu.eatme.model.ModelManager;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.ReadOnlyUserPrefs;
import seedu.eatme.model.UserPrefs;
import seedu.eatme.model.util.SampleDataUtil;
import seedu.eatme.storage.EateryListStorage;
import seedu.eatme.storage.FeedListStorage;
import seedu.eatme.storage.JsonEateryListStorage;
import seedu.eatme.storage.JsonFeedListStorage;
import seedu.eatme.storage.JsonUserPrefsStorage;
import seedu.eatme.storage.Storage;
import seedu.eatme.storage.StorageManager;
import seedu.eatme.storage.UserPrefsStorage;
import seedu.eatme.ui.Ui;
import seedu.eatme.ui.UiManager;

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
        logger.info("=============================[ Initializing EatMe ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        EateryListStorage eateryListStorage = new JsonEateryListStorage(userPrefs.getEateryListFilePath());
        FeedListStorage feedListStorage = new JsonFeedListStorage(userPrefs.getFeedListFilePath());

        storage = new StorageManager(eateryListStorage, feedListStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s eatery list and {@code userPrefs}. <br>
     * The data from the sample eatery list will be used instead if {@code storage}'s eatery list is not found,
     * or an empty eatery list will be used instead if errors occur when reading {@code storage}'s eatery list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyEateryList> eateryListOptional;
        ReadOnlyEateryList initialEateryList;
        Optional<ReadOnlyFeedList> feedListOptional;
        ReadOnlyFeedList initialFeedList;

        try {
            eateryListOptional = storage.readEateryList();
            if (!eateryListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample EateryList");
            }
            initialEateryList = eateryListOptional.orElseGet(SampleDataUtil::getSampleEateryList);

            feedListOptional = storage.readFeedList();
            if (!feedListOptional.isPresent()) {
                logger.info("Feed list file not found. Will be starting with an empty FeedList");
            }
            initialFeedList = feedListOptional.orElseGet(SampleDataUtil::getSampleFeedList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. "
                    + "Will be starting with an empty EateryList and FeedList");

            initialEateryList = new EateryList();
            initialFeedList = new FeedList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Will be starting with an empty EateryList and FeedList");

            initialEateryList = new EateryList();
            initialFeedList = new FeedList();
        }

        return new ModelManager(initialEateryList, initialFeedList, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty EatMe");
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
        logger.info("Starting EatMe " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping EatMe ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
