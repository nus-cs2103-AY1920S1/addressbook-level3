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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.borrowerrecords.BorrowerRecordsStorage;
import seedu.address.storage.borrowerrecords.JsonBorrowerRecordsStorage;
import seedu.address.storage.catalog.CatalogStorage;
import seedu.address.storage.catalog.JsonCatalogStorage;
import seedu.address.storage.loanrecords.JsonLoanRecordsStorage;
import seedu.address.storage.loanrecords.LoanRecordsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String MESSAGE_DATA_NOT_FOUND = "Data file(s) not found. "
        + "Will be starting with sample library records";
    private static final String MESSAGE_DATA_INCORRECT_FORMAT = "Data file(s) not in the correct format. "
        + "Will be starting with sample library records";
    private static final String MESSAGE_PROBLEM_READING_FILE = "Problem while reading from file(s). "
        + "Will be starting with sample library records";

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing LiBerry ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        LoanRecordsStorage loanRecordsStorage = new JsonLoanRecordsStorage(userPrefs.getLoanRecordsFilePath());
        CatalogStorage catalogStorage = new JsonCatalogStorage(userPrefs.getCatalogFilePath());
        BorrowerRecordsStorage borrowerRecordsStorage =
            new JsonBorrowerRecordsStorage(userPrefs.getBorrowerRecordsFilePath());

        storage = new StorageManager(userPrefsStorage,
            loanRecordsStorage, catalogStorage, borrowerRecordsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s catalog, borrower records, loan records
     * and {@code userPrefs}. <br>
     * <p>
     * The data from the sample catalog will be used instead if {@code storage}'s catalog is not found,
     * or an empty catalog will be used instead if errors occur when reading {@code storage}'s catalog.
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
            if (loanRecordsOptional.isEmpty()) {
                logger.info(MESSAGE_DATA_NOT_FOUND);
                return getSampleModelManager(userPrefs, MESSAGE_DATA_NOT_FOUND);
            }
            initialLoanRecords = loanRecordsOptional.get();
            catalogOptional = storage.readCatalog(initialLoanRecords);
            borrowerRecordsOptional = storage.readBorrowerRecords(initialLoanRecords);

            if (catalogOptional.isEmpty() || borrowerRecordsOptional.isEmpty()) {
                logger.info(MESSAGE_DATA_NOT_FOUND);
                return getSampleModelManager(userPrefs, MESSAGE_DATA_NOT_FOUND);
            }
            initialCatalog = catalogOptional.get();
            initialBorrowerRecords = borrowerRecordsOptional.get();

        } catch (DataConversionException e) {
            logger.warning(MESSAGE_DATA_INCORRECT_FORMAT);
            return getSampleModelManager(userPrefs, MESSAGE_DATA_INCORRECT_FORMAT);
        } catch (IOException e) {
            logger.warning(MESSAGE_PROBLEM_READING_FILE);
            return getSampleModelManager(userPrefs, MESSAGE_PROBLEM_READING_FILE);
        }

        return new ModelManager(initialCatalog, initialLoanRecords, initialBorrowerRecords, userPrefs);
    }

    private Model getSampleModelManager(ReadOnlyUserPrefs userPrefs, String message) {
        ReadOnlyLoanRecords initialLoanRecords = SampleDataUtil.getSampleLoanRecords();
        return new ModelManager(
            SampleDataUtil.getSampleCatalog(initialLoanRecords), initialLoanRecords,
            SampleDataUtil.getSampleBorrowerRecords(initialLoanRecords), userPrefs, message);
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
            logger.warning("Problem while reading from the file. Will be starting default user prefs");
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
        logger.info("Starting LiBerry " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping LiBerry ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
