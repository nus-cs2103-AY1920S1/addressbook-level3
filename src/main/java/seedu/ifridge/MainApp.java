package seedu.ifridge;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.ifridge.commons.core.Config;
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.commons.core.Version;
import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.commons.util.ConfigUtil;
import seedu.ifridge.commons.util.StringUtil;
import seedu.ifridge.logic.Logic;
import seedu.ifridge.logic.LogicManager;
import seedu.ifridge.model.GroceryList;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.ReadOnlyGroceryList;
import seedu.ifridge.model.ReadOnlyShoppingList;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.ReadOnlyUserPrefs;
import seedu.ifridge.model.ShoppingList;
import seedu.ifridge.model.TemplateList;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.util.SampleDataUtil;
import seedu.ifridge.model.waste.WasteMonth;
import seedu.ifridge.storage.GroceryListStorage;
import seedu.ifridge.storage.JsonGroceryListStorage;
import seedu.ifridge.storage.JsonTemplateListStorage;
import seedu.ifridge.storage.JsonUserPrefsStorage;
import seedu.ifridge.storage.Storage;
import seedu.ifridge.storage.StorageManager;
import seedu.ifridge.storage.TemplateListStorage;
import seedu.ifridge.storage.UserPrefsStorage;
import seedu.ifridge.storage.shoppinglist.BoughtListStorage;
import seedu.ifridge.storage.shoppinglist.JsonBoughtItemStorage;
import seedu.ifridge.storage.shoppinglist.JsonShoppingItemStorage;
import seedu.ifridge.storage.shoppinglist.ShoppingListStorage;
import seedu.ifridge.storage.wastelist.JsonWasteListStorage;
import seedu.ifridge.storage.wastelist.WasteListStorage;
import seedu.ifridge.ui.Ui;
import seedu.ifridge.ui.UiManager;

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
        logger.info("=============================[ Initializing GroceryList ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GroceryListStorage groceryListStorage = new JsonGroceryListStorage(userPrefs.getGroceryListFilePath());
        TemplateListStorage templateListStorage = new JsonTemplateListStorage(userPrefs.getTemplateListFilePath());
        WasteListStorage wasteListStorage = new JsonWasteListStorage(userPrefs.getWasteArchiveFilePath());
        ShoppingListStorage shoppingListStorage = new JsonShoppingItemStorage(userPrefs.getShoppingListFilePath());
        BoughtListStorage boughtListStorage = new JsonBoughtItemStorage(userPrefs.getBoughtListFilePath());
        storage = new StorageManager(groceryListStorage, userPrefsStorage, templateListStorage, wasteListStorage,
                shoppingListStorage, boughtListStorage);

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

        Optional<ReadOnlyGroceryList> groceryListOptional;
        Optional<ReadOnlyTemplateList> templateListOptional;
        Optional<ReadOnlyShoppingList> shoppingListOptional;
        Optional<ReadOnlyGroceryList> boughtListOptional;
        ReadOnlyGroceryList initialGroceryListData;
        ReadOnlyTemplateList initialTemplateListData;
        TreeMap<WasteMonth, WasteList> initialWasteArchiveData;
        ReadOnlyShoppingList initialShoppingListData;
        ReadOnlyGroceryList initialBoughtListData;

        try {
            groceryListOptional = storage.readGroceryList();
            templateListOptional = storage.readTemplateList();
            shoppingListOptional = storage.readShoppingList();
            boughtListOptional = storage.readBoughtList();
            if (!groceryListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample GroceryList");
            }
            if (!templateListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TemplateList");
            }
            if (!shoppingListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ShoppingList");
            }
            if (!boughtListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample Bought List.");
            }
            initialGroceryListData = groceryListOptional.orElseGet(SampleDataUtil::getSampleGroceryList);
            initialTemplateListData = templateListOptional.orElseGet(SampleDataUtil::getSampleTemplateList);
            initialShoppingListData = shoppingListOptional.orElseGet(SampleDataUtil::getSampleShoppingList);
            initialBoughtListData = boughtListOptional.orElseGet(SampleDataUtil::getSampleBoughtList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GroceryList");
            initialGroceryListData = new GroceryList();
            initialTemplateListData = new TemplateList();
            initialShoppingListData = new ShoppingList();
            initialBoughtListData = new GroceryList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GroceryList");
            initialGroceryListData = new GroceryList();
            initialTemplateListData = new TemplateList();
            initialShoppingListData = new ShoppingList();
            initialBoughtListData = new GroceryList();
        }

        initialWasteArchiveData = initModelManagerWaste(storage);


        return new ModelManager(initialGroceryListData, userPrefs, initialTemplateListData, initialWasteArchiveData,
                initialShoppingListData, initialBoughtListData);
    }

    /**
     * Returns the initial waste list archive.
     */
    private TreeMap<WasteMonth, WasteList> initModelManagerWaste(Storage storage) {

        Optional<TreeMap<WasteMonth, WasteList>> wasteListOptional;
        TreeMap<WasteMonth, WasteList> initialWasteArchiveData;

        WasteList.initialiseWasteArchive();


        try {
            wasteListOptional = storage.readWasteList();
            if (!wasteListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample WasteList");
            }
            initialWasteArchiveData = wasteListOptional.orElseGet(SampleDataUtil::getSampleWasteArchive);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty WasteList");
            initialWasteArchiveData = new TreeMap<>();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty WasteList");
            initialWasteArchiveData = new TreeMap<>();
        }


        return initialWasteArchiveData;


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
            logger.warning("Problem while reading from the file. Will be starting with an empty GroceryList");
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
        logger.info("Starting GroceryList " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Grocery List ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
