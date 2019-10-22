package budgetbuddy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import budgetbuddy.commons.core.Config;
import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.core.Version;
import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.commons.util.ConfigUtil;
import budgetbuddy.commons.util.StringUtil;
import budgetbuddy.logic.Logic;
import budgetbuddy.logic.LogicManager;
import budgetbuddy.model.AccountsManager;
import budgetbuddy.model.LoansManager;
import budgetbuddy.model.Model;
import budgetbuddy.model.ModelManager;
import budgetbuddy.model.ReadOnlyUserPrefs;
import budgetbuddy.model.RuleManager;
import budgetbuddy.model.UserPrefs;
import budgetbuddy.storage.JsonUserPrefsStorage;
import budgetbuddy.storage.Storage;
import budgetbuddy.storage.StorageManager;
import budgetbuddy.storage.UserPrefsStorage;
import budgetbuddy.storage.loans.JsonLoansStorage;
import budgetbuddy.storage.loans.LoansStorage;
import budgetbuddy.ui.Ui;
import budgetbuddy.ui.UiManager;
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
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        LoansStorage loansStorage = new JsonLoansStorage(userPrefs.getLoansFilePath());
        storage = new StorageManager(loansStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        AccountsManager accountsManager = new AccountsManager();
        LoansManager loansManager = initLoansManager(storage);
        RuleManager ruleManager = new RuleManager();

        return new ModelManager(loansManager, ruleManager, accountsManager, userPrefs);
    }

    /**
     * Loads and returns a Loans Manager from storage.
     * Returns an empty Loans Manager if no file found or if exception occurs during loading.
     */
    private LoansManager initLoansManager(Storage storage) {
        Optional<LoansManager> loansManagerOptional;

        try {
            loansManagerOptional = storage.readLoans();
            if (loansManagerOptional.isEmpty()) {
                logger.info("Loans file not found. Will be starting with an empty LoansManager.");
            }
            return loansManagerOptional.orElseGet(LoansManager::new);
        } catch (DataConversionException e) {
            logger.warning("Loans file not in the correct format. Will be starting with an empty LoansManager.");
            return new LoansManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from loans file. Will be starting with an empty LoansManager.");
            return new LoansManager();
        }
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Budget Buddy");
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
        logger.info("Starting Budget Buddy " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Budget Buddy ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
