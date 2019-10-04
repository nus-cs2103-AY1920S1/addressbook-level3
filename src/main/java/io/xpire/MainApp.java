package io.xpire;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import io.xpire.commons.core.Config;
import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.core.Version;
import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.commons.util.ConfigUtil;
import io.xpire.commons.util.StringUtil;
import io.xpire.logic.Logic;
import io.xpire.logic.LogicManager;
import io.xpire.model.Xpire;
import io.xpire.model.Model;
import io.xpire.model.ModelManager;
import io.xpire.model.ReadOnlyXpire;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.UserPrefs;
import io.xpire.model.util.SampleDataUtil;
import io.xpire.storage.XpireStorage;
import io.xpire.storage.JsonXpireStorage;
import io.xpire.storage.JsonUserPrefsStorage;
import io.xpire.storage.Storage;
import io.xpire.storage.StorageManager;
import io.xpire.storage.UserPrefsStorage;
import io.xpire.ui.Ui;
import io.xpire.ui.UiManager;
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
        logger.info("=============================[ Initializing Xpire ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        XpireStorage xpireStorage = new JsonXpireStorage(
                userPrefs.getXpireFilePath()
        );
        storage = new StorageManager(xpireStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s expiry date tracker and {@code userPrefs}.
     * <br> The data from the sample expiry date tracker will be used instead if {@code storage}'s expiry date tracker
     * is not found, or an empty expiry date tracker will be used instead if errors occur when reading
     * {@code storage}'s expiry date tracker.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyXpire> expiryDateTrackerOptional;
        ReadOnlyXpire initialData;
        try {
            expiryDateTrackerOptional = storage.readXpire();
            if (!expiryDateTrackerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Expiry Date Tracker");
            }
            initialData = expiryDateTrackerOptional.orElseGet(SampleDataUtil::getSampleXpire);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Expiry Date Tracker");
            initialData = new Xpire();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Expiry Date Tracker");
            initialData = new Xpire();
        }

        return new ModelManager(initialData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpiryDateTracker");
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
        logger.info("Starting Xpire " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Xpire ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
