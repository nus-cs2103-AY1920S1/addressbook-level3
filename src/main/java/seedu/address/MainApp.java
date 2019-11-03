package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;
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
import seedu.address.model.ActivityBook;
import seedu.address.model.AddressBook;
import seedu.address.model.InternalState;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.ActivityBookStorage;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.InternalStateStorage;
import seedu.address.storage.JsonActivityBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonInternalStateStorage;
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
    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info(
            "=============================[ Initializing AddressBook ]==========================="
        );
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
            config.getUserPrefsFilePath()
        );
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        InternalStateStorage internalStateStorage = new JsonInternalStateStorage(
            userPrefs.getInternalStateFilePath()
        );
        InternalState internalState = initState(internalStateStorage);

        ActivityBookStorage activityBookStorage = new JsonActivityBookStorage(userPrefs.getActivityBookFilePath());

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(
            userPrefs.getAddressBookFilePath()
        );

        storage =
            new StorageManager(
                addressBookStorage,
                userPrefsStorage,
                internalStateStorage,
                activityBookStorage
            );

        initLogging(config);

        model = initModelManager(storage, userPrefs, internalState, activityBookStorage);
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
    }

    /**
    * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
    * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
    * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
    */
    private Model initModelManager(
        Storage storage,
        ReadOnlyUserPrefs userPrefs,
        InternalState internalState,
        ActivityBookStorage activityBookStorage
    ) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        Optional<ActivityBook> activityBookOptional;
        ReadOnlyAddressBook initialData;
        ActivityBook initialActivityBook;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info(
                    "Data file not found. Will be starting with a sample AddressBook"
                );
            }
            initialData =
                addressBookOptional.orElseGet(
                    SampleDataUtil::getSampleAddressBook
                );
            // Update initial state after initialising sample list of contacts
            internalState.updateInternalState();
        } catch (DataConversionException e) {
            logger.warning(
                "Data file not in the correct format. Will be starting with an empty AddressBook"
            );
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning(
                "Problem while reading from the file. Will be starting with an empty AddressBook"
            );
            initialData = new AddressBook();
        }

        try {
            activityBookOptional = activityBookStorage.readActivityBook();
            if (!activityBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty ActivityBook");
            }
            initialActivityBook = activityBookOptional.orElseGet(() -> new ActivityBook());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ActivityBook");
            initialActivityBook = new ActivityBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ActivityBook");
            initialActivityBook = new ActivityBook();
        }

        return new ModelManager(initialData, userPrefs, internalState, initialActivityBook);
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
            Optional<Config> configOptional = ConfigUtil.readConfig(
                configFilePathUsed
            );
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning(
                "Config file at " + configFilePathUsed
                + " is not in the correct format. "
                + "Using default config properties"
            );
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning(
                "Failed to save config file : " + StringUtil.getDetails(e)
            );
        }
        return initializedConfig;
    }

    /**
    * Returns a {@code InternalState} using the file at {@code storage}'s user prefs file path,
    * or a new {@code InternalState} with default configuration if errors occur when
    * reading from the file.
    */
    protected InternalState initState(InternalStateStorage storage) {
        Path stateFilePath = storage.getInternalStateFilePath();
        logger.info("Using state file : " + stateFilePath);

        InternalState initState;
        Consumer<InternalState> writeFile = (state) -> {
            //Update state file in case it was missing to begin with or there are new/unused fields
            try {
                storage.saveInternalState(state);
            } catch (IOException e) {
                logger.warning(
                    "Failed to save state file : " + StringUtil.getDetails(e)
                );
            }
        };
        try {
            Optional<InternalState> stateOptional = storage.readInternalState();
            initState = stateOptional.orElse(new InternalState());
        } catch (DataConversionException e) {
            logger.warning(
                "InternalState file at " + stateFilePath
                + " is not in the correct format. "
                + "Using default user prefs");
            initState = new InternalState();
            writeFile.accept(initState);
        } catch (IOException e) {
            // TODO: Reset the program here
            logger.warning(
                "Problem while reading from the file. Program execution undefined."
            );
            initState = new InternalState();
            writeFile.accept(initState);
        }

        initState.applyInternalState();
        return initState;
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
            logger.warning("UserPrefs file at " + prefsFilePath
                    + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning(
                "Problem while reading from the file. Will be starting with an empty AddressBook"
            );
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning(
                "Failed to save config file : " + StringUtil.getDetails(e)
            );
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
        logger.info(
            "============================ [ Stopping Address Book ] ============================="
        );
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            storage.saveInternalState(model.getInternalState());
        } catch (IOException e) {
            logger.severe(
                "Failed to save preferences " + StringUtil.getDetails(e)
            );
        }
    }
}
