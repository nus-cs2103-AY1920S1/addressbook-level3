package seedu.billboard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.billboard.commons.core.Config;
import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.commons.core.Version;
import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.commons.util.ConfigUtil;
import seedu.billboard.commons.util.StringUtil;
import seedu.billboard.logic.Logic;
import seedu.billboard.logic.LogicManager;
import seedu.billboard.model.*;
import seedu.billboard.model.util.SampleDataUtil;
import seedu.billboard.storage.*;
import seedu.billboard.ui.Ui;
import seedu.billboard.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 1, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing Billboard ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        BillboardStorage billboardStorage = new JsonBillboardStorage(userPrefs.getBillboardFilePath());
        ArchiveStorage archiveStorage = new JsonArchiveStorage(userPrefs.getArchiveFilePath());
        storage = new StorageManager(billboardStorage, archiveStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s Billboard expenses, Archive expenses
     * and {@code userPrefs}. <br>
     * The data from the sample Billboard will be used instead if {@code storage}'s Billboard or Archive expenses
     * are not found, or an empty Billboard will be used instead if errors occur
     * when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyBillboard> billboardOptional;
        ReadOnlyBillboard initialData;
        try {
            billboardOptional = storage.readBillboard();
            if (billboardOptional.isEmpty()) {
                logger.info("Data file not found. Will be starting with a sample Billboard");
            }
            initialData = billboardOptional.orElseGet(SampleDataUtil::getSampleBillboard);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Billboard");
            initialData = new Billboard();
        } catch (IOException e) {
            logger.warning("Problem while reading from the data file. Will be starting with an empty Billboard");
            initialData = new Billboard();
        }

        Optional<ReadOnlyArchives> archiveOptional;
        ReadOnlyArchives initialArchiveData;
        try {
            archiveOptional = storage.readArchive();
            if (archiveOptional.isEmpty()) {
                logger.info("Archive file not found. Will be starting with a sample archive");
            }
            initialArchiveData = archiveOptional.orElseGet(SampleDataUtil::getSampleBillboard);
        } catch (DataConversionException e) {
            logger.warning("Archive file not in the correct format. Will be starting with an empty archive");
            initialArchiveData = new Archives();
        } catch (IOException e) {
            logger.warning("Problem while reading from the archive file. Will be starting with an empty archive");
            initialArchiveData = new Archives();
        }

        return new ModelManager(initialData, initialArchiveData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Billboard");
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
        logger.info("Starting Billboard " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Billboard ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
