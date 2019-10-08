package thrift;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import thrift.commons.core.Config;
import thrift.commons.core.LogsCenter;
import thrift.commons.core.Version;
import thrift.commons.exceptions.DataConversionException;
import thrift.commons.util.ConfigUtil;
import thrift.commons.util.StringUtil;
import thrift.logic.Logic;
import thrift.logic.LogicManager;
import thrift.model.Model;
import thrift.model.ModelManager;
import thrift.model.PastUndoableCommands;
import thrift.model.ReadOnlyThrift;
import thrift.model.ReadOnlyUserPrefs;
import thrift.model.Thrift;
import thrift.model.UserPrefs;
import thrift.model.util.SampleDataUtil;
import thrift.storage.JsonThriftStorage;
import thrift.storage.JsonUserPrefsStorage;
import thrift.storage.Storage;
import thrift.storage.StorageManager;
import thrift.storage.ThriftStorage;
import thrift.storage.UserPrefsStorage;
import thrift.ui.Ui;
import thrift.ui.UiManager;

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
        logger.info("=============================[ Initializing THRIFT ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ThriftStorage thriftStorage = new JsonThriftStorage(userPrefs.getThriftFilePath());
        storage = new StorageManager(thriftStorage, userPrefsStorage);
        PastUndoableCommands pastUndoableCommands = new PastUndoableCommands();

        initLogging(config);

        model = initModelManager(storage, userPrefs, pastUndoableCommands);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s thrift and {@code userPrefs}. <br>
     * The data from the sample thrift will be used instead if {@code storage}'s thrift is not found,
     * or an empty thrift will be used instead if errors occur when reading {@code storage}'s thrift.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs,
            PastUndoableCommands pastUndoableCommands) {
        Optional<ReadOnlyThrift> thriftOptional;
        ReadOnlyThrift initialData;
        try {
            thriftOptional = storage.readThrift();
            if (!thriftOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample THRIFT");
            }
            initialData = thriftOptional.orElseGet(SampleDataUtil::getSampleThrift);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty THRIFT");
            initialData = new Thrift();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty THRIFT");
            initialData = new Thrift();
        }

        return new ModelManager(initialData, userPrefs, pastUndoableCommands);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty THRIFT");
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
        logger.info("Starting THRIFT " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping THRIFT ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
