package budgetbuddy.storage.rules;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.model.RuleManager;

/**
 * Represents a storage for {@link RuleManager}.
 */
public interface RuleStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRuleFilePath();

    /**
     * Returns RuleManager data as a {@link RuleManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException If the data in storage is not in the expected format.
     * @throws IOException If there was any problem when reading from the storage.
     */
    Optional<RuleManager> readRules() throws DataConversionException, IOException;

    /**
     * @see #readRules()
     */
    Optional<RuleManager> readRules(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link RuleManager} to the storage.
     * @param ruleManager Cannot be null.
     * @throws IOException If any problem occurs when writing to the file.
     */
    void saveRules(RuleManager ruleManager) throws IOException;

    /**
     * @param filePath The path to save the data file to.
     * @see #saveRules(RuleManager)
     */
    void saveRules(RuleManager ruleManager, Path filePath) throws IOException;
}
