package seedu.address.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ModulesInfo;

/**
 * A class to access ModulesInfo stored in the hard disk as a json file
 */
public class JsonModulesInfoStorage implements ModulesInfoStorage {

    private Path filePath;

    public JsonModulesInfoStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getModulesInfoPath() {
        return filePath;
    }

    @Override
    public Optional<ModulesInfo> readModulesInfo() throws IOException {
        return readModulesInfo(filePath);
    }

    /**
     * Similar to {@link #readModulesInfo()}
     *
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<ModulesInfo> readModulesInfo(Path prefsFilePath) throws IOException {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream(prefsFilePath.toString());
        if (stream == null) {
            return Optional.empty();
        }
        ModulesInfo modulesInfo = JsonUtil.fromJsonString(
                new String(Objects.requireNonNull(stream.readAllBytes())), ModulesInfo.class);
        modulesInfo.init();
        return Optional.of(modulesInfo);
    }
}
