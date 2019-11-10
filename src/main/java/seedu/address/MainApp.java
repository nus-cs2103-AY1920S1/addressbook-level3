package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.address.appmanager.AppManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Version;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ConfigUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.appsettings.ReadOnlyAppSettings;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbanklist.ReadOnlyWordBankList;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.appsettings.AppSettingsStorage;
import seedu.address.storage.appsettings.JsonAppSettingsStorage;
import seedu.address.storage.globalstatistics.GlobalStatisticsStorage;
import seedu.address.storage.globalstatistics.JsonGlobalStatisticsStorage;
import seedu.address.storage.statistics.JsonWordBankStatisticsListStorage;
import seedu.address.storage.statistics.WordBankStatisticsListStorage;
import seedu.address.storage.userprefs.JsonUserPrefsStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;
import seedu.address.storage.wordbanks.JsonWordBankListStorage;
import seedu.address.storage.wordbanks.WordBankListStorage;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {
    private static final Version VERSION = new Version(1, 4, 0, true);
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    private Config config;
    private AppManager appManager;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Dukemon ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        logger.info("initialized userPrefs");
        WordBankListStorage wordBankListStorage = initWordBankListStorage(userPrefsStorage, userPrefs);
        logger.info("initialized wordBankListStorage");
        WordBankStatisticsListStorage wbStatsStorage =
                new JsonWordBankStatisticsListStorage(userPrefs.getDataFilePath());
        logger.info("initialized wbStatsStorage");
        GlobalStatisticsStorage globalStatsStorage = new JsonGlobalStatisticsStorage(userPrefs.getDataFilePath());
        logger.info("initialized globalStatsStorage");
        AppSettingsStorage appSettingsStorage = new JsonAppSettingsStorage(userPrefs.getAppSettingsFilePath());
        logger.info("initialized appSettingsStorage");
        storage = new StorageManager(wordBankListStorage, userPrefsStorage,
                wbStatsStorage, globalStatsStorage, appSettingsStorage);
        logger.info("initialized Storage");
        initLogging(config);
        model = initModel(storage, userPrefs);
        logic = new LogicManager(model, storage);
        appManager = new AppManager(logic);
        ui = new UiManager(appManager);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s storage and {@code userPrefs}. <br>
     * The data from the sample storage will be used instead if {@code storage}'s storage is not found,
     * or an empty storage will be used instead if errors occur when reading {@code storage}'s storage.
     */

    private Model initModel(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyWordBankList> optionalWbl = storage.getWordBankList();
        WordBankList wbl;
        if (optionalWbl.isPresent()) {
            wbl = (WordBankList) optionalWbl.get();
        } else {
            wbl = null;
            logger.warning("Word Bank List not initiated. IO exception.");
        }
        WordBankStatisticsList wbStatsList = storage.getWordBankStatisticsList();
        GlobalStatistics globalStatistics = storage.getGlobalStatistics();
        ReadOnlyAppSettings appSettings = null;
        try {
            Optional<AppSettings> settingsOptional = storage.readAppSettings();
            appSettings = settingsOptional.orElse(new AppSettings());
        } catch (IOException | DataConversionException e) {
            logger.warning("Init model failed. IO exception.");
        }
        return new ModelManager(wbl, wbStatsList, globalStatistics, userPrefs, appSettings);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
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

    /**
     /**
     * Returns a {@code WordBankListStorage} with the data from {@code UserPrefs}'s storage and {@code userPrefs}. <br>
     *
     * @param userPrefsStorage
     * @param userPrefs
     * @return WordBankListStorage.
     * @throws DataConversionException if word banks are corrupted.
     * @throws IllegalValueException if word banks list contain duplicate name or it's bank has duplicate cards.
     * @throws IOException file is not found.
     */
    private WordBankListStorage initWordBankListStorage(UserPrefsStorage userPrefsStorage, UserPrefs userPrefs)
            throws DataConversionException, IllegalValueException, IOException {
        WordBankListStorage wordBankListStorage = new JsonWordBankListStorage(userPrefs.getDataFilePath(),
                userPrefs.isSampleInitiated());

        if (!userPrefs.isSampleInitiated()) {
            userPrefs.setSampleInitiated();
            logger.info("UserPrefs isSampleInitiated set to true");
            userPrefsStorage.saveUserPrefs(userPrefs);
            logger.info("UserPrefs saved");
        }
        return wordBankListStorage;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    private UserPrefs initPrefs(UserPrefsStorage storage) {
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Dukemon");
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
        logger.info("Starting Dukemon " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Dukemon ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
