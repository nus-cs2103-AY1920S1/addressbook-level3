package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
import seedu.address.model.AddressBook;
import seedu.address.model.CardBook;
import seedu.address.model.FileBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PasswordBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCardBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedNoteBook;
import seedu.address.model.util.SampleDataCardUtil;
import seedu.address.model.util.SampleDataNotesUtil;
import seedu.address.model.util.SampleDataPasswordUtil;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.CardBookStorage;
import seedu.address.storage.FileBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonCardBookStorage;
import seedu.address.storage.JsonFileBookStorage;
import seedu.address.storage.JsonNoteBookStorage;
import seedu.address.storage.JsonPasswordBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.NoteBookStorage;
import seedu.address.storage.PasswordBookStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.TestStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.ui.DialogManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, false);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        super.init();
    }

    /**
     * Initialises SecureIT app with the given password.
     *
     * @param password the master password used to encrypt data.
     */
    private void initWithPassword(String password) {
        logger.info("=============================[ Initializing SecureIT ]===========================");
        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath(), password);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath(), password);
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(userPrefs.getAddressBookFilePath(), password);
        FileBookStorage fileBookStorage =
                new JsonFileBookStorage(userPrefs.getFileBookFilePath(), password);
        CardBookStorage cardBookStorage =
                new JsonCardBookStorage(userPrefs.getCardBookFilePath(), password);
        NoteBookStorage noteBookStorage =
                new JsonNoteBookStorage(userPrefs.getNoteBookFilePath(), password);
        PasswordBookStorage passwordBookStorage =
                new JsonPasswordBookStorage(userPrefs.getPasswordBookFilePath(), password);
        storage = new StorageManager(addressBookStorage, fileBookStorage, cardBookStorage, noteBookStorage,
                passwordBookStorage, userPrefsStorage, password);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {

        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialAddressData;

        try {
            addressBookOptional = storage.readAddressBook();

            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialAddressData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty file");
            initialAddressData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty file");
            initialAddressData = new AddressBook();
        }
        ReadOnlyFileBook initialDataFile = initFileBook(storage);
        ReadOnlyCardBook initialCardData = initCardBook(storage);
        ReadOnlyNoteBook initialNoteData = initNoteBook(storage);
        ReadOnlyPasswordBook initialDataPassword = initPasswordBook(storage);
        return new ModelManager(initialAddressData, initialDataFile, initialCardData, initialNoteData,
                initialDataPassword, userPrefs);
    }

    /**
     * Returns data from {@code storage}'s file book.
     */
    private ReadOnlyFileBook initFileBook(Storage storage) {
        Optional<ReadOnlyFileBook> fileBookOptional;
        ReadOnlyFileBook initialFileData;
        try {
            fileBookOptional = storage.readFileBook();
            if (!fileBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty FileBook");
            }
            initialFileData = fileBookOptional.orElseGet(FileBook::new);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty FileBook");
            initialFileData = new FileBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty FileBook");
            initialFileData = new FileBook();
        }
        return initialFileData;
    }

    /**
     * Initializes a ReadOnlyNoteBook.
     * @param storage storage object used for application.
     * @return ReadOnlyNoteBook from storage object if present, else a new ReadOnlyNoteBook.
     */
    private ReadOnlyNoteBook initNoteBook(Storage storage) {
        Optional<ReadOnlyNoteBook> noteBookOptional;
        ReadOnlyNoteBook initialNoteData;
        try {
            noteBookOptional = storage.readNoteBook();
            if (!noteBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialNoteData = noteBookOptional.orElseGet(SampleDataNotesUtil::getSampleNoteBook);
        } catch (DataConversionException e) {
            logger.warning(e + "");
            logger.warning("Data file not in the correct format. Will be starting with an empty file");
            initialNoteData = new VersionedNoteBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty file");
            initialNoteData = new VersionedNoteBook();
        }
        return initialNoteData;
    }

    /**
     * Returns a {@code Card} with the data from {@code storage}'s card book. <br>
     * The data from the sample card book will be used instead if {@code storage}'s card book is not found,
     * or an empty card book will be used instead if errors occur when reading {@code storage}'s card book.
     */
    private ReadOnlyCardBook initCardBook(Storage storage) {
        Optional<ReadOnlyCardBook> cardBookOptional;
        ReadOnlyCardBook initialCardData;
        try {
            cardBookOptional = storage.readCardBook();
            if (!cardBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample CardBook");
            }
            initialCardData = cardBookOptional.orElseGet(SampleDataCardUtil::getSampleCardBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty CardBook");
            initialCardData = new CardBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty CardBook");
            initialCardData = new CardBook();
        }
        return initialCardData;
    }

    /**
     * Returns a {@code Password} with the data from {@code storage}'s password book. <br>
     * The data from the sample password book will be used instead if {@code storage}'s password book is not found,
     * or an empty password book will be used instead if errors occur when reading {@code storage}'s password book.
     */
    private ReadOnlyPasswordBook initPasswordBook(Storage storage) {
        Optional<ReadOnlyPasswordBook> passwordBookOptional;
        ReadOnlyPasswordBook initialDataPassword = SampleDataPasswordUtil.getSamplePasswordBook();
        try {
            passwordBookOptional = storage.readPasswordBook();
            if (!passwordBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample PasswordBook");
            }
            initialDataPassword = passwordBookOptional.orElseGet(SampleDataPasswordUtil::getSamplePasswordBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty PasswordBook");
            initialDataPassword = new PasswordBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty PasswordBook");
            initialDataPassword = new PasswordBook();
        }
        return initialDataPassword;
    }

    /**
     * Starts the log.
     */
    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath, String password) {
        Config initializedConfig;
        Path configFilePathUsed;


        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readEncryptedConfig(configFilePathUsed, password);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed, password);
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
        if (!TestStorage.isUserExist()) {
            showDialog(
                DialogManager::showCreatePasswordDialog,
                result -> !"".equals(result),
                result -> {
                    try {
                        TestStorage.initPassword(result);
                        initWithPassword(result);
                        startSecureIt(primaryStage);
                    } catch (IOException e) {
                        //TODO: if init password fails
                    }
                }
            );
        } else {
            showDialog(
                DialogManager::showValidatePasswordDialog,
                password -> {
                    try {
                        return TestStorage.testPassword(password);
                    } catch (IOException e) {
                        //TODO: if test password fails
                        return false;
                    }
                },
                result -> {
                    initWithPassword(result);
                    startSecureIt(primaryStage);
                }
            );
        }
    }

    /**
     * Display a dialog specified by the method supplied. After the dialog is dismissed, it
     * validates the result based on the validation given, and redisplay the dialog if the
     * validation fails. Otherwise, the callback is executed based on the result.
     * @param method specifies which dialog to be displayed.
     * @param validation validates the result from the dialog.
     * @param callback executes after validation succeeded.
     */
    private void showDialog(Function<Boolean, Optional<String>> method,
                           Predicate<String> validation,
                           Consumer<String> callback) {
        boolean validationFailed = false;
        while (true) {
            Optional<String> result = method.apply(validationFailed);
            if (result.isEmpty()) {
                break;
            }
            if (validation.test(result.get())) {
                callback.accept(result.get());
                break;
            }
            validationFailed = true;
        }
    }

    /**
     * Starts the main app.
     * @param primaryStage the primary stage of ui
     */
    private void startSecureIt(Stage primaryStage) {
        logger.info("Starting SecureIT " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        if (storage != null) {
            try {
                logger.info("============================ [ Stopping Address Book ] =============================");
                storage.saveUserPrefs(model.getUserPrefs());
            } catch (IOException e) {
                logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
            }
        }
    }
}
