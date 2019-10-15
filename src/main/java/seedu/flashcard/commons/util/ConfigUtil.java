package seedu.flashcard.commons.util;


import seedu.flashcard.commons.core.Config;
import seedu.flashcard.commons.exceptions.DataConversionException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class ConfigUtil {
    public static Optional<Config> readConfig(Path configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
