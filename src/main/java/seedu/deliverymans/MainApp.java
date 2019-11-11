package seedu.deliverymans;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.deliverymans.commons.core.Config;
import seedu.deliverymans.commons.core.LogsCenter;
import seedu.deliverymans.commons.core.Version;
import seedu.deliverymans.commons.exceptions.DataConversionException;
import seedu.deliverymans.commons.util.ConfigUtil;
import seedu.deliverymans.commons.util.StringUtil;
import seedu.deliverymans.logic.Logic;
import seedu.deliverymans.logic.LogicManager;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.ModelManager;
import seedu.deliverymans.model.ReadOnlyUserPrefs;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.database.CustomerDatabase;
import seedu.deliverymans.model.database.DeliverymenDatabase;
import seedu.deliverymans.model.database.OrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyCustomerDatabase;
import seedu.deliverymans.model.database.ReadOnlyDeliverymenDatabase;
import seedu.deliverymans.model.database.ReadOnlyOrderDatabase;
import seedu.deliverymans.model.database.ReadOnlyRestaurantDatabase;
import seedu.deliverymans.model.database.RestaurantDatabase;
import seedu.deliverymans.model.util.SampleDataUtil;
import seedu.deliverymans.storage.JsonUserPrefsStorage;
import seedu.deliverymans.storage.Storage;
import seedu.deliverymans.storage.StorageManager;
import seedu.deliverymans.storage.UserPrefsStorage;
import seedu.deliverymans.storage.customer.CustomerDatabaseStorage;
import seedu.deliverymans.storage.customer.JsonCustomerDatabaseStorage;
import seedu.deliverymans.storage.deliveryman.DeliverymenDatabaseStorage;
import seedu.deliverymans.storage.deliveryman.JsonDeliverymenDatabaseStorage;
import seedu.deliverymans.storage.order.JsonOrderDatabaseStorage;
import seedu.deliverymans.storage.order.OrderDatabaseStorage;
import seedu.deliverymans.storage.restaurant.JsonRestaurantDatabaseStorage;
import seedu.deliverymans.storage.restaurant.RestaurantDatabaseStorage;
import seedu.deliverymans.ui.Ui;
import seedu.deliverymans.ui.UiManager;

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
        logger.info("=============================[ Initializing DeliveryMANS... ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        CustomerDatabaseStorage customerDatabaseStorage =
                new JsonCustomerDatabaseStorage(userPrefs.getCustomerDatabaseFilePath());
        DeliverymenDatabaseStorage deliverymenDatabaseStorage =
                new JsonDeliverymenDatabaseStorage(userPrefs.getDeliverymenDatabaseFilePath());
        RestaurantDatabaseStorage restaurantDatabaseStorage =
                new JsonRestaurantDatabaseStorage(userPrefs.getRestaurantDatabaseFilePath());
        OrderDatabaseStorage orderDatabaseStorage = new JsonOrderDatabaseStorage(userPrefs.getOrderBookFilePath());

        storage = new StorageManager(customerDatabaseStorage, deliverymenDatabaseStorage,
                restaurantDatabaseStorage, orderDatabaseStorage, userPrefsStorage);

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
        Optional<ReadOnlyCustomerDatabase> customerDatabaseOptional;
        Optional<ReadOnlyDeliverymenDatabase> deliverymenDatabaseOptional;
        Optional<ReadOnlyRestaurantDatabase> restaurantDatabaseOptional;
        Optional<ReadOnlyOrderDatabase> orderBookOptional;

        ReadOnlyCustomerDatabase initialCustomerData;
        ReadOnlyDeliverymenDatabase initialDeliverymenData;
        ReadOnlyRestaurantDatabase initialRestaurantData;
        ReadOnlyOrderDatabase initialOrderData;

        try {
            customerDatabaseOptional = storage.readCustomerDatabase();
            if (!customerDatabaseOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample CustomerDatabase");
            }
            initialCustomerData = customerDatabaseOptional.orElseGet(SampleDataUtil::getSampleCustomerDatabase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. "
                    + "Will be starting with an empty CustomerDatabase");
            initialCustomerData = new CustomerDatabase();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Will be starting with an empty CustomerDatabase");
            initialCustomerData = new CustomerDatabase();
        }

        try {
            deliverymenDatabaseOptional = storage.readDeliverymenDatabase();
            if (!deliverymenDatabaseOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample DeliverymenDatabase");
            }
            initialDeliverymenData =
                    deliverymenDatabaseOptional.orElseGet(SampleDataUtil::getSampleDeliverymenDatabase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. "
                    + "Will be starting with an empty DeliverymenDatabase");
            initialDeliverymenData = new DeliverymenDatabase();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Will be starting with an empty DeliverymenDatabase");
            initialDeliverymenData = new DeliverymenDatabase();
        }

        try {
            restaurantDatabaseOptional = storage.readRestaurantDatabase();
            if (!restaurantDatabaseOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample RestaurantDatabase");
            }
            initialRestaurantData = restaurantDatabaseOptional.orElseGet(SampleDataUtil::getSampleRestaurantDatabase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. "
                    + "Will be starting with an empty RestaurantDatabase");
            initialRestaurantData = new RestaurantDatabase();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Will be starting with an empty RestaurantDatabase");
            initialRestaurantData = new RestaurantDatabase();
        }

        try {
            orderBookOptional = storage.readOrderBook();
            if (!orderBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample OrderBook");
            }
            initialOrderData = orderBookOptional.orElseGet(SampleDataUtil::getSampleOrderDatabase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty OrderBook");
            initialOrderData = new OrderDatabase();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty OrderBook");
            initialOrderData = new OrderDatabase();
        }

        return new ModelManager(initialCustomerData, initialDeliverymenData, initialRestaurantData,
                initialOrderData, userPrefs);
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
        logger.info("Starting DeliveryMANS " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping DeliveryMANS... ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
