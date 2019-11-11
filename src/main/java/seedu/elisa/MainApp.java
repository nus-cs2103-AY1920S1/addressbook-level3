package seedu.elisa;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import seedu.elisa.commons.core.Config;
import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.Version;
import seedu.elisa.commons.exceptions.DataConversionException;
import seedu.elisa.commons.util.ConfigUtil;
import seedu.elisa.commons.util.StringUtil;

import seedu.elisa.logic.Logic;
import seedu.elisa.logic.LogicManager;

import seedu.elisa.model.ElisaCommandHistory;
import seedu.elisa.model.ElisaCommandHistoryManager;
import seedu.elisa.model.ItemModel;
import seedu.elisa.model.ItemModelManager;
import seedu.elisa.model.ItemStorage;
import seedu.elisa.model.ReadOnlyUserPrefs;
import seedu.elisa.model.UserPrefs;

import seedu.elisa.storage.ItemListStorage;
import seedu.elisa.storage.JsonItemStorage;
import seedu.elisa.storage.JsonUserPrefsStorage;
import seedu.elisa.storage.Storage;
import seedu.elisa.storage.StorageManager;
import seedu.elisa.storage.UserPrefsStorage;
import seedu.elisa.ui.Ui;
import seedu.elisa.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected ItemModel model;
    protected Config config;
    protected ElisaCommandHistory commandHistory;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ItemListStorage itemListStorage = new JsonItemStorage(userPrefs.getItemStorageFilePath());
        storage = new StorageManager(itemListStorage, userPrefsStorage);

        initLogging(config);

        commandHistory = new ElisaCommandHistoryManager();

        model = initModelManager(storage, userPrefs, commandHistory);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private ItemModel initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs, ElisaCommandHistory stateHistory) {
        ItemStorage initialData;
        try {
            initialData = storage.toModelType();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Item Storage");
            initialData = new ItemStorage();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Item Storage");
            initialData = new ItemStorage();
        }

        return new ItemModelManager(initialData, userPrefs, stateHistory);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        //Bryan Reminder
        logic.shutdown();
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
