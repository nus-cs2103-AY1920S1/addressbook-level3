package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    public static Optional<Config> readConfig(Path configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static Optional<Config> readEncryptedConfig(Path configFilePath, String password)
            throws DataConversionException {
        return JsonUtil.readEncryptedJsonFile(configFilePath, Config.class, password);
    }

    public static void saveConfig(Config config, Path configFilePath, String password) throws IOException {
        JsonUtil.saveEncryptedJsonFile(config, configFilePath, password);
    }

}
