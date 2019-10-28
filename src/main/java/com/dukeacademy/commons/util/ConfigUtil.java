package com.dukeacademy.commons.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.dukeacademy.commons.core.Config;
import com.dukeacademy.commons.exceptions.DataConversionException;

/**
 * A class for accessing the Config File.
 */
public class ConfigUtil {

    /**
     * Read config optional.
     *
     * @param configFilePath the config file path
     * @return the optional
     * @throws DataConversionException the data conversion exception
     */
    public static Optional<Config> readConfig(Path configFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(configFilePath, Config.class);
    }

    /**
     * Save config.
     *
     * @param config         the config
     * @param configFilePath the config file path
     * @throws IOException the io exception
     */
    public static void saveConfig(Config config, Path configFilePath) throws IOException {
        JsonUtil.saveJsonFile(config, configFilePath);
    }

}
