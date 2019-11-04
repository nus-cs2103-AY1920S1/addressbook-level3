package seedu.tarence;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.stage.Stage;
import seedu.tarence.commons.core.Config;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.commons.core.Version;
import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.util.ConfigUtil;
import seedu.tarence.commons.util.StringUtil;
import seedu.tarence.logic.Logic;
import seedu.tarence.logic.LogicManager;
import seedu.tarence.model.Application;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.util.SampleDataUtil;
import seedu.tarence.storage.ApplicationStorage;
import seedu.tarence.storage.JsonApplicationStorage;
import seedu.tarence.storage.JsonStateStorage;
import seedu.tarence.storage.JsonUserPrefsStorage;
import seedu.tarence.storage.Storage;
import seedu.tarence.storage.StorageManager;
import seedu.tarence.storage.UserPrefsStorage;
import seedu.tarence.ui.Ui;
import seedu.tarence.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends javafx.application.Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final String DATA_FOLDER_NAME = "data";
    private static final String STATE_FOLDER_NAME = "state";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing T.A.rence ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ApplicationStorage applicationStorage = new JsonApplicationStorage(userPrefs.getApplicationFilePath());
        // Creates a JsonStateStorage class for Undo.
        JsonStateStorage jsonStateStorage = new JsonStateStorage(DATA_FOLDER_NAME, STATE_FOLDER_NAME);

        storage = new StorageManager(applicationStorage, userPrefsStorage, jsonStateStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s class list and {@code userPrefs}. <br>
     * The data from the sample class list will be used instead if {@code storage}'s class list is not found,
     * or an empty class list will be used instead if errors occur when reading {@code storage}'s class list.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyApplication> applicationOptional;
        ReadOnlyApplication initialData;
        try {
            applicationOptional = storage.readApplication();
            if (!applicationOptional.isPresent()) {
                logger.info("Data file not found. Will be starting the application with sample data");
            }
            initialData = applicationOptional.orElseGet(SampleDataUtil::getSampleApplication);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty class list");
            initialData = new Application();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty class list");
            initialData = new Application();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty class list");
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
        logger.info("Starting T.A.rence " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping T.A.rence ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
            storage.clearStateFolder();
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
