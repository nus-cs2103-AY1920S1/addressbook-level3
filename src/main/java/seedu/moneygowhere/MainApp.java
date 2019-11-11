package seedu.moneygowhere;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.moneygowhere.commons.core.Config;
import seedu.moneygowhere.commons.core.LogsCenter;
import seedu.moneygowhere.commons.core.Version;
import seedu.moneygowhere.commons.exceptions.DataConversionException;
import seedu.moneygowhere.commons.util.ConfigUtil;
import seedu.moneygowhere.commons.util.StringUtil;
import seedu.moneygowhere.logic.Logic;
import seedu.moneygowhere.logic.LogicManager;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.ReadOnlyUserPrefs;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.util.SampleDataUtil;
import seedu.moneygowhere.storage.JsonSpendingBookStorage;
import seedu.moneygowhere.storage.JsonUserPrefsStorage;
import seedu.moneygowhere.storage.SpendingBookStorage;
import seedu.moneygowhere.storage.Storage;
import seedu.moneygowhere.storage.StorageManager;
import seedu.moneygowhere.storage.UserPrefsStorage;
import seedu.moneygowhere.ui.Ui;
import seedu.moneygowhere.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing MoneyGoWhere ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        SpendingBookStorage spendingBookStorage = new JsonSpendingBookStorage(userPrefs.getSpendingBookFilePath());
        storage = new StorageManager(spendingBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s MoneyGoWhere and {@code userPrefs}. <br>
     * The data from the sample MoneyGoWhere will be used instead if {@code storage}'s MoneyGoWhere is not found,
     * or an empty MoneyGoWhere will be used instead if errors occur when reading {@code storage}'s MoneyGoWhere.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlySpendingBook> spendingBookOptional;
        ReadOnlySpendingBook initialData;
        try {
            spendingBookOptional = storage.readSpendingBook();
            if (!spendingBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample SpendingBook");
            }
            initialData = spendingBookOptional.orElseGet(SampleDataUtil::getSampleSpendingBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty SpendingBook");
            initialData = new SpendingBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty SpendingBook");
            initialData = new SpendingBook();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty SpendingBook");
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
        logger.info("Starting MoneyGoWhere " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping MoneyGoWhere ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
