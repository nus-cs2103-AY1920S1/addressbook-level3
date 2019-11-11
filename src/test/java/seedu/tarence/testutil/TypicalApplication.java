package seedu.tarence.testutil;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.commons.util.JsonUtil;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.storage.JsonSerializableApplication;

/**
 * Returns an application from test folder for testing purposes
 */
public class TypicalApplication {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableApplicationTest");
    private static final Path VALID_APPLICATION = TEST_DATA_FOLDER.resolve("validApplication.json");

    /**
     * Reads an application json stored in test folder.
     */
    public static ReadOnlyApplication getValidApplication() throws IllegalValueException, DataConversionException {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(VALID_APPLICATION,
                JsonSerializableApplication.class).get();

        ReadOnlyApplication applicationFromFile = dataFromFile.toModelType();
        return applicationFromFile;
    }
}
