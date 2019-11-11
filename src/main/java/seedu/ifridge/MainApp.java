package seedu.ifridge;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.ObservableList;
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
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.WasteList;
import seedu.ifridge.model.food.*;
import seedu.ifridge.model.food.exceptions.InvalidDictionaryException;
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
import seedu.ifridge.storage.shoppinglist.JsonBoughtListStorage;
import seedu.ifridge.storage.shoppinglist.JsonShoppingListStorage;
import seedu.ifridge.storage.shoppinglist.ShoppingListStorage;
import seedu.ifridge.storage.unitdictionary.JsonUnitDictionaryStorage;
import seedu.ifridge.storage.unitdictionary.UnitDictionaryStorage;
import seedu.ifridge.storage.wastelist.JsonWasteListStorage;
import seedu.ifridge.storage.wastelist.WasteListStorage;
import seedu.ifridge.ui.Ui;
import seedu.ifridge.ui.UiManager;

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
        logger.info("=============================[ Initializing GroceryList ]===========================");
        logger.info("=============================[ Initializing TemplateList ]===========================");
        logger.info("=============================[ Initializing WasteList ]===========================");
        logger.info("=============================[ Initializing ShoppingList ]===========================");
        logger.info("=============================[ Initializing BoughtList ]===========================");
        logger.info("=============================[ Initializing UnitDictionary ]===========================");

        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        GroceryListStorage groceryListStorage = new JsonGroceryListStorage(userPrefs.getGroceryListFilePath());
        TemplateListStorage templateListStorage = new JsonTemplateListStorage(userPrefs.getTemplateListFilePath());
        WasteListStorage wasteListStorage = new JsonWasteListStorage(userPrefs.getWasteArchiveFilePath());
        ShoppingListStorage shoppingListStorage = new JsonShoppingListStorage(userPrefs.getShoppingListFilePath());
        BoughtListStorage boughtListStorage = new JsonBoughtListStorage(userPrefs.getBoughtListFilePath());
        UnitDictionaryStorage unitDictionaryStorage = new JsonUnitDictionaryStorage(
                userPrefs.getUnitDictionaryFilePath());
        storage = new StorageManager(groceryListStorage, userPrefsStorage, templateListStorage, wasteListStorage,
                shoppingListStorage, boughtListStorage, unitDictionaryStorage);

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
        Optional<ReadOnlyShoppingList> shoppingListOptional;
        Optional<ReadOnlyGroceryList> boughtListOptional;
        Optional<ReadOnlyTemplateList> templateListOptional;
        ReadOnlyGroceryList initialGroceryListData;
        TreeMap<WasteMonth, WasteList> initialWasteArchiveData;
        ReadOnlyShoppingList initialShoppingListData;
        ReadOnlyGroceryList initialBoughtListData;
        ReadOnlyTemplateList initialTemplateListData;
        UnitDictionary initialUnitDictionaryData;

        initialShoppingListData = initModelManagerShoppingList(storage);
        initialGroceryListData = initModelManagerGroceryList(storage);
        initialBoughtListData = initModelManagerBoughtList(storage);
        initialWasteArchiveData = initModelManagerWaste(storage);
        initialTemplateListData = initModelManagerTemplateList(storage);
        try {
            initialUnitDictionaryData = initModelManagerUnitDictionary(storage, initialGroceryListData,
                    initialTemplateListData, initialShoppingListData, initialBoughtListData);
        } catch (InvalidDictionaryException e) {
            initialShoppingListData = new ShoppingList();
            initialGroceryListData = new GroceryList();
            initialTemplateListData = new TemplateList();
            initialBoughtListData = new GroceryList();
            initialUnitDictionaryData = new UnitDictionary(new HashMap<String, String>());
        }

        return new ModelManager(initialGroceryListData, userPrefs, initialTemplateListData, initialWasteArchiveData,
                initialShoppingListData, initialBoughtListData, initialUnitDictionaryData);
    }

    /**
     * Returns the initial unit dictionary.
     */
    private UnitDictionary initModelManagerUnitDictionary(
            Storage storage, ReadOnlyGroceryList groceryList, ReadOnlyTemplateList templateList,
            ReadOnlyShoppingList shoppingList, ReadOnlyGroceryList boughtList) throws InvalidDictionaryException {

        Optional<UnitDictionary> unitDictionaryOptional;
        UnitDictionary newUnitDictionary = generateNewUnitDictionary(groceryList, templateList,
                                                                     shoppingList, boughtList);
        UnitDictionary initialUnitDictionaryData;
        try {
            unitDictionaryOptional = storage.readUnitDictionary();
            if (!unitDictionaryOptional.isPresent()) {
                logger.info("Data file not found. Will generate new UnitDictionary");
                initialUnitDictionaryData = newUnitDictionary;
            }
            initialUnitDictionaryData = unitDictionaryOptional.orElse(newUnitDictionary);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will generate new UnitDictionary");
            initialUnitDictionaryData = newUnitDictionary;
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will generate new UnitDictionary");
            initialUnitDictionaryData = newUnitDictionary;
        }

        return initialUnitDictionaryData;
    }

    /**
     * Checks through the initial grocerylist, templatelist, shoppinglist to generate a new unit dictionary
     * if the unit dictionary is empty. If the units of the items in the grocerylist, templatelist, shoppinglist is
     * incorrect, it will throw an error.
     * @param groceryList the list of grocery items to be added into model
     * @param templateList the list of template items and templates to be added into model
     * @param shoppingList the list of shopping items to be added into model
     * @return
     */
    private UnitDictionary generateNewUnitDictionary(ReadOnlyGroceryList groceryList, ReadOnlyTemplateList templateList,
                                                     ReadOnlyShoppingList shoppingList, ReadOnlyGroceryList boughtList)
            throws InvalidDictionaryException {

        HashMap<String, String> mapToBeCreated = new HashMap<>();
        mapToBeCreated = updateMapWithTemplateItems(mapToBeCreated, templateList);
        mapToBeCreated = updateMapWithGroceryItems(mapToBeCreated, groceryList);
        mapToBeCreated = updateMapWithShoppingItems(mapToBeCreated, shoppingList);
        mapToBeCreated = updateMapWithBoughtItems(mapToBeCreated, boughtList);

        return new UnitDictionary(mapToBeCreated);
    }

    /**
     * Updates the dictionary map with the names and unitTypes in the template list
     * @param mapToBeCreated map to be edited
     * @param templateList list of templates
     * @return edited map to be entered into the UnitDictionary
     */
    private HashMap<String, String> updateMapWithTemplateItems(
            HashMap<String, String> mapToBeCreated, ReadOnlyTemplateList templateList)
            throws InvalidDictionaryException {

        ObservableList<UniqueTemplateItems> templates = templateList.getTemplateList();

        for (int i = 0; i < templates.size(); i++) {
            UniqueTemplateItems listN = templates.get(i);
            for (int j = 0; j < listN.getSize(); j++) {
                Food foodItem = listN.get(j);
                Name name = foodItem.getName();
                Amount amount = foodItem.getAmount();

                String nameInUpperCase = name.toString().toUpperCase();
                String unitType = amount.getUnitType(amount);
                String setType = mapToBeCreated.get(nameInUpperCase);

                if (setType != null && !setType.equals(unitType)) {
                    throw new InvalidDictionaryException();
                } else if (setType == null) {
                    mapToBeCreated.put(nameInUpperCase, unitType);
                } else {
                    continue;
                }
            }

        }
        return mapToBeCreated;
    }

    /**
     * Updates the dictionary map with the names and unitTypes in the grocery list
     * @param mapToBeCreated map to be edited
     * @param groceryList list consisting of grocery items
     * @return edited map to be entered into Unit Dictionary
     */
    private HashMap<String, String> updateMapWithGroceryItems(
            HashMap<String, String> mapToBeCreated, ReadOnlyGroceryList groceryList)
            throws InvalidDictionaryException {
        //Method to be written
        ObservableList<GroceryItem> groceryItems = groceryList.getGroceryList();

        for (int i = 0; i < groceryItems.size(); i++) {
            GroceryItem groceryItem = groceryItems.get(i);
            Name name = groceryItem.getName();
            Amount amount = groceryItem.getAmount();

            String nameInUpperCase = name.toString().toUpperCase();
            String unitType = Amount.getUnitType(amount);
            String setType = mapToBeCreated.get(nameInUpperCase);

            if (setType != null && !setType.equals(unitType)) {
                throw new InvalidDictionaryException();
            } else if (setType == null) {
                mapToBeCreated.put(nameInUpperCase, unitType);
            } else {
                continue;
            }
        }

        return mapToBeCreated;
    }

    /**
     * Updates the dictionary map with the names and unitTypes in the bought list
     * @param mapToBeCreated map to be edited
     * @param boughtList list consisting of bought items
     * @return edited map to be entered into Unit Dictionary
     */
    private HashMap<String, String> updateMapWithBoughtItems(
            HashMap<String, String> mapToBeCreated, ReadOnlyGroceryList boughtList)
            throws InvalidDictionaryException {
        ObservableList<GroceryItem> boughtItems = boughtList.getGroceryList();

        for (int i = 0; i < boughtItems.size(); i++) {
            GroceryItem boughtItem = boughtItems.get(i);
            Name name = boughtItem.getName();
            Amount amount = boughtItem.getAmount();

            String nameInUpperCase = name.toString().toUpperCase();
            String unitType = Amount.getUnitType(amount);
            String setType = mapToBeCreated.get(nameInUpperCase);

            if (setType != null && !setType.equals(unitType)) {
                throw new InvalidDictionaryException();
            } else if (setType == null) {
                mapToBeCreated.put(nameInUpperCase, unitType);
            } else {
                continue;
            }
        }

        return mapToBeCreated;
    }

    private HashMap<String, String> updateMapWithShoppingItems(
            HashMap<String, String> mapToBeCreated, ReadOnlyShoppingList shoppingList)
            throws InvalidDictionaryException {
        ObservableList<ShoppingItem> shoppingItems = shoppingList.getShoppingList();

        for (int i = 0; i < shoppingItems.size(); i++) {
            ShoppingItem shoppingItem = shoppingItems.get(i);
            Name name = shoppingItem.getName();
            Amount amount = shoppingItem.getAmount();

            String nameInUpperCase = name.toString().toUpperCase();
            String unitType = Amount.getUnitType(amount);
            String setType = mapToBeCreated.get(nameInUpperCase);

            if (setType != null && !setType.equals(unitType)) {
                throw new InvalidDictionaryException();
            } else if (setType == null) {
                mapToBeCreated.put(nameInUpperCase, unitType);
            } else {
                continue;
            }
        }

        return mapToBeCreated;
    }

    /**
     * Returns the initial grocery list.
     */
    private ReadOnlyGroceryList initModelManagerGroceryList(Storage storage) {
        Optional<ReadOnlyGroceryList> groceryListOptional;
        ReadOnlyGroceryList initialGroceryListData;
        try {
            groceryListOptional = storage.readGroceryList();
            if (!groceryListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample GroceryList");
            }
            initialGroceryListData = groceryListOptional.orElseGet(SampleDataUtil::getSampleGroceryList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty GroceryList");
            initialGroceryListData = new GroceryList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty GroceryList");
            initialGroceryListData = new GroceryList();
        }

        return initialGroceryListData;
    }

    /**
     * Returns the initial bought list.
     */
    private ReadOnlyGroceryList initModelManagerBoughtList(Storage storage) {
        Optional<ReadOnlyGroceryList> boughtListOptional;
        ReadOnlyGroceryList initialBoughtListData;
        try {
            boughtListOptional = storage.readBoughtList();
            if (!boughtListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample BoughtList");
            }
            initialBoughtListData = boughtListOptional.orElseGet(SampleDataUtil::getSampleBoughtList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty BoughtList");
            initialBoughtListData = new GroceryList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty BoughtList");
            initialBoughtListData = new GroceryList();
        }

        return initialBoughtListData;
    }

    /**
     * Returns the initial template list.
     */
    private ReadOnlyTemplateList initModelManagerTemplateList(Storage storage) {
        Optional<ReadOnlyTemplateList> templateListOptional;
        ReadOnlyTemplateList initialTemplateListData;
        try {
            templateListOptional = storage.readTemplateList();
            if (!templateListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TemplateList");
            }
            initialTemplateListData = templateListOptional.orElseGet(SampleDataUtil::getSampleTemplateList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TemplateList");
            initialTemplateListData = new TemplateList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TemplateList");
            initialTemplateListData = new TemplateList();
        }

        return initialTemplateListData;
    }

    /**
     * Returns the initial shopping list.
     */
    private ReadOnlyShoppingList initModelManagerShoppingList(Storage storage) {
        Optional<ReadOnlyShoppingList> shoppingListOptional;
        ReadOnlyShoppingList initialShoppingListData;
        try {
            shoppingListOptional = storage.readShoppingList();
            if (!shoppingListOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ShoppingList");
            }
            initialShoppingListData = shoppingListOptional.orElseGet(SampleDataUtil::getSampleShoppingList);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty ShoppingList");
            initialShoppingListData = new ShoppingList();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty ShoppingList");
            initialShoppingListData = new ShoppingList();
        }

        return initialShoppingListData;
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

        WasteMonth currentMonth = new WasteMonth(LocalDate.now());
        boolean currentWasteMonthInArchive = initialWasteArchiveData.containsKey(currentMonth);
        if (!currentWasteMonthInArchive) {
            initialWasteArchiveData.put(currentMonth, new WasteList(currentMonth));
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
        logger.info("============================ [ Stopping iFridge ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
