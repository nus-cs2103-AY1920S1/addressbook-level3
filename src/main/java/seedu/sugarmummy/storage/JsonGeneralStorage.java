package seedu.sugarmummy.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.commons.exceptions.DataConversionException;
import seedu.sugarmummy.commons.exceptions.IllegalValueException;
import seedu.sugarmummy.commons.util.FileUtil;
import seedu.sugarmummy.commons.util.JsonUtil;

/**
 * This is a class to access data stored as a json file on the hard disk.
 */
public class JsonGeneralStorage<T, U extends JsonSerializableContent<T>> implements GeneralStorage<T, U> {

    private static final Logger logger = LogsCenter.getLogger(JsonGeneralStorage.class);

    private Path filePath;
    private Class<T> itemClassType;
    private Class<U> jsonClassType;

    //Due the type erasure of generics, we have to pass the specific class type, such as UniqueFoodList.class
    public JsonGeneralStorage(Path filePath, Class<T> itemClassType, Class<U> jsonClassType) {
        this.filePath = filePath;
        this.itemClassType = itemClassType;
        this.jsonClassType = jsonClassType;
    }

    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public Optional<T> read() throws DataConversionException {
        return read(filePath);
    }

    /**
     * Similar to {@link #read()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<T> read(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<U> jsonContent = JsonUtil.readJsonFile(filePath, jsonClassType);

        if (jsonContent.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonContent.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void save(T content) throws IOException {
        save(content, filePath);
    }

    /**
     * Similar to {@link #save(T)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void save(T content, Path filePath) throws IOException {
        requireNonNull(content);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        try {
            JsonUtil.saveJsonFile(jsonClassType.getConstructor(itemClassType).newInstance(content), filePath);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
                | InstantiationException e) {
            assert false : "Incorrect implement of JsonSerializableItem class";
        }
    }

}
