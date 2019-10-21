package seedu.savenus;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.savenus.commons.core.Config;
import seedu.savenus.commons.core.LogsCenter;
import seedu.savenus.commons.core.Version;
import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.commons.util.ConfigUtil;
import seedu.savenus.commons.util.StringUtil;
import seedu.savenus.logic.Logic;
import seedu.savenus.logic.LogicManager;
import seedu.savenus.model.Menu;
import seedu.savenus.model.Model;
import seedu.savenus.model.ModelManager;
import seedu.savenus.model.PurchaseHistory;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.ReadOnlyPurchaseHistory;
import seedu.savenus.model.ReadOnlyUserPrefs;
import seedu.savenus.model.UserPrefs;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.savings.JsonSavingsStorage;
import seedu.savenus.model.savings.ReadOnlySavingsAccount;
import seedu.savenus.model.savings.SavingsAccount;
import seedu.savenus.model.savings.SavingsStorage;
import seedu.savenus.model.sorter.CustomSorter;
import seedu.savenus.model.util.SampleDataUtil;
import seedu.savenus.storage.CustomSortStorage;
import seedu.savenus.storage.JsonCustomSortStorage;
import seedu.savenus.storage.JsonMenuStorage;
import seedu.savenus.storage.JsonPurchaseHistoryStorage;
import seedu.savenus.storage.JsonRecsStorage;
import seedu.savenus.storage.JsonUserPrefsStorage;
import seedu.savenus.storage.MenuStorage;
import seedu.savenus.storage.PurchaseHistoryStorage;
import seedu.savenus.storage.RecsStorage;
import seedu.savenus.storage.Storage;
import seedu.savenus.storage.StorageManager;
import seedu.savenus.storage.UserPrefsStorage;
import seedu.savenus.ui.Ui;
import seedu.savenus.ui.UiManager;

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
        logger.info("=============================[ Initializing Menu ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        MenuStorage menuStorage = new JsonMenuStorage(userPrefs.getMenuFilePath());
        SavingsStorage savingsAccountStorage = new JsonSavingsStorage(userPrefs.getSavingsAccountFilePath());
        RecsStorage userRecommendations = new JsonRecsStorage(userPrefs.getRecsFilePath());
        PurchaseHistoryStorage purchaseHistoryStorage = new JsonPurchaseHistoryStorage(userPrefs
                .getPurchaseHistoryFilePath());
        CustomSortStorage sort = new JsonCustomSortStorage(userPrefs.getSortFilePath());
        storage = new StorageManager(menuStorage, userPrefsStorage, userRecommendations,
                purchaseHistoryStorage, sort, savingsAccountStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs, userRecommendations, purchaseHistoryStorage,
                sort, savingsAccountStorage);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s $aveNUS menu and {@code userPrefs}. <br>
     * The data from the sample menu will be used instead if {@code storage}'s menu is not found,
     * or an empty menu will be used instead if errors occur when reading {@code storage}'s menu.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs, RecsStorage userRecs,
                                   PurchaseHistoryStorage purchaseHistoryStorage,
                                   CustomSortStorage userSortFields, SavingsStorage savingsAccountStorage) {
        Optional<ReadOnlyMenu> menuOptional;
        ReadOnlyMenu initialData;

        Optional<ReadOnlySavingsAccount> savingsAccountOptional;
        ReadOnlySavingsAccount initialSavingsAccount;

        Optional<ReadOnlyPurchaseHistory> purchaseHistoryOptional;
        ReadOnlyPurchaseHistory initialPurchaseHistory;

        Optional<UserRecommendations> recsOptional;
        UserRecommendations initialRecs;

        Optional<CustomSorter> sorterOptional;
        CustomSorter initialSorter;
        try {
            menuOptional = storage.readMenu();
            if (!menuOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Menu");
            }
            initialData = menuOptional.orElseGet(SampleDataUtil::getSampleMenu);

            savingsAccountOptional = storage.readSavingsAccount();
            if (!savingsAccountOptional.isPresent()) {
                logger.info("Savings Account file is not found. Will be starting with an empty Savings Account");
            }
            initialSavingsAccount = savingsAccountOptional.orElse(new SavingsAccount());

            recsOptional = userRecs.readRecs();
            if (!recsOptional.isPresent()) {
                logger.info("Recommendation file not found. Will be starting with a blank Recommendation");
            }
            initialRecs = recsOptional.orElse(new UserRecommendations());

            purchaseHistoryOptional = storage.readPurchaseHistory();
            if (!purchaseHistoryOptional.isPresent()) {
                logger.info("Purchase History file not found. Will be starting with a blank Purchase History");
            }
            initialPurchaseHistory = purchaseHistoryOptional.orElse(new PurchaseHistory());

            sorterOptional = userSortFields.readFields();
            if (!sorterOptional.isPresent()) {
                logger.info("CustomSorter file not found. Will be starting with a blank CustomSorter");
            }
            initialSorter = sorterOptional.orElse(new CustomSorter());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty application");
            initialData = new Menu();
            initialSavingsAccount = new SavingsAccount();
            initialRecs = new UserRecommendations();
            initialPurchaseHistory = new PurchaseHistory();
            initialSorter = new CustomSorter();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty application");
            initialData = new Menu();
            initialSavingsAccount = new SavingsAccount();
            initialRecs = new UserRecommendations();
            initialPurchaseHistory = new PurchaseHistory();
            initialSorter = new CustomSorter();
        }
        return new ModelManager(initialData, userPrefs, initialRecs, initialPurchaseHistory,
                initialSorter, initialSavingsAccount);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Menu");
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
        logger.info("Starting Menu " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Menu ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
