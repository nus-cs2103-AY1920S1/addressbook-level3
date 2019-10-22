package com.dukeacademy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.Config;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.core.Version;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.util.ConfigUtil;
import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.model.prefs.ReadOnlyUserPrefs;
import com.dukeacademy.model.prefs.UserPrefs;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.model.util.SampleDataUtil;
import com.dukeacademy.storage.Storage;
import com.dukeacademy.storage.StorageManager;
import com.dukeacademy.storage.prefs.JsonUserPrefsStorage;
import com.dukeacademy.storage.prefs.UserPrefsStorage;
import com.dukeacademy.storage.question.JsonQuestionBankStorage;
import com.dukeacademy.storage.question.QuestionBankStorage;
import com.dukeacademy.ui.Ui;
import com.dukeacademy.ui.UiManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 6, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Storage storage;
    protected Config config;
    protected CommandLogic commandLogic;
    protected QuestionsLogic questionsLogic;
    protected ProgramSubmissionLogic programSubmissionLogic;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing QuestionBank ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        QuestionBankStorage
            questionBankStorage =
            new JsonQuestionBankStorage(userPrefs.getQuestionBankFilePath());
        storage = new StorageManager(questionBankStorage, userPrefsStorage);

        initLogging(config);

        questionsLogic = this.initQuestionsLogic(userPrefs);
        programSubmissionLogic = this.initProgramSubmissionLogic(userPrefs);
        commandLogic = this.initCommandLogic(userPrefs);
    }

    private QuestionsLogic initQuestionsLogic(ReadOnlyUserPrefs userPrefs) {
        // TODO: initialize questions logic from user prefs
    }

    private ProgramSubmissionLogic initProgramSubmissionLogic(ReadOnlyUserPrefs userPrefs) {
        // TODO: initialize program submission logic from user prefs
    }

    private CommandLogic initCommandLogic(ReadOnlyUserPrefs userPrefs) {
        // TODO: initialize command logic from user prefs and register all command factories
    }

    private Ui initUi(ReadOnlyUserPrefs userPrefs) {
        // TODO: initialize ui from user prefs
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
            logger.warning("Problem while reading from the file. Will be starting with an empty QuestionBank");
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
        logger.info("Starting QuestionBank " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Difficulty Book ] =============================");
//        try {
//            storage.saveUserPrefs(model.getUserPrefs());
//        } catch (IOException e) {
//            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
//        }
    }
}
