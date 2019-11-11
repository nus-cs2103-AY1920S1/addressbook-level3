package seedu.address;

import static seedu.address.model.commons.Currency.DEFAULT_BASE_CURRENCY;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.HttpsClientUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.BudgetList;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBudgetList;
import seedu.address.model.ReadOnlyExpenseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.exchangedata.ExchangeDataSingleton;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.BudgetListStorage;
import seedu.address.storage.ExchangeDataStorage;
import seedu.address.storage.ExpenseListStorage;
import seedu.address.storage.JsonBudgetListStorage;
import seedu.address.storage.JsonExchangeDataStorage;
import seedu.address.storage.JsonExpenseListStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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
        logger.info("=============================[ Initializing MYMorise ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ExpenseListStorage expenseListStorage = new JsonExpenseListStorage(userPrefs.getExpenseListFilePath());
        BudgetListStorage budgetListStorage = new JsonBudgetListStorage(userPrefs.getBudgetListFilePath());
        ExchangeDataStorage exchangeDataStorage = new JsonExchangeDataStorage(userPrefs.getExchangeDataFilePath());
        storage = new StorageManager(expenseListStorage, budgetListStorage, exchangeDataStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }


    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s expense list and {@code userPrefs}. <br>
     * The data from the sample expense list will be used instead if {@code storage}'s expense list is not found,
     * or an empty expense list will be used instead if errors occur when reading {@code storage}'s expense list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyExpenseList> expenseListOptional;
        Optional<ExchangeData> exchangeDataOptional;
        Optional<ReadOnlyBudgetList> budgetListOptional;
        ReadOnlyExpenseList initialData;
        ExchangeData initialExchangeData;
        ReadOnlyBudgetList initialBudgets;

        try {
            //Ensure data folder exists
            if (!Files.exists(Path.of("data"))) {
                try {
                    Files.createDirectory(Path.of("data"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // Download updated data
            try {
                HttpsClientUtil.getLatestExchangeData(DEFAULT_BASE_CURRENCY);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Read from file
            exchangeDataOptional = storage.readExchangeData();

            if (!exchangeDataOptional.isPresent()) {
                logger.info("Data file not found/corrupted. Will be starting with a sample ExchangeData");
            }
            initialExchangeData = exchangeDataOptional.orElseGet(SampleDataUtil::getSampleExchangeData);
            ExchangeDataSingleton.updateInstance(initialExchangeData);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ExpenseList");
            initialExchangeData = SampleDataUtil.getSampleExchangeData();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenseList");
            initialExchangeData = SampleDataUtil.getSampleExchangeData();
        }

        try {
            expenseListOptional = storage.readExpenseList();
            if (!expenseListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ExpenseList");
            }
            initialData = expenseListOptional.orElseGet(SampleDataUtil::getSampleExpenseList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ExpenseList");
            initialData = new ExpenseList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenseList");
            initialData = new ExpenseList();
        }

        try {
            budgetListOptional = storage.readBudgetList();
            if (!budgetListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample BudgetList");
            }
            initialBudgets = budgetListOptional.orElseGet(SampleDataUtil::getSampleBudgetList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty BudgetList");
            initialBudgets = new BudgetList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty BudgetList");
            initialBudgets = new BudgetList();
        }

        return new ModelManager(initialData, initialBudgets, initialExchangeData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ExpenseList");
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
        logger.info("Starting ExpenseList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping MYMorise ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
