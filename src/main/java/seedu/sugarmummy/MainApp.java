package seedu.sugarmummy;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.sugarmummy.commons.core.Config;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.commons.core.Version;
import seedu.sugarmummy.commons.exceptions.DataConversionException;
import seedu.sugarmummy.commons.util.ConfigUtil;
import seedu.sugarmummy.commons.util.StringUtil;
import seedu.sugarmummy.logic.Logic;
import seedu.sugarmummy.logic.LogicManager;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelManager;
import seedu.sugarmummy.model.ReadOnlyData;
import seedu.sugarmummy.model.ReadOnlyUserPrefs;
import seedu.sugarmummy.model.UserPrefs;
import seedu.sugarmummy.model.biography.ReadOnlyUserList;
import seedu.sugarmummy.model.biography.UserList;
import seedu.sugarmummy.model.calendar.Calendar;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;
import seedu.sugarmummy.model.recmf.UniqueFoodList;
import seedu.sugarmummy.model.records.UniqueRecordList;
import seedu.sugarmummy.model.util.SampleCalendarDataUtil;
import seedu.sugarmummy.model.util.SampleFoodDataUtil;
import seedu.sugarmummy.model.util.SampleRecordDataUtil;
import seedu.sugarmummy.model.util.SampleUserDataUtil;
import seedu.sugarmummy.storage.JsonUserPrefsStorage;
import seedu.sugarmummy.storage.Storage;
import seedu.sugarmummy.storage.StorageManager;
import seedu.sugarmummy.storage.UserPrefsStorage;
import seedu.sugarmummy.storage.biography.JsonUserListStorage;
import seedu.sugarmummy.storage.biography.UserListStorage;
import seedu.sugarmummy.storage.calendar.JsonCalendarStorage;
import seedu.sugarmummy.storage.recmf.JsonFoodListStorage;
import seedu.sugarmummy.storage.records.JsonRecordListStorage;
import seedu.sugarmummy.ui.Ui;
import seedu.sugarmummy.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static final String LABEL_BIO_DATA_TYPE = "bio";
    private static final String LABEL_CALENDAR_DATA_TYPE = "calendar";
    private static final String LABEL_FOOD_DATA_TYPE = "food list";
    private static final String LABEL_RECORD_DATA_TYPE = "record list";

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing SugarMummy ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        UserListStorage userListStorage = new JsonUserListStorage(userPrefs.getUserListFilePath());
        JsonFoodListStorage jsonFoodListStorage = new JsonFoodListStorage(userPrefs.getFoodListFilePath());
        JsonRecordListStorage jsonRecordListStorage = new JsonRecordListStorage(userPrefs.getRecordListFilePath());
        JsonCalendarStorage jsonCalendarStorage = new JsonCalendarStorage(userPrefs.getEventListFilePath(),
                userPrefs.getReminderListFilePath());
        storage = new StorageManager(userPrefsStorage, userListStorage, jsonFoodListStorage,
                jsonRecordListStorage, jsonCalendarStorage);

        initLogging(config);

        Model model = initModelManager(userPrefs);
        this.model = model;
        logic = new LogicManager(this.model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s SugarMummy and {@code userPrefs}. <br> The
     * data from the sample data will be used instead if {@code storage}'s SugarMummy is not found, or an empty
     * SugarMummy will be used instead if errors occur when reading {@code storage}'s SugarMummy.
     */
    private Model initModelManager(ReadOnlyUserPrefs userPrefs) {
        ReadOnlyUserList initialUserData;
        ReadOnlyCalendar initialCalendar;
        UniqueFoodList initialFoodList;
        UniqueRecordList initialRecordListData;

        initialUserData = (ReadOnlyUserList) getInitialData(LABEL_BIO_DATA_TYPE,
                SampleUserDataUtil::getSampleUserList, UserList::new);
        initialFoodList = (UniqueFoodList) getInitialData(LABEL_FOOD_DATA_TYPE,
                SampleFoodDataUtil::getSampleFoodList, UniqueFoodList::new);
        initialRecordListData = (UniqueRecordList) getInitialData(LABEL_RECORD_DATA_TYPE,
                SampleRecordDataUtil::getSampleRecordList, UniqueRecordList::new);
        initialCalendar = (ReadOnlyCalendar) getInitialData(LABEL_CALENDAR_DATA_TYPE,
                SampleCalendarDataUtil::getSampleCalendar, Calendar::new);

        return new ModelManager(userPrefs, initialUserData, initialFoodList, initialRecordListData,
                initialCalendar);
    }

    /**
     * Returns an optional containing read-only data types.
     *
     * @param dataType String label of data type for which optional data is to be obtained.
     * @return Optional containing read-only data types.
     * @throws IOException
     * @throws DataConversionException
     */
    private Optional<? extends ReadOnlyData> getOptionalData(String dataType) throws IOException,
            DataConversionException {
        switch (dataType) {
        case LABEL_BIO_DATA_TYPE:
            return storage.readUserList();
        case LABEL_FOOD_DATA_TYPE:
            return storage.readFoodList();
        case LABEL_RECORD_DATA_TYPE:
            return storage.readRecordList();
        case LABEL_CALENDAR_DATA_TYPE:
            return storage.readCalendar();
        default:
            return null;
        }
    }

    /**
     * Returns an object representing data of the given data type.
     *
     * @param dataType           String representing the type of initial data to be retrieved
     * @param sampleDataSupplier Supplier that creates a new sample data file upon execution.
     * @param dataObjectSupplier Supplier that creates a new data file upon execution.
     * @return Object representing data of the given data type.
     */
    private Object getInitialData(String dataType, Supplier<? extends Object> sampleDataSupplier,
                                  Supplier<? extends Object> dataObjectSupplier) {
        Object initialData;
        try {
            Optional<? extends ReadOnlyData> dataOptional = getOptionalData(dataType);
            if (dataOptional.isEmpty()) {
                logger.info(capitaliseFirstLetter(dataType) + " data file not found. Will be starting a sample "
                        + dataType + " data file");
                initialData = sampleDataSupplier.get();
            } else {
                initialData = dataOptional.get();
            }
        } catch (DataConversionException | IOException e) {
            logger.warning(dataType + "data file not in the correct format. Will be starting with an empty "
                    + dataType + " data file");
            initialData = dataObjectSupplier.get();
        }
        return initialData;
    }

    private String capitaliseFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br> The default file path {@code
     * Config#DEFAULT_CONFIG_FILE} will be used instead if {@code configFilePath} is null.
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
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path, or a new {@code UserPrefs}
     * with default configuration if errors occur when reading from the file.
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
            logger.warning("Problem while reading from the file. Will be starting with an empty SugarMummy");
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
        logger.info("Starting SugarMummy " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping SugarMummy ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        logic.stopAllReminders();
    }
}
