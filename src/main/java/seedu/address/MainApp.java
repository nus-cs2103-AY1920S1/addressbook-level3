package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.inventory.model.exception.NoSuchIndexException;
import seedu.address.person.commons.core.Config;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.commons.core.Version;
import seedu.address.person.commons.exceptions.DataConversionException;
import seedu.address.person.commons.util.ConfigUtil;
import seedu.address.person.commons.util.StringUtil;
import seedu.address.person.logic.Logic;
import seedu.address.person.logic.LogicManager;
import seedu.address.person.model.AddressBook;
import seedu.address.person.model.Model;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.ReadOnlyAddressBook;
import seedu.address.person.model.ReadOnlyUserPrefs;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.util.SampleDataUtil;
import seedu.address.person.storage.AddressBookStorage;
import seedu.address.person.storage.JsonAddressBookStorage;
import seedu.address.person.storage.JsonUserPrefsStorage;
import seedu.address.person.storage.Storage;
import seedu.address.person.storage.StorageManager;
import seedu.address.person.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String FILE_PATH_REIMBURSEMENT = "data/reimbursementInformation.txt";

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;


    protected seedu.address.transaction.logic.LogicManager transactionLogic;
    protected seedu.address.transaction.model.ModelManager transactionModel;
    protected seedu.address.transaction.storage.StorageManager transactionStorage;

    protected seedu.address.reimbursement.logic.LogicManager reimbursementLogic;
    protected seedu.address.reimbursement.model.ModelManager reimbursementModel;
    protected seedu.address.reimbursement.storage.StorageManager reimbursementStorage;

    protected seedu.address.inventory.logic.LogicManager inventoryLogic;
    protected seedu.address.inventory.model.ModelManager inventoryModel;
    protected seedu.address.inventory.storage.StorageManager inventoryStorage;

    protected seedu.address.cashier.logic.LogicManager cashierLogic;
    protected seedu.address.cashier.model.ModelManager cashierModel;
    protected seedu.address.cashier.storage.StorageManager cashierStorage;

    protected seedu.address.overview.logic.LogicManager overviewLogic;
    protected seedu.address.overview.model.ModelManager overviewModel;
    protected seedu.address.overview.storage.StorageManager overviewStorage;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        //For Transaction Storage and Manager
        transactionStorage =
                new seedu.address.transaction.storage.StorageManager("data/transactionHistory.txt", model);
        transactionModel =
                new seedu.address.transaction.model.ModelManager(transactionStorage.readTransactionList());

        //For Reimbursement Storage and Manager
        reimbursementStorage =
                new seedu.address.reimbursement.storage.StorageManager(FILE_PATH_REIMBURSEMENT, transactionStorage);
        reimbursementModel =
                new seedu.address.reimbursement.model.ModelManager(reimbursementStorage.readReimbursementList());

        //For Inventory Storage and Manager
        inventoryStorage =
                new seedu.address.inventory.storage.StorageManager("data/inventoryInformation.txt");
        inventoryModel =
                new seedu.address.inventory.model.ModelManager(inventoryStorage);

        //For Cashier Storage and Manager
        cashierStorage = new seedu.address.cashier.storage.StorageManager("data"
                        + "/inventoryInformation.txt", "data/transactionHistory.txt", model);
        cashierModel = new seedu.address.cashier.model.ModelManager(cashierStorage);

        //For Overview Storage and Manager
        overviewStorage = new seedu.address.overview.storage.StorageManager("data/overviewInformation.txt");
        overviewModel = new seedu.address.overview.model.ModelManager(overviewStorage);

        //All logic
        transactionLogic = new seedu.address.transaction.logic.LogicManager(transactionModel, transactionStorage,
                model, storage, reimbursementModel, reimbursementStorage);

        reimbursementLogic = new seedu.address.reimbursement.logic.LogicManager(reimbursementModel,
                reimbursementStorage, transactionModel, transactionStorage, model);

        inventoryLogic = new
                seedu.address.inventory.logic.LogicManager(cashierModel, cashierStorage,
                inventoryModel, inventoryStorage);

        cashierLogic = new seedu.address.cashier.logic.LogicManager(cashierModel, cashierStorage, model, storage,
                reimbursementModel, reimbursementStorage, transactionModel, transactionStorage, inventoryModel,
                inventoryStorage);

        overviewLogic = new seedu.address.overview.logic.LogicManager(overviewModel, overviewStorage, transactionLogic,
                inventoryLogic);

        logic = new LogicManager(model, storage, transactionLogic, reimbursementLogic, cashierLogic, inventoryLogic);

        //no config for ui yet
        /*UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(new Config().getUserPrefsFilePath());
        UserPrefs userPrefs = new UserPrefs();
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        seedu.address.person.storage.StorageManager personStorage =
                new seedu.address.person.storage.StorageManager(addressBookStorage, userPrefsStorage);

        ReadOnlyAddressBook initialData;
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = personStorage.readAddressBook();
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);

        } catch (Exception e) {

            initialData = new AddressBook();
        }*/
        /*seedu.address.person.model.ModelManager personMM =
                new seedu.address.person.model.ModelManager(initialData, userPrefs);*/

        ui = new UiManager(transactionLogic, reimbursementLogic, inventoryLogic, logic, cashierLogic, overviewLogic);

    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
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
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            transactionModel.sortReset();
            transactionStorage.writeFile(transactionModel.getTransactionList());
            reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
            inventoryStorage.writeFile(inventoryModel.getInventoryList());
        } catch (IOException | NoSuchIndexException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
