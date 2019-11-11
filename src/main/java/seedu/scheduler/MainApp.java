package seedu.scheduler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import seedu.scheduler.commons.core.Config;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.core.Version;
import seedu.scheduler.commons.exceptions.DataConversionException;
import seedu.scheduler.commons.util.ConfigUtil;
import seedu.scheduler.commons.util.StringUtil;
import seedu.scheduler.logic.Logic;
import seedu.scheduler.logic.LogicManager;
import seedu.scheduler.model.IntervieweeList;
import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.ReadOnlyUserPrefs;
import seedu.scheduler.model.SampleData;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.storage.IntervieweeListStorage;
import seedu.scheduler.storage.InterviewerListStorage;
import seedu.scheduler.storage.JsonIntervieweeListStorage;
import seedu.scheduler.storage.JsonInterviewerListStorage;
import seedu.scheduler.storage.JsonUserPrefsStorage;
import seedu.scheduler.storage.Storage;
import seedu.scheduler.storage.StorageManager;
import seedu.scheduler.storage.UserPrefsStorage;
import seedu.scheduler.ui.Ui;
import seedu.scheduler.ui.UiManager;

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

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Scheduler ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        IntervieweeListStorage intervieweeListStorage = new JsonIntervieweeListStorage(
                userPrefs.getIntervieweeListFilePath());
        InterviewerListStorage interviewerListStorage = new JsonInterviewerListStorage(
                userPrefs.getInterviewerListFilePath());
        storage = new StorageManager(intervieweeListStorage, interviewerListStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);

        model.addRefreshListener(ui);
        model.addTabListener(ui);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s interviewee list, interviewer list
     * and {@code userPrefs}. <br>
     * The data from the sample interviewee and interviewer list will be used instead if {@code storage}'s
     * interviewee and interviewer lists are not found, or an empty interviewee and interviewer list will be
     * used instead if errors occur when reading {@code storage}'s interviewee and interviewer lists.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyList<Interviewee>> intervieweeListOptional;
        Optional<ReadOnlyList<Interviewer>> interviewerListOptional;
        ReadOnlyList<Interviewee> initialIntervieweeList;
        ReadOnlyList<Interviewer> initialInterviewerList;

        try {
            intervieweeListOptional = storage.readIntervieweeList();

            if (!intervieweeListOptional.isPresent()) {
                logger.info("Interviewee data file not found, will be starting with a sample list of "
                        + "Interviewees");
            }

            initialIntervieweeList = intervieweeListOptional.orElseGet(SampleData::getSampleIntervieweeList);
        } catch (DataConversionException e) {
            logger.warning("Interviewee data file not in the correct format. Will be starting with an "
                    + "empty list of Interviewees");
            initialIntervieweeList = new IntervieweeList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the Interviewee data file. Will be starting with "
                    + "an empty list of Interviewees");
            initialIntervieweeList = new IntervieweeList();
        }

        try {
            interviewerListOptional = storage.readInterviewerList();

            if (!interviewerListOptional.isPresent()) {
                logger.info("Interviewer data file not found, will be starting with a sample list of "
                        + "Interviewers");
            }

            initialInterviewerList = interviewerListOptional.orElseGet(SampleData::getSampleInterviewerList);
        } catch (DataConversionException e) {
            logger.warning("Interviewer data file not in the correct format. Will be starting with an "
                    + "empty list of Interviewers");
            initialInterviewerList = new InterviewerList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the Interviewer data file. Will be starting with "
                    + "an empty list of Interviewers");
            initialInterviewerList = new InterviewerList();
        }
        return new ModelManager(initialIntervieweeList, initialInterviewerList, userPrefs, new LinkedList<>());

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
            logger.warning("Problem while reading from the file. Will be starting with an empty list of "
                    + "Interviewees and Interviewers");
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
        logger.info("Starting Scheduler " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Scheduler ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
