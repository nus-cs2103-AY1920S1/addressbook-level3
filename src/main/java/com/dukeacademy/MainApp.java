package com.dukeacademy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.Config;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.core.Mode;
import com.dukeacademy.commons.core.Version;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.util.ConfigUtil;
import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.commands.CommandLogicManager;
import com.dukeacademy.logic.commands.attempt.AttemptCommandFactory;
import com.dukeacademy.logic.commands.exit.ExitCommandFactory;
import com.dukeacademy.logic.commands.submit.SubmitCommandFactory;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.logic.question.QuestionsLogicManager;
import com.dukeacademy.model.prefs.ReadOnlyUserPrefs;
import com.dukeacademy.model.prefs.UserPrefs;
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
    private QuestionsLogic questionsLogic;
    private ProgramSubmissionLogicManager programSubmissionLogic;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing DukeAcademy ]===========================");
        super.init();

        // Retrieves config parameters
        AppParameters appParameters = AppParameters.parse(getParameters());
        Config config = initConfig(appParameters.getConfigPath());

        // Sets logging level as described
        initLogging(config);

        // Configures user preferences
        UserPrefs userPrefs = initPrefs(config);

        if (userPrefs == null) {
            logger.warning("Fatal: Unrecognized mode in config file. Unable to initialize preferences.");
            this.stop();
            return;
        }

        questionsLogic = this.initQuestionsLogic(userPrefs);
        programSubmissionLogic = this.initProgramSubmissionLogic(userPrefs);
        CommandLogicManager commandLogic = this.initCommandLogic();

        if (this.programSubmissionLogic == null) {
            logger.info("Fatal: Failed to create program submission logic.");
            this.stop();
            return;
        }

        ui = this.initUi(commandLogic, questionsLogic, programSubmissionLogic);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    private Config initConfig(Path configFilePath) {
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

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    private UserPrefs initPrefs(Config config) {
        logger.info("=============================[ Configuring preferences ]===========================");

        if (config.getMode() == Mode.PRODUCTION) {
            logger.info("Production mode detected, using development prefs.");
            return getUserPrefsInProduction();
        } else if (config.getMode() == Mode.DEVELOPMENT) {
            logger.info("Development mode detected, using custom prefs.");
            return getUserPrefsInDevelopment(config);
        } else {
            return null;
        }
    }

    private UserPrefs getUserPrefsInDevelopment(Config config) {
        Path prefsFilePath = config.getUserPrefsFilePath();
        UserPrefsStorage storage = new JsonUserPrefsStorage(prefsFilePath);

        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;

        try {
            initializedPrefs = storage.readUserPrefs().orElse(new UserPrefs(Paths.get("development")));
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs(Paths.get("development"));
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Using default user prefs.");
            initializedPrefs = new UserPrefs(Paths.get("development"));
        }

        return initializedPrefs;
    }

    private UserPrefs getUserPrefsInProduction() {
        Path appRootDirectory = Paths.get(System.getProperty("user.home")).resolve("DukeAcademy");
        Path testExecutorOutputPath = appRootDirectory.resolve("tests");
        Path questionBankFilePath = appRootDirectory.resolve("questionBank.json");

        // If app is opened for the first time...
        if (!appRootDirectory.toFile().exists()) {
            logger.info("Opening app for the first time. Creating new app directory at : " + appRootDirectory);

            if (!appRootDirectory.toFile().mkdirs()) {
                logger.warning("Unable to create create app directory.");
                return null;
            }

            if (!testExecutorOutputPath.toFile().mkdir()) {
                logger.warning("Unable to create create test executor output directory.");
                return null;
            }

            createQuestionBankFile(questionBankFilePath);
            return new UserPrefs(appRootDirectory);
        }

        // If test output directory not found
        if (!testExecutorOutputPath.toFile().exists()) {
            logger.info("Tests output folder not found. Creating folder at : " + testExecutorOutputPath);
            if (!testExecutorOutputPath.toFile().mkdir()) {
                logger.warning("Unable to create create test executor output directory.");
                return null;
            }
        }

        // If questions data not found
        if (!appRootDirectory.resolve("questionBank.json").toFile().exists()) {
            logger.info("Question storage file not found. Loading questions to : " + questionBankFilePath);

            createQuestionBankFile(appRootDirectory);
        }

        return new UserPrefs(appRootDirectory);
    }

    /**
     * Helper method to create a question bank json file at the specified location. Default questions are copied.
     * @param questionBankFilePath the path at which to create the file.
     */
    private void createQuestionBankFile(Path questionBankFilePath) {
        try {
            logger.info("Creating new question bank.");
            // Copy default questions
            Path defaultQuestions = Paths.get("questionBank.json");
            Files.copy(defaultQuestions, questionBankFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.warning("Unable to create default question bank data file.");
        }
    }

    /**
     * Returns a new CommandLogicManager based on the UserPrefs passed into the function. Any commands to be registered
     * in the CommandLogicManager is done in this method.
     *
     * @return a CommandLogicManger instance.
     */
    private CommandLogicManager initCommandLogic() {
        logger.info("============================ [ Initializing command logic ] =============================");

        if (this.questionsLogic == null || this.programSubmissionLogic == null) {
            logger.warning("Command logic should be initialized after Question and Program Submission logic.");
            this.stop();
        }

        CommandLogicManager commandLogicManager = new CommandLogicManager();
        // Registering exit command
        ExitCommandFactory exitCommandFactory = new ExitCommandFactory(this.questionsLogic,
                this.programSubmissionLogic);
        commandLogicManager.registerCommand(exitCommandFactory);
        // Registering attempt command
        AttemptCommandFactory attemptCommandFactory = new AttemptCommandFactory(this.questionsLogic,
                this.programSubmissionLogic);
        commandLogicManager.registerCommand(attemptCommandFactory);
        // Registering submit command
        SubmitCommandFactory submitCommandFactory = new SubmitCommandFactory(this.questionsLogic,
                this.programSubmissionLogic);
        commandLogicManager.registerCommand(submitCommandFactory);

        return commandLogicManager;
    }

    /**
     * Returns a new QuestionLogicManager based on the UserPrefs passed into the function.
     *
     * @param userPrefs a UserPrefs instance.
     * @return a QuestionsLogicManager instance.
     */
    private QuestionsLogicManager initQuestionsLogic(ReadOnlyUserPrefs userPrefs) {
        logger.info("============================ [ Initializing question logic ] =============================");
        QuestionBankStorage storage = new JsonQuestionBankStorage(userPrefs.getQuestionBankFilePath());
        return new QuestionsLogicManager(storage);
    }

    /**
     * Returns a new ProgramSubmissionLogicManager based on the UserPrefs passed into the function.
     *
     * @return a ProgramSubmissionLogicManager instance.
     */
    private ProgramSubmissionLogicManager initProgramSubmissionLogic(ReadOnlyUserPrefs userPrefs) {
        logger.info("============================ [ Initializing program submission logic ] "
                + "=============================");
        try {
            String outputPath = userPrefs.getTestExecutorOutputPath().toString();
            return new ProgramSubmissionLogicManager(outputPath);
        } catch (LogicCreationException e) {
            return null;
        }
    }

    private Ui initUi(CommandLogic commandLogic, QuestionsLogic questionsLogic,
                      ProgramSubmissionLogic programSubmissionLogic) {
        logger.info("============================ [ Initializing UI ] =============================");
        return new UiManager(commandLogic, questionsLogic, programSubmissionLogic);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("============================ [ Starting application ] =============================");
        logger.info("Starting QuestionBank " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping DukeAcademy ] =============================");

        // Deletes the temporary folder created for test execution purposes
        if (this.programSubmissionLogic != null) {
            this.programSubmissionLogic.closeProgramSubmissionLogicManager();
        }
    }
}
