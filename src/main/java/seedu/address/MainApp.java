package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
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
import seedu.address.model.ModulePlanner;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.ModulePlannerStorage;
import seedu.address.storage.JsonModulePlannerStorage;
import seedu.address.storage.JsonModulesInfoStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.ModulesInfoStorage;
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
        logger.info("=============================[ Initializing ModulePlanner ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        ModulePlannerStorage modulePlannerStorage = new JsonModulePlannerStorage(userPrefs.getModulePlannerFilePath());
        ModulesInfoStorage modulesInfoStorage = new JsonModulesInfoStorage(config.getModulesInfoFilePath());
        ModulesInfo modulesInfo = initModulesInfo(modulesInfoStorage);

        // TODO: modulesInfo is not used from here on out -- use it with ModelManager
        // These show how the module information could be used for verification
        modulesInfo.verify("CS4248", Arrays.asList(new String[] {"CS3243", "ST2334"})); // true
        modulesInfo.verify("CS4248", Arrays.asList(new String[] {"CS3245", "ST2334"})); // true
        modulesInfo.verify("CS4248", Arrays.asList(new String[] {"CS3243", "ST2131"})); // false

        storage = new StorageManager(modulePlannerStorage, userPrefsStorage, modulesInfoStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs, modulesInfo);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s module planner and {@code userPrefs}. <br>
     * The data from the sample module planner will be used instead if {@code storage}'s module planner is not found,
     * or an empty module planner will be used instead if errors occur when reading {@code storage}'s module planner.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs, ModulesInfo modulesInfo) {
        Optional<ReadOnlyModulePlanner> modulePlannerOptional;
        ReadOnlyModulePlanner initialData;
        try {
            modulePlannerOptional = storage.readModulePlanner(modulesInfo);
            if (!modulePlannerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ModulePlanner");
            }
            initialData = modulePlannerOptional.orElseGet(() -> SampleDataUtil.getSampleModulePlanner(modulesInfo));
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with a sample ModulePlanner");
            initialData = SampleDataUtil.getSampleModulePlanner(modulesInfo);
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with a sample ModulePlanner");
            initialData = SampleDataUtil.getSampleModulePlanner(modulesInfo);
        }

        return new ModelManager(initialData, userPrefs, modulesInfo);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ModulePlanner");
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

    /**
     * Returns a {@code ModulesInfo} using the file at {@code storage}'s modules info file path.
     */
    protected ModulesInfo initModulesInfo(ModulesInfoStorage storage) {
        Path prefsFilePath = storage.getModulesInfoPath();
        logger.info("Using modules info file : " + prefsFilePath);

        ModulesInfo initializedModulesInfo;
        try {
            Optional<ModulesInfo> prefsOptional = storage.readModulesInfo();
            initializedModulesInfo = prefsOptional.orElse(new ModulesInfo());
        } catch (DataConversionException e) {
            logger.warning("ModulesInfo file at " + prefsFilePath + " is not in the correct format. "
                    + "Will proceed without modules information");
            initializedModulesInfo = new ModulesInfo();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting without modules information");
            initializedModulesInfo = new ModulesInfo();
        }

        return initializedModulesInfo;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ModulePlanner " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Module Planner ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
