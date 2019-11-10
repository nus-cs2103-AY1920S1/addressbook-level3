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
import seedu.address.commons.exceptions.CheatSheetDataConversionException;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.FlashcardDataConversionException;
import seedu.address.commons.exceptions.NoteDataConversionException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyStudyBuddyProCheatSheets;
import seedu.address.model.ReadOnlyStudyBuddyProFlashcards;
import seedu.address.model.ReadOnlyStudyBuddyProNotes;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.JsonStudyBuddyProStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.StudyBuddyProStorage;
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
        logger.info("=============================[ Initializing StudyBuddyPro ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        StudyBuddyProStorage studyBuddyProStorage = new JsonStudyBuddyProStorage(userPrefs.getFlashcardFilePath(),
                userPrefs.getNoteFilePath(), userPrefs.getCheatSheetFilePath());

        storage = new StorageManager(studyBuddyProStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample StudyBuddyPro will be used instead if {@code storage}'s StudyBuddyPro is not found,
     * or if errors occur when reading {@code storage}'s StudyBuddyPro. TO CHANGE
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {

        StudyBuddyPro initialData = new StudyBuddyPro();
        initialData = initModelManagerFlashcardHelper(storage, initialData);
        initialData = initModelManagerNoteHelper(storage, initialData);
        initialData = initModelManagerCheatSheetHelper(storage, initialData);

        return new ModelManager(initialData, userPrefs);
    }

    /**
     *
     * @param storage
     * @param initialData
     * @return
     */
    private StudyBuddyPro initModelManagerFlashcardHelper(Storage storage, StudyBuddyPro initialData) {
        Optional<ReadOnlyStudyBuddyProFlashcards> studyBuddyProFlashcardsOptional;
        try {
            studyBuddyProFlashcardsOptional = storage.readStudyBuddyProFlashcards();
            if (studyBuddyProFlashcardsOptional.isEmpty()) {
                logger.info("Flashcards data file not found. Will be starting with sample flashcards");
                initialData.setFlashcards(Arrays.asList(SampleDataUtil.getSampleFlashcards()));
            } else {
                initialData.setFlashcards(studyBuddyProFlashcardsOptional.get().getFlashcardList());
                initialData.addAllTags(studyBuddyProFlashcardsOptional.get().getTagList());
            }
        } catch (FlashcardDataConversionException e) {
            logger.warning("Flashcards data file not in the correct format. Will be starting with sample "
                    + "flashcards and continue checking for notes and cheatsheet data files");
            initialData.setFlashcards(Arrays.asList(SampleDataUtil.getSampleFlashcards()));
        } catch (IOException e) {
            logger.warning("Problem while reading from flashcard data file. Will be starting with sample "
                    + "flashcards and continue checking for notes and cheatsheet data files");
            initialData.setFlashcards(Arrays.asList(SampleDataUtil.getSampleFlashcards()));
        } finally {
            return initialData;
        }
    }

    /**
     *
     * @param storage
     * @param initialData
     * @return
     */
    private StudyBuddyPro initModelManagerNoteHelper(Storage storage, StudyBuddyPro initialData) {
        Optional<ReadOnlyStudyBuddyProNotes> studyBuddyProNotesOptional;
        try {
            studyBuddyProNotesOptional = storage.readStudyBuddyProNotes();
            if (studyBuddyProNotesOptional.isEmpty()) {
                logger.info("Notes data file not found. Will be starting with sample notes");
                initialData.setNotes(Arrays.asList(SampleDataUtil.getSampleNotes()));
            } else {
                initialData.setNotes(studyBuddyProNotesOptional.get().getNoteList());
                initialData.addAllTags(studyBuddyProNotesOptional.get().getTagList());
            }
        } catch (NoteDataConversionException e) {
            logger.warning("Notes data file not in the correct format. Will be starting with sample "
                    + "notes and continue checking for cheatsheet data file");
            initialData.setNotes(Arrays.asList(SampleDataUtil.getSampleNotes()));
        } catch (IOException e) {
            logger.warning("Problem while reading from notes data file. Will be starting with sample "
                    + "notes and continue checking for cheatsheet data file");
            initialData.setNotes(Arrays.asList(SampleDataUtil.getSampleNotes()));
        } finally {
            return initialData;
        }
    }

    /**
     *
     * @param storage
     * @param initialData
     * @return
     */
    private StudyBuddyPro initModelManagerCheatSheetHelper(Storage storage, StudyBuddyPro initialData) {
        Optional<ReadOnlyStudyBuddyProCheatSheets> studyBuddyProCheatSheetsOptional;
        try {
            studyBuddyProCheatSheetsOptional = storage.readStudyBuddyProCheatSheets();
            if (studyBuddyProCheatSheetsOptional.isEmpty()) {
                logger.info("Cheatsheets data file not found. Will be starting with empty cheatsheets");
            } else {
                initialData.setCheatSheets(studyBuddyProCheatSheetsOptional.get().getCheatSheetList());
                initialData.addAllTags(studyBuddyProCheatSheetsOptional.get().getTagList());
            }
        } catch (CheatSheetDataConversionException e) {
            logger.warning("Cheatsheets data file not in the correct format. Will be starting with empty "
                    + "cheatsheets");
        } catch (IOException e) {
            logger.warning("Problem while reading from cheatsheet data file. Will be starting with empty "
                    + "cheatsheets");
        } finally {
            return initialData;
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
            logger.warning("Problem while reading from the file. Will be starting with an empty StudyBuddyPro");
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
        logger.info("Starting StudyBuddyPro " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping StudyBuddyPro ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
