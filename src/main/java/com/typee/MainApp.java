package com.typee;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.typee.commons.core.Config;
import com.typee.commons.core.LogsCenter;
import com.typee.commons.core.Version;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.commons.util.ConfigUtil;
import com.typee.commons.util.StringUtil;
import com.typee.logic.Logic;
import com.typee.logic.LogicManager;
import com.typee.model.EngagementList;
import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.UserPrefs;
import com.typee.model.util.SampleDataUtil;
import com.typee.storage.EngagementListStorage;
import com.typee.storage.JsonEngagementListStorage;
import com.typee.storage.JsonTypeeStorage;
import com.typee.storage.JsonUserPrefsStorage;
import com.typee.storage.Storage;
import com.typee.storage.StorageManager;
import com.typee.storage.TypeeStorage;
import com.typee.storage.UserPrefsStorage;
import com.typee.ui.Ui;
import com.typee.ui.UiManager;

import javafx.application.Application;
import javafx.stage.Stage;

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
        logger.info("=============================[ Initializing Typee ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        EngagementListStorage engagementListStorage = new JsonEngagementListStorage(userPrefs.getAddressBookFilePath());
        TypeeStorage typeeStorage = new JsonTypeeStorage(config.getTabMenuFilePath());
        storage = new StorageManager(engagementListStorage, userPrefsStorage, typeeStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s engagement manager and {@code userPrefs}.
     * <br> The data from the sample Typee application will be used instead if {@code storage}'s engagement
     * manager is not found, or an empty engagement manager will be used instead if errors occur when reading
     * {@code storage}'s engagement manager.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyEngagementList> addressBookOptional;
        ReadOnlyEngagementList initialData;
        try {
            addressBookOptional = storage.readEngagementList();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample engagement manager");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty engagement manager");
            initialData = new EngagementList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty engagement manager");
            initialData = new EngagementList();
        }

        return new ModelManager(initialData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty EngagementList");
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
        logger.info("Starting Typee " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Typee ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
