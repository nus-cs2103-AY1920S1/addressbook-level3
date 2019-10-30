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
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;
//import seedu.address.model.util.SampleDataUtil;
import seedu.address.statistic.Statistic;
import seedu.address.statistic.StatisticManager;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.CustomerBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonCustomerBookStorage;
import seedu.address.storage.JsonOrderBookStorage;
import seedu.address.storage.JsonPhoneBookStorage;
import seedu.address.storage.JsonScheduleBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.OrderBookStorage;
import seedu.address.storage.PhoneBookStorage;
import seedu.address.storage.ScheduleBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
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
    protected Statistic statistic;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing T09-4 project  ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        CustomerBookStorage customerBookStorage = new JsonCustomerBookStorage(userPrefs.getCustomerBookFilePath());
        PhoneBookStorage phoneBookStorage = new JsonPhoneBookStorage(userPrefs.getPhoneBookFilePath());
        ScheduleBookStorage scheduleBookStorage = new JsonScheduleBookStorage(userPrefs.getScheduleBookFilePath());
        OrderBookStorage orderBookStorage = new JsonOrderBookStorage(userPrefs.getOrderBookFilePath());
        OrderBookStorage archivedOrderBookStorage = new JsonOrderBookStorage(userPrefs.getArchivedOrderBookFilePath());
        storage = new StorageManager(addressBookStorage, customerBookStorage, phoneBookStorage, scheduleBookStorage,
                orderBookStorage, archivedOrderBookStorage, userPrefsStorage);

        initLogging(config);
        //create statistic manager;
        statistic = new StatisticManager();
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model, storage, statistic);
        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyDataBook<Customer>> customerBookOptional;
        Optional<ReadOnlyDataBook<Phone>> phoneBookOptional;
        Optional<ReadOnlyDataBook<Schedule>> scheduleBookOptional;
        Optional<ReadOnlyDataBook<Order>> orderBookOptional;
        Optional<ReadOnlyDataBook<Order>> archivedOrderBookOptional;

        ReadOnlyDataBook<Customer> initialCustomerData;
        ReadOnlyDataBook<Phone> initialPhoneData;
        ReadOnlyDataBook<Schedule> initialScheduleData;
        ReadOnlyDataBook<Order> initialOrderData;
        ReadOnlyDataBook<Order> initialArchivedOrderData;

        try {
            customerBookOptional = storage.readCustomerBook();

            if (customerBookOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a new Customer DataBook");
            }
            initialCustomerData = customerBookOptional.orElse(new DataBook<>());

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format."
                    + " Will be starting with an empty Customer DataBook");
            initialCustomerData = new DataBook<>();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file."
                    + " Will be starting with an empty Customer DataBook");
            initialCustomerData = new DataBook<>();
        }

        try {
            phoneBookOptional = storage.readPhoneBook();

            if (phoneBookOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a new Phone DataBook");
            }

            initialPhoneData = phoneBookOptional.orElse(new DataBook<>());

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Phone DataBook");
            initialPhoneData = new DataBook<>();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Phone DataBook");
            initialPhoneData = new DataBook<>();
        }

        try {
            scheduleBookOptional = storage.readScheduleBook();

            if (scheduleBookOptional.isEmpty() || storage.readCustomerBook().isEmpty()
                || storage.readPhoneBook().isEmpty() || storage.readOrderBook().isEmpty()) {
                logger.info("Data file not found. Will be starting with a new Schedule DataBook");
                initialScheduleData = new DataBook<>();
            } else {
                initialScheduleData = scheduleBookOptional.orElse(new DataBook<>());
            }


        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Schedule DataBook");
            initialScheduleData = new DataBook<>();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Schedule DataBook");
            initialScheduleData = new DataBook<>();
        }

        try {
            orderBookOptional = storage.readOrderBook();

            if (orderBookOptional.isEmpty() || storage.readCustomerBook().isEmpty()
                || storage.readPhoneBook().isEmpty() || storage.readScheduleBook().isEmpty()) {
                logger.info("Data file not found. Will be starting with a new Order DataBook");
                initialOrderData = new DataBook<>();
            } else {
                initialOrderData = orderBookOptional.orElse(new DataBook<>());

            }



        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Order DataBook");

            initialOrderData = new DataBook<>();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Order DataBook");
            initialOrderData = new DataBook<>();
        }

        try {
            archivedOrderBookOptional = storage.readArchivedOrderBook();

            if (!archivedOrderBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Archived Order DataBook");
            }

            initialArchivedOrderData = archivedOrderBookOptional.orElse(new DataBook<Order>());

        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Order DataBook");

            initialArchivedOrderData = new DataBook<>();

        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Order DataBook");
            initialArchivedOrderData = new DataBook<>();
        }


        for (Order o: initialOrderData.getList()) {
            if (o.getStatus().equals(Status.CANCELLED) || o.getStatus().equals(Status.COMPLETED)) {
                initialOrderData.getList().remove(o);
                initialArchivedOrderData.getList().add(o);
            }
        }

        for (Order o: initialArchivedOrderData.getList()) {
            if (!o.getStatus().equals(Status.CANCELLED) && !o.getStatus().equals(Status.COMPLETED)) {
                initialArchivedOrderData.getList().remove(o);
                initialOrderData.getList().add(o);
            }
        }


        return new ModelManager(initialCustomerData, initialPhoneData, initialOrderData, initialScheduleData,
                initialArchivedOrderData, userPrefs);
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
        logger.info("============================ [ Stopping Seller Manager ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
