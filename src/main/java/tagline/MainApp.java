package tagline;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

import tagline.commons.core.Config;
import tagline.commons.core.LogsCenter;
import tagline.commons.core.Version;
import tagline.commons.exceptions.DataConversionException;
import tagline.commons.util.ConfigUtil;
import tagline.commons.util.StringUtil;
import tagline.logic.Logic;
import tagline.logic.LogicManager;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.ReadOnlyUserPrefs;
import tagline.model.UserPrefs;
import tagline.model.contact.AddressBook;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.GroupBook;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.NoteBook;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.TagBook;
import tagline.model.util.SampleDataUtil;
import tagline.storage.JsonUserPrefsStorage;
import tagline.storage.Storage;
import tagline.storage.StorageManager;
import tagline.storage.UserPrefsStorage;
import tagline.storage.contact.AddressBookStorage;
import tagline.storage.contact.JsonAddressBookStorage;
import tagline.storage.group.GroupBookStorage;
import tagline.storage.group.JsonGroupBookStorage;
import tagline.storage.note.JsonNoteBookStorage;
import tagline.storage.note.NoteBookStorage;
import tagline.storage.tag.JsonTagBookStorage;
import tagline.storage.tag.TagBookStorage;
import tagline.ui.Ui;
import tagline.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 2, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing TagLine ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(userPrefs.getAddressBookFilePath());
        NoteBookStorage noteBookStorage = new JsonNoteBookStorage(userPrefs.getNoteBookFilePath());
        GroupBookStorage groupBookStorage = new JsonGroupBookStorage(userPrefs.getGroupBookFilePath());
        TagBookStorage tagBookStorage = new JsonTagBookStorage((userPrefs.getTagBookFilePath()));

        storage = new StorageManager(addressBookStorage, noteBookStorage, groupBookStorage, tagBookStorage,
            userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Gets and returns a {@code ReadOnlyAddressBook} from {@code storage}.
     */
    private ReadOnlyAddressBook getAddressBookFromStorage(Storage storage) {
        Optional<ReadOnlyAddressBook> addressBookOptional = Optional.empty();
        ReadOnlyAddressBook initialAddressBookData;

        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample address book");
            }
            initialAddressBookData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty address book");
            initialAddressBookData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty address book");
            initialAddressBookData = new AddressBook();
        }

        return initialAddressBookData;
    }

    /**
     * Gets and returns a {@code ReadOnlyNoteBook} from {@code storage}.
     */
    private ReadOnlyNoteBook getNoteBookFromStorage(Storage storage) {
        Optional<ReadOnlyNoteBook> noteBookOptional = Optional.empty();
        ReadOnlyNoteBook initialNoteBookData;

        try {
            noteBookOptional = storage.readNoteBook();
            if (!noteBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample note book");
            }
            initialNoteBookData = noteBookOptional.orElseGet(SampleDataUtil::getSampleNoteBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty note book");
            initialNoteBookData = new NoteBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty note book");
            initialNoteBookData = new NoteBook();
        }

        return initialNoteBookData;
    }

    /**
     * Gets and returns a {@code ReadOnlyGroupBook} from {@code storage}.
     */
    private ReadOnlyGroupBook getGroupBookFromStorage(Storage storage) {
        Optional<ReadOnlyGroupBook> groupBookOptional = Optional.empty();
        ReadOnlyGroupBook initialGroupBookData;

        try {
            groupBookOptional = storage.readGroupBook();
            if (!groupBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample group book");
            }
            initialGroupBookData = groupBookOptional.orElseGet(SampleDataUtil::getSampleGroupBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty group book");
            initialGroupBookData = new GroupBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty group book");
            initialGroupBookData = new GroupBook();
        }

        return initialGroupBookData;
    }

    /**
     * Gets and returns a {@code ReadOnlyTagBook} from {@code storage}.
     */
    private ReadOnlyTagBook getTagBookFromStorage(Storage storage) {
        Optional<ReadOnlyTagBook> tagBookOptional = Optional.empty();
        ReadOnlyTagBook initialTagBookData;

        try {
            tagBookOptional = storage.readTagBook();
            if (!tagBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample group book");
            }
            initialTagBookData = tagBookOptional.orElseGet(SampleDataUtil::getSampleTagBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty group book");
            initialTagBookData = new TagBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty group book");
            initialTagBookData = new TagBook();
        }

        return initialTagBookData;
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book, note book and
     * {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     * This is same for the note book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyAddressBook initialAddressBookData = getAddressBookFromStorage(storage);
        ReadOnlyNoteBook initialNoteBookData = getNoteBookFromStorage(storage);
        ReadOnlyGroupBook initialGroupBookData = getGroupBookFromStorage(storage);
        ReadOnlyTagBook initialTagBookData = getTagBookFromStorage(storage);
        return new ModelManager(initialAddressBookData, initialNoteBookData,
            initialGroupBookData, initialTagBookData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
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
        logger.info("Starting TagLine " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping TagLine ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
