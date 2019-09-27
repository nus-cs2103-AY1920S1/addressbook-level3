package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.CatalogStorage;
import seedu.address.storage.JsonCatalogStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.borrowerrecords.BorrowerRecordsStorage;
import seedu.address.storage.borrowerrecords.JsonBorrowerRecordsStorage;
import seedu.address.storage.catalog.CatalogStorage;
import seedu.address.storage.catalog.JsonCatalogStorage;
import seedu.address.storage.loanrecord.JsonLoanRecordsStorage;
import seedu.address.storage.loanrecord.LoanRecordsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

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
        LoanRecordsStorage loanRecordsStorage = new JsonLoanRecordsStorage(userPrefs.getLoanRecordsFilePath());
        CatalogStorage catalogStorage = new JsonCatalogStorage(userPrefs.getCatalogFilePath());
        BorrowerRecordsStorage borrowerRecordsStorage
                = new JsonBorrowerRecordsStorage(userPrefs.getBorrowerRecordsFilePath());

        storage = new StorageManager(userPrefsStorage,
                loanRecordsStorage, catalogStorage, borrowerRecordsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     *
     *     TODO edit this
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
      
        Optional<ReadOnlyLoanRecords> loanRecordsOptional;
        ReadOnlyLoanRecords initialLoanRecords;
        Optional<ReadOnlyCatalog> catalogOptional;
        ReadOnlyCatalog initialCatalog;
        Optional<ReadOnlyBorrowerRecords> borrowerRecordsOptional;
        ReadOnlyBorrowerRecords initialBorrowerRecords;

        try {
            loanRecordsOptional = storage.readLoanRecords();
            if (!loanRecordsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample LoanRecord");
            }
            initialLoanRecords = loanRecordsOptional.orElseGet(SampleDataUtil::getSampleLoanRecords);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty LoanRecord");
            initialLoanRecords = new LoanRecords();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty LoanRecord");
            initialLoanRecords = new LoanRecords();
        }

        try {
            catalogOptional = storage.readCatalog();
            if (!catalogOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Catalog");
            }
            initialCatalog = catalogOptional.orElseGet(SampleDataUtil::getSampleCatalog);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Catalog");
            initialCatalog = new Catalog();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Catalog");
            initialCatalog = new Catalog();
        }

        try {
            borrowerRecordsOptional = storage.readBorrowerRecords();
            if (!borrowerRecordsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample BorrowerRecords");
            }
            initialBorrowerRecords = borrowerRecordsOptional.orElseGet(SampleDataUtil::getSampleBorrowerRecords);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty BorrowerRecords");
            initialBorrowerRecords = new BorrowerRecords();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty BorrowerRecords");
            initialBorrowerRecords = new BorrowerRecords();
        }

        return new ModelManager(userPrefs, initialLoanRecords,
                initialCatalog, initialBorrowerRecords);
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
        logger.info("============================ [ Stopping Catalog ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
