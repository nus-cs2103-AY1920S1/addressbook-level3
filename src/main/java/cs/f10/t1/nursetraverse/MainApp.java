package cs.f10.t1.nursetraverse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cs.f10.t1.nursetraverse.commons.core.Config;
import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.commons.core.Version;
import cs.f10.t1.nursetraverse.commons.exceptions.DataConversionException;
import cs.f10.t1.nursetraverse.commons.util.ConfigUtil;
import cs.f10.t1.nursetraverse.commons.util.StringUtil;
import cs.f10.t1.nursetraverse.logic.Logic;
import cs.f10.t1.nursetraverse.logic.LogicManager;
import cs.f10.t1.nursetraverse.model.AppointmentBook;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.ModelManager;
import cs.f10.t1.nursetraverse.model.PatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyAppointmentBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyPatientBook;
import cs.f10.t1.nursetraverse.model.ReadOnlyUserPrefs;
import cs.f10.t1.nursetraverse.model.UserPrefs;
import cs.f10.t1.nursetraverse.model.util.SampleDataUtil;
import cs.f10.t1.nursetraverse.storage.AppointmentBookStorage;
import cs.f10.t1.nursetraverse.storage.JsonAppointmentBookStorage;
import cs.f10.t1.nursetraverse.storage.JsonPatientBookStorage;
import cs.f10.t1.nursetraverse.storage.JsonUserPrefsStorage;
import cs.f10.t1.nursetraverse.storage.PatientBookStorage;
import cs.f10.t1.nursetraverse.storage.Storage;
import cs.f10.t1.nursetraverse.storage.StorageManager;
import cs.f10.t1.nursetraverse.storage.UserPrefsStorage;
import cs.f10.t1.nursetraverse.ui.Ui;
import cs.f10.t1.nursetraverse.ui.UiManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 4, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("============================= [ Initializing PatientBook and AppointmentBook ] "
                + "===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        PatientBookStorage patientBookStorage = new JsonPatientBookStorage(userPrefs.getPatientBookFilePath());
        AppointmentBookStorage appointmentBookStorage = new JsonAppointmentBookStorage(userPrefs
                                                                                        .getAppointmentBookFilePath());
        storage = new StorageManager(patientBookStorage, userPrefsStorage, appointmentBookStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s patient book and {@code userPrefs}
     * and {@code storage}'s appointment book. <br>
     * The data from the sample patient book or sample appointment book will be used instead if {@code storage}'s
     * patient book or appointment's is not found, or an empty patient book or appointment book will be used instead if
     * errors occur when reading {@code storage}'s patient book or appointment book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        Optional<ReadOnlyPatientBook> patientBookOptional;
        ReadOnlyPatientBook initialPatientData;

        Optional<ReadOnlyAppointmentBook> appointmentBookOptional;
        ReadOnlyAppointmentBook initialAppointmentData;
        try {
            patientBookOptional = storage.readPatientBook();
            appointmentBookOptional = storage.readAppointmentBook();

            if (!patientBookOptional.isPresent() || !appointmentBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample PatientBook "
                        + "and AppointmentBook");
                initialPatientData = SampleDataUtil.getSamplePatientBook();
                initialAppointmentData = SampleDataUtil.getSampleAppointmentBook();
            } else {
                initialPatientData = patientBookOptional.get();
                initialAppointmentData = appointmentBookOptional.get();
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty PatientBook "
                    + "and AppointmentBook");
            initialPatientData = new PatientBook();
            initialAppointmentData = new AppointmentBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty PatientBook "
                    + "and AppointmentBook");
            initialPatientData = new PatientBook();
            initialAppointmentData = new AppointmentBook();
        }

        return new ModelManager(initialPatientData, userPrefs, initialAppointmentData);
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
            logger.warning("Problem while reading from the file. Will be starting with an empty PatientBook");
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
        logger.info("Starting PatientBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping PatientBook and AppointmentBook ] "
                + "=============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
