package seedu.planner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.planner.commons.core.Config;
import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.core.Version;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.util.ConfigUtil;
import seedu.planner.commons.util.StringUtil;
import seedu.planner.logic.Logic;
import seedu.planner.logic.LogicManager;

import seedu.planner.model.AccommodationManager;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
import seedu.planner.model.ReadOnlyUserPrefs;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.util.SampleDataUtil;
import seedu.planner.storage.JsonUserPrefsStorage;
import seedu.planner.storage.Storage;
import seedu.planner.storage.StorageManager;
import seedu.planner.storage.UserPrefsStorage;
import seedu.planner.storage.accommodation.AccommodationStorage;
import seedu.planner.storage.accommodation.JsonAccommodationStorage;
import seedu.planner.storage.activity.ActivityStorage;
import seedu.planner.storage.activity.JsonActivityStorage;
import seedu.planner.storage.contact.ContactStorage;
import seedu.planner.storage.contact.JsonContactStorage;
import seedu.planner.storage.day.ItineraryStorage;
import seedu.planner.storage.day.JsonItineraryStorage;
import seedu.planner.ui.Ui;
import seedu.planner.ui.UiManager;

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
        logger.info("=============================[ Initializing Planner ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AccommodationStorage accommodationStorage = new JsonAccommodationStorage(userPrefs.getAccommodationFilePath());
        ActivityStorage activityStorage = new JsonActivityStorage(userPrefs.getActivityFilePath());
        ContactStorage contactStorage = new JsonContactStorage(userPrefs.getContactFilePath());
        ItineraryStorage itineraryStorage = new JsonItineraryStorage(userPrefs.getItineraryFilePath());
        storage = new StorageManager(accommodationStorage, activityStorage, contactStorage, itineraryStorage,
                userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s accommodation, activity, contact,
     * itinerary and {@code userPrefs}.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyAccommodation accommodation = initAccommodationManager(storage);
        ReadOnlyActivity activity = initActivityManager(storage);
        ReadOnlyContact contact = initContactManager(storage);
        ReadOnlyItinerary itinerary = initItinerary(storage);
        return new ModelManager(accommodation, activity, contact, itinerary, userPrefs);
    }

    /**
     * Returns a {@code AccommodationManager} with the data from {@code storage}'s accommodation and {@code userPrefs}.
     * <br> The data from the sample accommodationManager will be used instead if {@code storage}'s
     * accommodation is not found, or an empty accommodationManager will be used instead if errors occur
     * when reading {@code storage}'s accommodation.
     */
    private ReadOnlyAccommodation initAccommodationManager(Storage storage) {
        Optional<ReadOnlyAccommodation> accommodationManagerOptional;
        ReadOnlyAccommodation initialData;
        try {
            accommodationManagerOptional = storage.readAccommodation();
            if (!accommodationManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AccommodationManager");
            }
            initialData = accommodationManagerOptional.orElseGet(SampleDataUtil::getSampleAccommodationManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty "
                    + "AccommodationManager");
            initialData = new AccommodationManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "AccommodationManager");
            initialData = new AccommodationManager();
        }
        return initialData;
    }

    /**
     * Returns a {@code ActivityManager} with the data from {@code storage}'s activity and {@code userPrefs}.
     * <br> The data from the sample activityManager will be used instead if {@code storage}'s activity is not found,
     * or an empty activityManager will be used instead if errors occur when reading {@code storage}'s activity.
     */
    private ReadOnlyActivity initActivityManager(Storage storage) {
        Optional<ReadOnlyActivity> activityManagerOptional;
        ReadOnlyActivity initialData;
        try {
            activityManagerOptional = storage.readActivity();
            if (!activityManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ActivityManager");
            }
            initialData = activityManagerOptional.orElseGet(SampleDataUtil::getSampleActivityManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty "
                    + "ActivityManager");
            initialData = new ActivityManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "ActivityManager");
            initialData = new ActivityManager();
        }
        return initialData;
    }

    /**
     * Returns a {@code ContactManager} with the data from {@code storage}'s contact and {@code userPrefs}. <br>
     * The data from the sample contactManager will be used instead if {@code storage}'s contact is not found,
     * or an empty contactManager will be used instead if errors occur when reading {@code storage}'s contact.
     */
    private ReadOnlyContact initContactManager(Storage storage) {
        Optional<ReadOnlyContact> contactManagerOptional;
        ReadOnlyContact initialData;
        try {
            contactManagerOptional = storage.readContact();
            if (!contactManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ContactManager");
            }
            initialData = contactManagerOptional.orElseGet(SampleDataUtil::getSampleContactManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty "
                    + "ContactManager");
            initialData = new ContactManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "ContactManager");
            initialData = new ContactManager();
        }
        return initialData;
    }

    /**
     * Returns a {@code Itinerary} with the data from {@code storage}'s itinerary and {@code userPrefs}. <br>
     * The data from the sample itinerary will be used instead if {@code storage}'s itinerary is not found,
     * or an empty itinerary will be used instead if errors occur when reading {@code storage}'s itinerary.
     */
    private ReadOnlyItinerary initItinerary(Storage storage) {
        Optional<ReadOnlyItinerary> itineraryOptional;
        ReadOnlyItinerary initialData;
        try {
            itineraryOptional = storage.readItinerary();
            if (!itineraryOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Itinerary");
            }
            initialData = itineraryOptional.orElseGet(SampleDataUtil::getSampleItinerary);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty "
                    + "Itinerary");
            initialData = new Itinerary();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty "
                    + "Itinerary");
            initialData = new Itinerary();
        }
        return initialData;
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Planner");
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
        logger.info("Starting Planner " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Plan2Travel ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
