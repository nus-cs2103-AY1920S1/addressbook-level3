package seedu.guilttrip;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.guilttrip.commons.core.Config;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.core.Version;
import seedu.guilttrip.commons.exceptions.DataConversionException;
import seedu.guilttrip.commons.util.ConfigUtil;
import seedu.guilttrip.commons.util.StringUtil;
import seedu.guilttrip.commons.util.TimeUtil;
import seedu.guilttrip.logic.Logic;
import seedu.guilttrip.logic.LogicManager;
import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.ReadOnlyUserPrefs;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.util.SampleDataUtil;
import seedu.guilttrip.storage.GuiltTripStorage;
import seedu.guilttrip.storage.JsonGuiltTripStorage;
import seedu.guilttrip.storage.JsonUserPrefsStorage;
import seedu.guilttrip.storage.Storage;
import seedu.guilttrip.storage.StorageManager;
import seedu.guilttrip.storage.UserPrefsStorage;
import seedu.guilttrip.ui.Ui;
import seedu.guilttrip.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing GuiltTrip ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GuiltTripStorage guiltTripStorage = new JsonGuiltTripStorage(userPrefs.getGuiltTripFilePath());
        storage = new StorageManager(guiltTripStorage, userPrefsStorage);

        initLogging(config);
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
        GuiltTrip guiltTrip = (GuiltTrip) model.getGuiltTrip();
        guiltTrip.linkReminderListToUi((UiManager) ui);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s GuiltTip and {@code userPrefs}. <br>
     * The data from the sample GuiltTrip will be used instead if {@code storage}'s GuiltTrip is not found,
     * or an empty GuiltTrip will be used instead if errors occur when reading {@code storage}'s GuiltTrip.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        TimeUtil.startTimer();
        Optional<ReadOnlyGuiltTrip> guiltTripOptional;
        ReadOnlyGuiltTrip initialData;
        try {
            guiltTripOptional = storage.readGuiltTrip();

            if (guiltTripOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample GuiltTrip");
            }
            initialData = guiltTripOptional.orElseGet(SampleDataUtil::getSampleGuiltTrip);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GuiltTrip");
            initialData = SampleDataUtil.getSampleGuiltTrip();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GuiltTrip");
            initialData = SampleDataUtil.getSampleGuiltTrip();
        } catch (IllegalArgumentException e) {
            logger.warning("Problem while reading from the file. There is a duplicate category and "
                    + "will be starting with an empty GuiltTrip");
            initialData = SampleDataUtil.getSampleGuiltTrip();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty GuiltTrip");
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
        logger.info("Starting GuiltTrip " + MainApp.VERSION);
        ui.start(primaryStage);
        TimeUtil.startTimer();
    }

    @Override
    public void stop() {
        TimeUtil.endTimer();
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
