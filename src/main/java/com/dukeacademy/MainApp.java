package com.dukeacademy;

import java.io.File;
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
import com.dukeacademy.logic.commands.CommandLogicManager;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.logic.question.QuestionsLogicManager;
import com.dukeacademy.model.prefs.ReadOnlyUserPrefs;
import com.dukeacademy.model.prefs.UserPrefs;
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
    protected CommandLogicManager commandLogic;
    protected QuestionsLogicManager questionsLogic;
    protected ProgramSubmissionLogicManager programSubmissionLogic;

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

        commandLogic = this.initCommandLogic();
        questionsLogic = this.initQuestionsLogic(userPrefs);
        programSubmissionLogic = this.initProgramSubmissionLogic();
        ui = this.initUi(commandLogic, questionsLogic, programSubmissionLogic, userPrefs);
    }

    /**
     * Returns a new QuestionLogicManager based on the UserPrefs passed into the function.
     * @param userPrefs a UserPrefs instance.
     * @return a QuestionsLogicManager instance.
     */
    private QuestionsLogicManager initQuestionsLogic(ReadOnlyUserPrefs userPrefs) {
        QuestionBankStorage storage = new JsonQuestionBankStorage(userPrefs.getQuestionBankFilePath());
        return new QuestionsLogicManager(storage);
    }

    /**
     * Returns a new ProgramSubmissionLogicManager based on the UserPrefs passed into the function.
     * @return a ProgramSubmissionLogicManager instance.
     */
    private ProgramSubmissionLogicManager initProgramSubmissionLogic() {
        // TODO: eventually store program execution path in user prefs
        String outputPath = System.getProperty("user.home") + File.separator + "DukeAcademy";
        File file = new File(outputPath);
        if (!file.exists()) {
            file.mkdir();
        }

        try {
            return new ProgramSubmissionLogicManager(outputPath);
        } catch (LogicCreationException e) {
            logger.info("Fatal: failed to create program submission logic. Exiting app.");
            this.stop();
            return null;
        }
    }

    /**
     * Returns a new CommandLogicManager based on the UserPrefs passed into the function. Any commands to be registered
     * in the CommandLogicManager is done in this method.
     * @return a CommandLogicManger instance.
     */
    private CommandLogicManager initCommandLogic() {
        CommandLogicManager commandLogicManager = new CommandLogicManager();
        // TODO: create and register commands
        return commandLogicManager;
    }

    private Ui initUi(CommandLogic commandLogic, QuestionsLogic questionsLogic,
                      ProgramSubmissionLogic programSubmissionLogic, ReadOnlyUserPrefs userPrefs) {
        return new UiManager(commandLogic, questionsLogic, programSubmissionLogic, userPrefs.getGuiSettings());
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
        this.programSubmissionLogic.closeProgramSubmissionLogicManager();
    }
}
