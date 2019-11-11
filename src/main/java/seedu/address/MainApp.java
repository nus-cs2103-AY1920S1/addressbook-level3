package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.note.NotesRecord;
import seedu.address.model.note.ReadOnlyNotesRecord;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.quiz.SavedQuizzes;
import seedu.address.model.statistics.ReadOnlyStatisticsRecord;
import seedu.address.model.statistics.StatisticsRecord;
import seedu.address.model.student.ReadOnlyStudentRecord;
import seedu.address.model.student.StudentRecord;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleNotesUtil;
import seedu.address.model.util.SampleStatisticUtil;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.event.EventStorage;
import seedu.address.storage.event.JsonEventStorage;
import seedu.address.storage.note.JsonNotesRecordStorage;
import seedu.address.storage.note.NotesRecordStorage;
import seedu.address.storage.question.JsonQuestionStorage;
import seedu.address.storage.question.QuestionStorage;
import seedu.address.storage.quiz.JsonQuizStorage;
import seedu.address.storage.quiz.QuizStorage;
import seedu.address.storage.student.JsonStudentRecordStorage;
import seedu.address.storage.student.StudentRecordStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 0, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info(
            "=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        StudentRecordStorage studentRecordStorage =
            new JsonStudentRecordStorage(userPrefs.getStudentRecordFilePath());
        QuestionStorage savedQuestionStorage =
            new JsonQuestionStorage(userPrefs.getSavedQuestionsFilePath());
        EventStorage eventStorage =
            new JsonEventStorage(userPrefs.getEventRecordFilePath());
        QuizStorage savedQuizStorage =
                new JsonQuizStorage(userPrefs.getSavedQuizzesFilePath());
        NotesRecordStorage notesRecordStorage = new JsonNotesRecordStorage(userPrefs.getNotesRecordFilePath());
        storage = new StorageManager(userPrefsStorage, studentRecordStorage,
            savedQuestionStorage, savedQuizStorage, notesRecordStorage, eventStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code
     * userPrefs}. <br> The data from the sample address book will be used instead if {@code
     * storage}'s address book is not found, or an empty address book will be used instead if errors
     * occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyStudentRecord> studentRecordOptional;
        Optional<ReadOnlyQuestions> questionsOptional;
        Optional<ReadOnlyEvents> eventsOptional;
        Optional<ReadOnlyQuizzes> quizzesOptional;
        Optional<ReadOnlyNotesRecord> notesRecordOptional;

        ReadOnlyStudentRecord initialStudentRecord;
        ReadOnlyQuestions initialQuestions;
        ReadOnlyEvents initialEvents;
        ReadOnlyQuizzes initialQuizzes;
        ReadOnlyNotesRecord initialNotesRecord;
        ReadOnlyStatisticsRecord initialStatisticsRecord;

        try {
            studentRecordOptional = storage.readStudentRecord();
            questionsOptional = storage.readQuestions();
            eventsOptional = storage.readEvents();
            quizzesOptional = storage.readQuizzes();
            notesRecordOptional = storage.readNotesRecord();

            if (!studentRecordOptional.isPresent()) {
                logger.info("Student file not found. Will be starting with a student record with a sample student.");
            }
            if (!questionsOptional.isPresent()) {
                logger.info("Question file not found. Will create an empty one.");
            }
            if (!eventsOptional.isPresent()) {
                logger.info("Events file not found. Will create an empty one.");
            }
            if (!quizzesOptional.isPresent()) {
                logger.info("Quiz file not found. Will create an empty one.");
            }
            if (!notesRecordOptional.isPresent()) {
                logger.info("Notes Record not found. Will start with sample NotesRecord");
            }
            initialStudentRecord = studentRecordOptional.orElseGet(SampleDataUtil::getSampleStudents);
            initialQuestions = questionsOptional.orElseGet(SampleDataUtil::getSampleQuestionList);
            initialEvents = eventsOptional.orElseGet(SampleDataUtil::getSampleEventsList);
            initialNotesRecord = notesRecordOptional.orElseGet(SampleNotesUtil::getSampleNotesRecord);
            initialStatisticsRecord = SampleStatisticUtil.getSampleStatisticsRecord();
            initialQuizzes = quizzesOptional.orElseGet(SampleDataUtil::getSampleQuizList);


        } catch (DataConversionException e) {
            logger.warning(
                "Data file not in the correct format. Will be starting with an empty NJoy assistant");
            initialStudentRecord = new StudentRecord();
            initialQuestions = new SavedQuestions();
            initialEvents = new EventRecord();
            initialQuizzes = new SavedQuizzes();
            initialNotesRecord = new NotesRecord();
            initialStatisticsRecord = new StatisticsRecord();

        } catch (IOException e) {
            logger.warning(
                "Problem while reading from the file. Will be starting with an empty  NJoy assistant");
            initialStudentRecord = new StudentRecord();
            initialQuestions = new SavedQuestions();
            initialEvents = new EventRecord();
            initialQuizzes = new SavedQuizzes();
            initialNotesRecord = new NotesRecord();
            initialStatisticsRecord = new StatisticsRecord();
        }

        return new ModelManager(initialStudentRecord, initialQuestions, initialQuizzes,
                initialNotesRecord, initialEvents, initialStatisticsRecord, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br> The default file path
     * {@code Config#DEFAULT_CONFIG_FILE} will be used instead if {@code configFilePath} is null.
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
            logger
                .warning("Config file at " + configFilePathUsed + " is not in the correct format. "
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
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path, or a
     * new {@code UserPrefs} with default configuration if errors occur when reading from the file.
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
            logger.warning(
                "Problem while reading from the file. Will be starting with an empty AddressBook");
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
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info(
            "============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
