package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.events.AppointmentBook;
import seedu.address.model.person.AddressBook;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.model.queue.QueueManager;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.util.SampleAppointmentDataUtil;
import seedu.address.model.util.SamplePersonDataUtil;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 3, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing ClerkPro ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        initLogging(config);

        storage = new StorageManager(config.getUserPrefsFilePath());
        model = initModelManager(storage, storage.getUserPrefs());
        logic = new LogicManager(model, storage);
        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {

        boolean dataBaseIsCorrupt = false;
        ReadOnlyAddressBook initialStaffAddressData = null;
        ReadOnlyAddressBook initialPatientAddressData = null;
        ReadOnlyAppointmentBook initialAppointmentData = null;
        ReadOnlyAppointmentBook initialDutyRosterData = null;

        try {
            Optional<ReadOnlyAddressBook> staffAddressBookOptional = storage.readStaffAddressBook();
            Optional<ReadOnlyAddressBook> patientAddressBookOptional = storage.readPatientAddressBook();
            Optional<ReadOnlyAppointmentBook> appointmentBookOptional = storage.readPatientAppointmentBook();
            Optional<ReadOnlyAppointmentBook> staffDutyRosterBookOptional = storage.readStaffDutyRosterBook();

            if (!staffAddressBookOptional.isPresent()) {
                dataBaseIsCorrupt = true;
                logger.info("Staff Data file not found.");

            } else if (!patientAddressBookOptional.isPresent()) {
                dataBaseIsCorrupt = true;
                logger.info("patient Data file not found.");

            } else if (!appointmentBookOptional.isPresent()) {
                dataBaseIsCorrupt = true;
                logger.info("appointment Data file not found.");

            } else if (!staffDutyRosterBookOptional.isPresent()) {
                dataBaseIsCorrupt = true;
                logger.info("shift Data file not found.");

            } else {
                initialStaffAddressData = staffAddressBookOptional.get();
                initialPatientAddressData = patientAddressBookOptional.get();
                initialAppointmentData = appointmentBookOptional.get();
                initialDutyRosterData = staffDutyRosterBookOptional.get();
            }

        } catch (DataConversionException e) {
            dataBaseIsCorrupt = true;
            logger.warning("Data file(s) not in the correct format.");
        } catch (IOException e) {
            dataBaseIsCorrupt = true;
            logger.warning("Problem while reading from the file.");
        }

        // restore sample data base if file corrupt detected
        if (dataBaseIsCorrupt) {
            logger.info("Potential corruption deleted. Will revert to last valid state.");
            PersonReferenceId.ClearAllReferenceId();
            initialStaffAddressData = SamplePersonDataUtil.getSampleStaffAddressBook();
            initialPatientAddressData = SamplePersonDataUtil.getSampleAddressBook();
            initialAppointmentData = SampleAppointmentDataUtil.getSampleAppointmentBook();
            initialDutyRosterData = SampleAppointmentDataUtil.getSampleDutyRosterBook();
        }

        assert initialPatientAddressData != null;
        assert initialStaffAddressData != null;
        assert initialAppointmentData != null;
        assert initialDutyRosterData != null;

        QueueManager queueManager = new QueueManager();

        return new ModelManager(initialPatientAddressData, initialStaffAddressData,
                initialAppointmentData, initialDutyRosterData,
                userPrefs, queueManager);
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


    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting ClerkPro " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping ClerkPro ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
