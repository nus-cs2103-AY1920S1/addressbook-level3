package seedu.algobase;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.algobase.commons.core.Config;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.commons.core.Version;
import seedu.algobase.commons.exceptions.DataConversionException;
import seedu.algobase.commons.util.ConfigUtil;
import seedu.algobase.commons.util.StringUtil;
import seedu.algobase.logic.Logic;
import seedu.algobase.logic.LogicManager;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.ReadOnlyUserPrefs;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.util.SampleDataUtil;
import seedu.algobase.storage.AlgoBaseStorage;
import seedu.algobase.storage.JsonAlgoBaseStorage;
import seedu.algobase.storage.JsonUserPrefsStorage;
import seedu.algobase.storage.Storage;
import seedu.algobase.storage.StorageManager;
import seedu.algobase.storage.UserPrefsStorage;
import seedu.algobase.ui.Ui;
import seedu.algobase.ui.UiManager;
import seedu.algobase.ui.action.UiLogic;
import seedu.algobase.ui.action.UiLogicManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected UiLogic uiLogic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AlgoBase ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        // User Preferences
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        // AlgoBase Storage
        AlgoBaseStorage algoBaseStorage = new JsonAlgoBaseStorage(userPrefs.getAlgoBaseFilePath());
        storage = new StorageManager(algoBaseStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);
        uiLogic = new UiLogicManager(model, storage);

        ui = new UiManager(logic, uiLogic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s algobase and {@code userPrefs}. <br>
     * The data from the sample algobase will be used instead if {@code storage}'s algobase is not found,
     * or an empty algobase will be used instead if errors occur when reading {@code storage}'s algobase.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyAlgoBase> algoBaseOptional;
        ReadOnlyAlgoBase initialData;
        try {
            algoBaseOptional = storage.readAlgoBase();
            if (!algoBaseOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AlgoBase");
            }
            initialData = algoBaseOptional.orElseGet(SampleDataUtil::getSampleAlgoBase);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AlgoBase");
            initialData = new AlgoBase();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AlgoBase");
            initialData = new AlgoBase();
        } catch (Exception e) {
            logger.warning(
                String.format(
                    "Problem reading from the file - %s exception occurred. Will be starting with an empty AlgoBase",
                    e.getClass().getSimpleName()
                )
            );
            initialData = new AlgoBase();
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
            logger.warning("Problem while reading from the file. Will be starting with an empty AlgoBase");
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
        logger.info("Starting AlgoBase " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping AlgoBase ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
