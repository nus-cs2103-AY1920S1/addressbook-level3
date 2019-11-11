package seedu.tarence.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.UserPrefs;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.testutil.TypicalApplication;

public class JsonStateStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableApplicationTest");
    private static final Path VALID_APPLICATION = TEST_DATA_FOLDER.resolve("validApplication.json");

    @Test
    public void saveApplicationState_noChangeInState_successfulExecution() throws IllegalValueException,
            DataConversionException, IOException {
        ReadOnlyApplication initialState = TypicalApplication.getValidApplication();
        ReadOnlyApplication subsequentState = TypicalApplication.getValidApplication();

        Path filePath = Paths.get("src", "test", "testPreferences.json");
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(filePath);
        ApplicationStorage applicationStorage = new JsonApplicationStorage(Paths.get("src", "test",
                "data", "JsonSerializableApplicationTest", "validApplication.json"));
        JsonStateStorage jsonStateStorage = new JsonStateStorage("src", "test");

        Storage storage = new StorageManager(applicationStorage, userPrefsStorage, jsonStateStorage);
        Model model = new ModelManager(initialState, new UserPrefs());

        jsonStateStorage.saveFirstState(initialState);
        storage.saveApplication(model.getApplication(), filePath);

        jsonStateStorage.clearStateFolder();
    }

    @Test
    public void saveApplicationState_changeInState_successfulExecution() throws IOException,
            IllegalValueException, DataConversionException {
        ReadOnlyApplication initialState = TypicalApplication.getValidApplication();
        ReadOnlyApplication subsequentState = TypicalApplication.getValidApplication();

        Path filePath = Paths.get("src", "test", "testPreferences.json");
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(filePath);
        ApplicationStorage applicationStorage = new JsonApplicationStorage(Paths.get("src", "test", "data",
                "JsonSerializableApplicationTest", "validApplication.json"));
        JsonStateStorage jsonStateStorage = new JsonStateStorage("src", "test");

        Storage storage = new StorageManager(applicationStorage, userPrefsStorage, jsonStateStorage);
        Model model = new ModelManager(initialState, new UserPrefs());

        jsonStateStorage.saveFirstState(initialState);
        storage.saveApplication(model.getApplication(), filePath);
        ModCode modCode = new ModCode("CS9999");
        ArrayList<Tutorial> emptyTutorialList = new ArrayList<>();
        model.addModule(new Module(modCode, emptyTutorialList));

        jsonStateStorage.saveApplicationState(model.getApplication());

        jsonStateStorage.clearStateFolder();
    }
}
