package seedu.system;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.system.commons.core.Config;
import seedu.system.commons.core.LogsCenter;
import seedu.system.commons.core.Version;
import seedu.system.commons.exceptions.DataConversionException;
import seedu.system.commons.util.ConfigUtil;
import seedu.system.commons.util.StringUtil;
import seedu.system.logic.Logic;
import seedu.system.logic.LogicManager;
import seedu.system.model.Data;
import seedu.system.model.Model;
import seedu.system.model.ModelManager;
import seedu.system.model.ReadOnlyData;
import seedu.system.model.ReadOnlyUserPrefs;
import seedu.system.model.UserPrefs;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Person;
import seedu.system.model.util.SampleDataUtil;
import seedu.system.storage.JsonSystemStorage;
import seedu.system.storage.JsonUserPrefsStorage;
import seedu.system.storage.Storage;
import seedu.system.storage.StorageManager;
import seedu.system.storage.SystemStorage;
import seedu.system.storage.UserPrefsStorage;
import seedu.system.ui.Ui;
import seedu.system.ui.UiManager;

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
        logger.info("=============================[ Initializing Data ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        SystemStorage systemStorage =
            new JsonSystemStorage(
                userPrefs.getPersonDataFilePath(),
                userPrefs.getCompetitionDataFilePath(),
                userPrefs.getParticipationDataFilePath()
            );
        storage = new StorageManager(systemStorage, userPrefsStorage);

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
        ReadOnlyData<Person> initialPersonData = new Data<>();
        Optional<ReadOnlyData<Person>> personDataOptional = Optional.empty();
        Optional<ReadOnlyData<Competition>> competitionDataOptional = Optional.empty();
        ReadOnlyData<Competition> initialCompetitionData = new Data<>();
        Optional<ReadOnlyData<Participation>> participationDataOptional = Optional.empty();
        ReadOnlyData<Participation> initialParticipationData = new Data<>();

        try {
            personDataOptional = storage.readPersonData();
            competitionDataOptional = storage.readCompetitionData();

            if (personDataOptional.isPresent() && competitionDataOptional.isPresent()) {
                initialPersonData = personDataOptional.get();
                initialCompetitionData = competitionDataOptional.get();
                participationDataOptional =
                    storage.readParticipationData(personDataOptional.get(), initialCompetitionData);
                if (participationDataOptional.isPresent()) {
                    initialParticipationData = participationDataOptional.get();
                }
            } else {
                logger.info("Not all data files were found. Will be starting with a sample Data");
                initialPersonData = SampleDataUtil.getSamplePersonData();
                initialCompetitionData = SampleDataUtil.getSampleCompetitionData();
                initialParticipationData = SampleDataUtil.getSampleParticipationData(initialPersonData,
                    initialCompetitionData);
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty Data");
            initialPersonData = new Data();
            initialCompetitionData = new Data();
            initialParticipationData = new Data();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty Data");
            initialPersonData = new Data();
            initialCompetitionData = new Data();
            initialParticipationData = new Data();
        }

        return new ModelManager(initialPersonData, initialCompetitionData, initialParticipationData, userPrefs);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty Data");
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
        logger.info("Starting Data " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
