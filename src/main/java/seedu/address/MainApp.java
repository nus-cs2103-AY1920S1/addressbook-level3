package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.commons.core.AliasMappings;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.DataInconsistencyException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.MooLah;
import seedu.address.model.ReadOnlyMooLah;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.Timekeeper;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonMooLahStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.MooLahStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 2, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected Timekeeper timekeeper;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing MooLah ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        MooLahStorage mooLahStorage = new JsonMooLahStorage(userPrefs.getMooLahFilePath());
        storage = new StorageManager(mooLahStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        timekeeper = new Timekeeper(logic);

        ui = new UiManager(logic, timekeeper);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s MooLah and {@code userPrefs}. <br>
     * The data from the sample MooLah will be used instead if {@code storage}'s MooLah is not found,
     * or an empty MooLah will be used instead if errors occur when reading {@code storage}'s MooLah.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyMooLah> mooLahOptional;
        ReadOnlyMooLah initialData;
        try {
            mooLahOptional = storage.readMooLah();
            if (!mooLahOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample MooLah");
            }
            initialData = mooLahOptional.orElseGet(SampleDataUtil::getSampleMooLah);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty MooLah");
            initialData = new MooLah();
        } catch (DataInconsistencyException e) {
            logger.warning("There are inconsistencies between expenses in expense list and in budgets.");
            initialData = new MooLah();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty MooLah");
            initialData = new MooLah();
        }

        return new ModelManager(initialData, userPrefs, new ModelHistory());
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
            logger.warning("Problem while reading from the file. Will be starting with an empty MooLah");
            initializedPrefs = new UserPrefs();
        }

        //ensure aliases are valid
        try {
            initializedPrefs.getAliasMappings().validate();
        } catch (Exception e) {
            logger.warning("Problem occurred while reading Aliases from the file. Will be resetting alias mappings: " + e.getMessage());
            initializedPrefs.setAliasMappings(new AliasMappings());
        }

        // Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }


        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting MooLah " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping MooLah ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
