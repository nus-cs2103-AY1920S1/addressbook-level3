package com.dukeacademy;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.Config;
import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.core.Version;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.util.ConfigUtil;
import com.dukeacademy.commons.util.FileUtil;
import com.dukeacademy.commons.util.StringUtil;
import com.dukeacademy.logic.commands.CommandLogic;
import com.dukeacademy.logic.commands.CommandLogicManager;
import com.dukeacademy.logic.commands.attempt.AttemptCommandFactory;
import com.dukeacademy.logic.commands.bookmark.BookmarkCommandFactory;
import com.dukeacademy.logic.commands.bookmark.DeleteBookmarkCommandFactory;
import com.dukeacademy.logic.commands.browse.BrowseCommandFactory;
import com.dukeacademy.logic.commands.exit.ExitCommandFactory;
import com.dukeacademy.logic.commands.find.FindCommandFactory;
import com.dukeacademy.logic.commands.help.HelpCommandFactory;
import com.dukeacademy.logic.commands.home.DashboardCommandFactory;
import com.dukeacademy.logic.commands.list.ListCommandFactory;
import com.dukeacademy.logic.commands.load.LoadCommandFactory;
import com.dukeacademy.logic.commands.notes.DeleteNoteCommandFactory;
import com.dukeacademy.logic.commands.notes.NewNoteCommandFactory;
import com.dukeacademy.logic.commands.notes.OpenNoteCommandFactory;
import com.dukeacademy.logic.commands.notes.SaveNoteCommandFactory;
import com.dukeacademy.logic.commands.submit.SubmitCommandFactory;
import com.dukeacademy.logic.commands.tab.TabCommandFactory;
import com.dukeacademy.logic.commands.view.ViewCommandFactory;
import com.dukeacademy.logic.notes.NotesLogic;
import com.dukeacademy.logic.notes.NotesLogicManager;
import com.dukeacademy.logic.program.ProgramSubmissionLogic;
import com.dukeacademy.logic.program.ProgramSubmissionLogicManager;
import com.dukeacademy.logic.program.exceptions.LogicCreationException;
import com.dukeacademy.logic.question.QuestionsLogic;
import com.dukeacademy.logic.question.QuestionsLogicManager;
import com.dukeacademy.model.state.ApplicationState;
import com.dukeacademy.storage.notes.JsonNoteBankStorage;
import com.dukeacademy.storage.notes.NoteBankStorage;
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

    /**
     * The constant VERSION.
     */
    private static final Version VERSION = new Version(1, 3, 1, true);
    private static Logger logger = LogsCenter.getLogger(MainApp.class);

    /**
     * The Ui.
     */
    private Ui ui;
    private QuestionsLogic questionsLogic;
    private ProgramSubmissionLogic programSubmissionLogic;
    private NotesLogic notesLogic;
    private ApplicationState applicationState;

    @Override
    public void init() throws Exception {
        super.init();

        // Retrieves config parameters
        AppParameters appParameters = AppParameters.parse(getParameters());
        Config config = initConfig(appParameters.getConfigPath());

        // Sets logging level as described
        initLogging(config);
        logger = LogsCenter.getLogger(MainApp.class);
        logger.info("=============================[ Initializing DukeAcademy ]===========================");

        initAppPaths(config);

        applicationState = this.initApplicationState();
        questionsLogic = this.initQuestionsLogic(config);
        programSubmissionLogic = this.initProgramSubmissionLogic(config);
        notesLogic = this.initNotesLogic(config);

        CommandLogicManager commandLogic = this.initCommandLogic();

        if (this.programSubmissionLogic == null) {
            logger.info("Fatal: Failed to create program submission logic.");
            this.stop();
            return;
        }

        ui = this.initUi(commandLogic, questionsLogic, programSubmissionLogic, applicationState);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     *
     * @param configFilePath the config file path
     * @return the config
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
     * Helper method to initialize all of the folders necessary for the app according to the path files specified
     * in the config instance.
     * @param config a config instance.
     */
    private void initAppPaths(Config config) {
        Path testOutputPath = config.getTestOutputPath();
        Path dataOutputPath = config.getDataPath();

        if (!testOutputPath.toFile().exists()) {
            logger.info("Test output folder not found at : " + testOutputPath);
            logger.info("Creating test output folder at : " + testOutputPath);

            if (!testOutputPath.toFile().mkdirs()) {
                logger.warning("Unable to create test executor output directory : " + testOutputPath);
            }
        }

        if (!dataOutputPath.toFile().exists()) {
            logger.info("Data folder not found at : " + dataOutputPath);
            logger.info("Creating data folder at : " + testOutputPath);

            createQuestionBankFile(dataOutputPath.resolve("QuestionBank.json"));
            createNoteBankFile(dataOutputPath.resolve("NoteBank.json"));
        }
    }

    /**
     * Helper method to create a question bank json file at the specified location. Default questions are copied.
     * @param questionBankFilePath the path at which to create the file.
     */
    private void createQuestionBankFile(Path questionBankFilePath) {
        try {
            logger.info("Creating new question bank.");

            // Copy default questions
            FileUtil.createIfMissing(questionBankFilePath);
            InputStream defaultQuestionsInputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("questionBank.json");
            if (defaultQuestionsInputStream != null) {
                logger.info("Copying default questions into the new question bank");
                Files.copy(defaultQuestionsInputStream, questionBankFilePath, StandardCopyOption.REPLACE_EXISTING);
                defaultQuestionsInputStream.close();
            } else {
                logger.warning("Fatal: default questions not found.");
                this.stop();
            }

        } catch (IOException | NullPointerException e) {
            logger.warning("Unable to create default question bank data file.");
        }
    }

    /**
     * Helper method to create a note bank json file at the specified location. Default note bank will be empty.
     * @param noteBankFilePath the path at which to create the file.
     */
    private void createNoteBankFile(Path noteBankFilePath) {
        try {
            logger.info("Creating new note bank.");
            FileUtil.createIfMissing(noteBankFilePath);
            InputStream emptyNoteBankInputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("noteBank.json");
            if (emptyNoteBankInputStream != null) {
                logger.info("Copying note bank template into new note bank");
                Files.copy(emptyNoteBankInputStream, noteBankFilePath, StandardCopyOption.REPLACE_EXISTING);
                emptyNoteBankInputStream.close();
            }
        } catch (IOException | NullPointerException e) {
            logger.warning("Unable to create new note bank data file.");
        }
    }

    /**
     * Returns a new CommandLogicManager based on the UserPrefs passed into the function. Any commands to be registered
     * in the CommandLogicManager is done in this method.
     *
     * @return a CommandLogicManger instance.
     * */
    private CommandLogicManager initCommandLogic() {
        logger.info("============================ [ Initializing command logic ] =============================");

        if (this.questionsLogic == null || this.programSubmissionLogic == null) {
            logger.warning("Command logic should be initialized after Question and Program Submission logic.");
            this.stop();
        }

        CommandLogicManager commandLogicManager = new CommandLogicManager();

        // Registering exit command
        ExitCommandFactory exitCommandFactory = new ExitCommandFactory(this.questionsLogic,
                this.programSubmissionLogic, this.notesLogic);
        commandLogicManager.registerCommand(exitCommandFactory);
        // Registering attempt command
        AttemptCommandFactory attemptCommandFactory = new AttemptCommandFactory(this.questionsLogic,
                this.programSubmissionLogic, applicationState);
        commandLogicManager.registerCommand(attemptCommandFactory);
        // Registering submit command
        SubmitCommandFactory submitCommandFactory = new SubmitCommandFactory(this.questionsLogic,
                this.programSubmissionLogic, applicationState);
        commandLogicManager.registerCommand(submitCommandFactory);
        // Registering view command
        ViewCommandFactory viewCommandFactory =
            new ViewCommandFactory(this.questionsLogic, applicationState);
        commandLogicManager.registerCommand(viewCommandFactory);
        // Registering home command
        DashboardCommandFactory dashboardCommandFactory =
            new DashboardCommandFactory(this.questionsLogic,
                this.programSubmissionLogic, applicationState);
        commandLogicManager.registerCommand(dashboardCommandFactory);
        // Registering list command
        ListCommandFactory listCommandFactory = new ListCommandFactory(this.questionsLogic, applicationState);
        commandLogicManager.registerCommand(listCommandFactory);
        // Registering bookmark command
        BookmarkCommandFactory bookmarkCommandFactory = new BookmarkCommandFactory(this.questionsLogic);
        commandLogicManager.registerCommand(bookmarkCommandFactory);
        // Registering Load command
        LoadCommandFactory loadCommandFactory =
            new LoadCommandFactory(this.questionsLogic);
        commandLogicManager.registerCommand(loadCommandFactory);
        // Registering Find command
        FindCommandFactory findCommandFactory =
            new FindCommandFactory(this.questionsLogic, this.applicationState);
        commandLogicManager.registerCommand(findCommandFactory);
        // Registering browse command
        BrowseCommandFactory browseCommandFactory =
            new BrowseCommandFactory(this.questionsLogic, this.applicationState);
        commandLogicManager.registerCommand(browseCommandFactory);
        // Registering tab command
        TabCommandFactory tabCommandFactory = new TabCommandFactory(this.applicationState);
        commandLogicManager.registerCommand(tabCommandFactory);
        // Registering delete bookmark command
        DeleteBookmarkCommandFactory deleteBookmarkCommandFactory =
                new DeleteBookmarkCommandFactory(this.questionsLogic);
        commandLogicManager.registerCommand(deleteBookmarkCommandFactory);
        // Registering help command
        HelpCommandFactory helpCommandFactory = new HelpCommandFactory(this.questionsLogic,
                this.programSubmissionLogic, this.applicationState);
        commandLogicManager.registerCommand(helpCommandFactory);
        // Registering new note command
        NewNoteCommandFactory newNoteCommandFactory = new NewNoteCommandFactory(this.notesLogic, this.applicationState
        );
        commandLogicManager.registerCommand(newNoteCommandFactory);
        // Registering open note command
        OpenNoteCommandFactory openNoteCommandFactory = new OpenNoteCommandFactory(this.notesLogic,
                this.applicationState);
        commandLogicManager.registerCommand(openNoteCommandFactory);
        // Registering save note command
        SaveNoteCommandFactory saveNoteCommandFactory = new SaveNoteCommandFactory(this.applicationState,
                this.notesLogic);
        commandLogicManager.registerCommand(saveNoteCommandFactory);
        // Registering delete note command
        DeleteNoteCommandFactory deleteNoteCommandFactory = new DeleteNoteCommandFactory(this.notesLogic,
                this.applicationState);
        commandLogicManager.registerCommand(deleteNoteCommandFactory);

        return commandLogicManager;
    }

    private ApplicationState initApplicationState() {
        logger.info("============================ [ Initializing application state ] ===="
                + "=========================");
        return new ApplicationState();
    }

    /**
     * Returns a new QuestionLogicManager based on the UserPrefs passed into the function.
     *
     * @param config a Config instance.
     * @return a QuestionsLogicManager instance.
     * */
    private QuestionsLogicManager initQuestionsLogic(Config config) {
        logger.info("============================ [ Initializing question logic ] =============================");
        QuestionBankStorage storage = new JsonQuestionBankStorage(config.getDataPath().resolve("QuestionBank.json"));

        return new QuestionsLogicManager(storage);
    }

    /**
     * Returns a new NotesLogicManager based on the given config preferences.
     * @param config a Config instance
     * @return a NotesLogicManager instance
     */
    private NotesLogicManager initNotesLogic(Config config) {
        logger.info("============================ [ Initializing notes logic ] =============================");
        NoteBankStorage storage = new JsonNoteBankStorage(config.getDataPath().resolve("NoteBank.json"));

        return new NotesLogicManager(storage);
    }

    /**
     * Returns a new ProgramSubmissionLogicManager based on the UserPrefs passed into the function.
     *
     * @return a ProgramSubmissionLogicManager instance.
     */
    private ProgramSubmissionLogic initProgramSubmissionLogic(Config config) {
        logger.info("============================ [ Initializing program submission logic ] "
                + "=============================");
        try {
            String outputPath = config.getTestOutputPath().toString();
            return new ProgramSubmissionLogicManager(outputPath);
        } catch (LogicCreationException e) {
            return null;
        }
    }

    private Ui initUi(CommandLogic commandLogic, QuestionsLogic questionsLogic,
                      ProgramSubmissionLogic programSubmissionLogic, ApplicationState applicationState) {
        logger.info("============================ [ Initializing UI ] =============================");
        return new UiManager(commandLogic, questionsLogic,
            programSubmissionLogic, notesLogic, applicationState);
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
