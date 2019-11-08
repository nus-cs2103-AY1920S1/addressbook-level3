package seedu.jarvis;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.jarvis.commons.core.Config;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.commons.core.Version;
import seedu.jarvis.commons.exceptions.DataConversionException;
import seedu.jarvis.commons.util.ConfigUtil;
import seedu.jarvis.commons.util.StringUtil;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.logic.LogicManager;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.ModelManager;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;
import seedu.jarvis.model.util.SampleDataUtil;
import seedu.jarvis.storage.Storage;
import seedu.jarvis.storage.StorageManager;
import seedu.jarvis.storage.cca.CcaTrackerStorage;
import seedu.jarvis.storage.cca.JsonCcaTrackerStorage;
import seedu.jarvis.storage.course.CoursePlannerStorage;
import seedu.jarvis.storage.course.JsonCoursePlannerStorage;
import seedu.jarvis.storage.finance.FinanceTrackerStorage;
import seedu.jarvis.storage.finance.JsonFinanceTrackerStorage;
import seedu.jarvis.storage.history.HistoryManagerStorage;
import seedu.jarvis.storage.history.JsonHistoryManagerStorage;
import seedu.jarvis.storage.planner.JsonPlannerStorage;
import seedu.jarvis.storage.planner.PlannerStorage;
import seedu.jarvis.storage.userprefs.JsonUserPrefsStorage;
import seedu.jarvis.storage.userprefs.UserPrefsStorage;
import seedu.jarvis.ui.Ui;
import seedu.jarvis.ui.UiManager;

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
        logger.info("=============================[ Initializing JARVIS ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        HistoryManagerStorage historyManagerStorage = new JsonHistoryManagerStorage(
                userPrefs.getHistoryManagerFilePath());
        CcaTrackerStorage ccaTrackerStorage = new JsonCcaTrackerStorage(userPrefs.getCcaTrackerFilePath());
        CoursePlannerStorage coursePlannerStorage = new JsonCoursePlannerStorage(userPrefs.getCoursePlannerFilePath());
        PlannerStorage plannerStorage = new JsonPlannerStorage(userPrefs.getPlannerFilePath());
        FinanceTrackerStorage financeTrackerStorage = new JsonFinanceTrackerStorage(userPrefs.getFinanceTrackerPath());

        storage = new StorageManager(userPrefsStorage, historyManagerStorage, ccaTrackerStorage,
                coursePlannerStorage, plannerStorage, financeTrackerStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic, model);
    }

    /**
     * Returns a {@code ModelManager} with the data from the {@code storage} and
     * {@code userPrefs}. <br>
     * Sample data is used instead if {@code storage}'s data for each respective component is not
     * found, or empty data will be used instead if errors occur when reading from {@code storage}.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        HistoryManager historyManager = readHistoryManager(storage);
        CcaTracker ccaTracker = readCcaTracker(storage);
        CoursePlanner coursePlanner = readCoursePlanner(storage);
        Planner planner = readPlanner(storage);
        FinanceTracker financeTracker = readFinanceTracker(storage);
        return new ModelManager(ccaTracker, historyManager, financeTracker, userPrefs, planner, coursePlanner);
    }

    /**
     * Gets the {@code HistoryManager} from storage.
     * An empty {@code HistoryManager} is used if errors occur when reading {@code Storage}'s history manager.
     */
    private HistoryManager readHistoryManager(Storage storage) {
        try {
            Optional<HistoryManager> historyManager = storage.readHistoryManager();
            if (historyManager.isPresent()) {
                return historyManager.get();
            } else {
                logger.info("Data file not found. Will be starting with an empty History Manager");
                return new HistoryManager();
            }
        } catch (DataConversionException | IOException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty History Manager");
            return new HistoryManager();
        }
    }

    /**
     * Gets the {@code CcaTracker} from storage.
     * An empty {@code CcaTracker} is used if errors occur when reading {@code Storage}'s Cca Tracker.
     */
    private CcaTracker readCcaTracker(Storage storage) {
        try {
            Optional<CcaTracker> optionalCcaTracker = storage.readCcaTracker();
            if (optionalCcaTracker.isPresent()) {
                return optionalCcaTracker.get();
            } else {
                logger.info("Data file not found. Will be starting with a sample Cca Tracker");
                return SampleDataUtil.getSampleCcaTracker();
            }

        } catch (DataConversionException | IOException e) {
            return new CcaTracker();
        }
    }

    /**
     * Gets the {@code CoursePlanner} from storage.
     * An empty {@code CoursePlanner} is used if errors occur when reading {@code Storage}'s course planner.
     */
    private CoursePlanner readCoursePlanner(Storage storage) {
        try {
            Optional<CoursePlanner> optionalCoursePlanner = storage.readCoursePlanner();
            if (optionalCoursePlanner.isPresent()) {
                return optionalCoursePlanner.get();
            } else {
                logger.info("Data file not found. Will be starting with a sample Course Planner");
                return SampleDataUtil.getSampleCoursePlanner();
            }
        } catch (DataConversionException | IOException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Course Planner");
            return new CoursePlanner();
        }
    }

    /**
     * Gets the {@code Planner} from storage.
     * An empty {@code Planner} is used if errors occur when reading {@code Storage}'s planner.
     */
    private Planner readPlanner(Storage storage) {
        try {
            Optional<Planner> optionalPlanner = storage.readPlanner();
            if (optionalPlanner.isPresent()) {
                return optionalPlanner.get();
            } else {
                logger.info("Data file not found. Will be starting with a sample Planner");
                return SampleDataUtil.getSamplePlanner();
            }
        } catch (DataConversionException | IOException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Planner");
            return new Planner();
        }
    }

    /**
     * Gets the {@code FinanceTracker} from storage.
     * An empty {@code FinanceTracker} is used if errors occur when reading {@code Storage}'s planner.
     */
    private FinanceTracker readFinanceTracker(Storage storage) {
        try {
            Optional<FinanceTracker> optionalFinanceTracker = storage.readFinanceTracker();
            if (optionalFinanceTracker.isPresent()) {
                return optionalFinanceTracker.get();
            } else {
                logger.info("Data file not found. Will be starting with a sample Finance Tracker");
                return SampleDataUtil.getSampleFinanceTracker();
            }
        } catch (DataConversionException | IOException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Finance Tracker");
            return new FinanceTracker();
        }
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
            // TODO check if this is a proper logger message
            logger.warning("Problem while reading from the file. Will be starting with an empty Jarvis.");
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
        logger.info("Starting JARVIS " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Jarvis ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
