package seedu.exercise;

import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.model.util.DefaultPropertyBookUtil.getDefaultPropertyBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.exercise.commons.core.Config;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.core.State;
import seedu.exercise.commons.core.Version;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.util.ConfigUtil;
import seedu.exercise.commons.util.StringUtil;
import seedu.exercise.logic.Logic;
import seedu.exercise.logic.LogicManager;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.PropertyBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.model.util.DefaultPropertyBookUtil;
import seedu.exercise.model.util.SampleDataUtil;
import seedu.exercise.storage.JsonPropertyBookStorage;
import seedu.exercise.storage.JsonUserPrefsStorage;
import seedu.exercise.storage.PropertyBookStorage;
import seedu.exercise.storage.Storage;
import seedu.exercise.storage.StorageBook;
import seedu.exercise.storage.UserPrefsStorage;
import seedu.exercise.storage.bookstorage.JsonExerciseBookStorage;
import seedu.exercise.storage.bookstorage.JsonRegimeBookStorage;
import seedu.exercise.storage.bookstorage.JsonScheduleBookStorage;
import seedu.exercise.ui.Ui;
import seedu.exercise.ui.UiManager;

/**
 * Runs the application.
 *
 * Additionally, the MainApp wil keep track of the state of the program.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 2, 1, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static State state;

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ExerciseBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);

        JsonExerciseBookStorage exerciseBookStorage = new JsonExerciseBookStorage(userPrefs.getExerciseBookFilePath());

        JsonExerciseBookStorage exerciseDatabaseStorage =
            new JsonExerciseBookStorage(userPrefs.getAllExerciseBookFilePath());

        JsonRegimeBookStorage regimeBookStorage = new JsonRegimeBookStorage(userPrefs.getRegimeBookFilePath());

        JsonScheduleBookStorage scheduleBookStorage = new JsonScheduleBookStorage(userPrefs.getScheduleBookFilePath());

        PropertyBookStorage propertyBookStorage =
            new JsonPropertyBookStorage(userPrefs.getPropertyBookFilePath());

        storage = new StorageBook(exerciseBookStorage, exerciseDatabaseStorage, regimeBookStorage,
            scheduleBookStorage, userPrefsStorage, propertyBookStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s exercise book and {@code userPrefs}. <br>
     * The data from the sample exercise book will be used instead if {@code storage}'s exercise book is not found,
     * or an empty exercise book will be used instead if errors occur when reading {@code storage}'s exercise book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        ReadOnlyResourceBook<Exercise> initialData = readExerciseData(storage, storage.getExerciseBookFilePath());
        ReadOnlyResourceBook<Regime> initialRegimeData = readRegimeData(storage, storage.getRegimeBookFilePath());
        ReadOnlyResourceBook<Exercise> initialDatabase =
            readExerciseData(storage, storage.getExerciseDatabaseFilePath());
        ReadOnlyResourceBook<Schedule> initialScheduleData = readScheduleData(storage);
        PropertyBook initialPropertyBook = getInitialPropertyBook(storage);

        return new ModelManager(initialData, initialRegimeData,
            initialDatabase, initialScheduleData, userPrefs, initialPropertyBook);
    }

    /**
     * Returns a {@code ReadOnlyResourceBook<Regime>} using the file at {@code path}. <br>
     * The data is read from {@code storage}.
     */
    private ReadOnlyResourceBook<Regime> readRegimeData(Storage storage, Path path) {
        Optional<ReadOnlyResourceBook<Regime>> regimeBookOptional;
        ReadOnlyResourceBook<Regime> regimeData;

        try {
            regimeBookOptional = storage.readRegimeBook(path);
            if (regimeBookOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample RegimeBook");
            }
            regimeData = regimeBookOptional.orElseGet(SampleDataUtil::getSampleRegimeBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty RegimeBook");
            regimeData = new ReadOnlyResourceBook<>();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty RegimeBook");
            regimeData = new ReadOnlyResourceBook<>();

        }

        return regimeData;
    }

    /**
     * Returns a {@code ReadOnlyResourceBook<Exercise>} using the file at {@code path}. <br>
     * The data is read from {@code storage}.
     */
    private ReadOnlyResourceBook<Exercise> readExerciseData(Storage storage, Path path) {
        Optional<ReadOnlyResourceBook<Exercise>> exerciseBookOptional;
        ReadOnlyResourceBook<Exercise> exerciseData;

        try {
            exerciseBookOptional = storage.readExerciseBook(path);
            if (exerciseBookOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample ExerciseBook");
            }
            exerciseData = exerciseBookOptional.orElseGet(SampleDataUtil::getSampleExerciseBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in correct format. Will be starting with an empty ExerciseBook");
            exerciseData = new ReadOnlyResourceBook<>();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ExerciseBook");
            exerciseData = new ReadOnlyResourceBook<>();
        }

        return exerciseData;
    }

    /**
     * Returns a {@code PropertyBook} from {@code storage}.
     */
    private ReadOnlyResourceBook<Schedule> readScheduleData(Storage storage) {
        Optional<ReadOnlyResourceBook<Schedule>> scheduleBookOptional;
        ReadOnlyResourceBook<Schedule> initialScheduleData;

        try {
            scheduleBookOptional = storage.readScheduleBook();
            if (!scheduleBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ScheduleBook");
            }
            initialScheduleData = scheduleBookOptional.orElseGet(SampleDataUtil::getSampleScheduleBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ScheduleBook");
            initialScheduleData = new ReadOnlyResourceBook<>();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ScheduleBook");
            initialScheduleData = new ReadOnlyResourceBook<>();
        }

        return initialScheduleData;
    }

    private PropertyBook getInitialPropertyBook(Storage storage) {
        Optional<PropertyBook> propertyBookOptional;
        PropertyBook initialPropertyBook;

        try {
            propertyBookOptional = storage.readPropertyBook();
            if (propertyBookOptional.isEmpty()) {
                logger.info("Data for PropertyBook not found. Will be starting with a"
                    + " default PropertyBook");
            }
            initialPropertyBook =
                propertyBookOptional.orElseGet(DefaultPropertyBookUtil::getDefaultPropertyBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with "
                + " a default PropertyBook");
            initialPropertyBook = getDefaultPropertyBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with "
                + "a default PropertyBook");
            initialPropertyBook = getDefaultPropertyBook();
        }
        return initialPropertyBook;
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
            logger.warning("Problem while reading from the file. Will be starting with an empty ExerciseBook");
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
        logger.info("Starting ExerciseBook " + MainApp.VERSION);
        state = State.NORMAL;
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Exercise Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }

    public static State getState() {
        return state;
    }

    /**
     * Sets the current state of the program.
     *
     * Only subclasses of {@code Command} can and should call this method.
     */
    public static void setState(State newState) {
        requireAllNonNull(newState);
        state = newState;
    }
}
