package mams;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import mams.commons.core.Config;
import mams.commons.core.LogsCenter;
import mams.commons.core.Version;
import mams.commons.exceptions.DataConversionException;
import mams.commons.util.ConfigUtil;
import mams.commons.util.StringUtil;
import mams.logic.Logic;
import mams.logic.LogicManager;
import mams.model.Mams;
import mams.model.Model;
import mams.model.ModelManager;
import mams.model.ReadOnlyMams;
import mams.model.ReadOnlyUserPrefs;
import mams.model.UserPrefs;
import mams.storage.CommandHistoryStorage;
import mams.storage.JsonCommandHistoryStorage;
import mams.storage.JsonMamsStorage;
import mams.storage.JsonUserPrefsStorage;
import mams.storage.MamsStorage;
import mams.storage.Storage;
import mams.storage.StorageManager;
import mams.storage.UserPrefsStorage;
import mams.ui.Ui;
import mams.ui.UiManager;

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
        logger.info("=============================[ Initializing MAMS ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        MamsStorage mamsStorage = new JsonMamsStorage(userPrefs.getMamsFilePath());
        CommandHistoryStorage commandHistoryStorage =
                new JsonCommandHistoryStorage(userPrefs.getCommandHistoryFilePath());
        storage = new StorageManager(mamsStorage, userPrefsStorage, commandHistoryStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from the MAMS in {@code storage} and {@code userPrefs}. <br>
     * The data from the sample MAMS will be used instead if MAMS in {@code storage} is not found,
     * or an empty MAMS will be used instead if errors occur when reading MAMS in {@code storage}.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyMams> mamsOptional;
        ReadOnlyMams initialData;
        try {
            mamsOptional = storage.readMams();
            if (!mamsOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample MAMS");
            }
            initialData = mamsOptional.orElse(new Mams());
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty MAMS");
            initialData = new Mams();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty MAMS");
            initialData = new Mams();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty MAMS");
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
        logger.info("Starting MAMS " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping MAMS ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
