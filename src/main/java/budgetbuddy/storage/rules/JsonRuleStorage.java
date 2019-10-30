package budgetbuddy.storage.rules;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.exceptions.DataConversionException;
import budgetbuddy.commons.exceptions.IllegalValueException;
import budgetbuddy.commons.util.FileUtil;
import budgetbuddy.commons.util.JsonUtil;
import budgetbuddy.model.RuleManager;

/**
 * A class to access RuleManager data stored as a json file on the hard disk.
 */
public class JsonRuleStorage implements RuleStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRuleStorage.class);

    private Path filePath;

    public JsonRuleStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRuleFilePath() {
        return filePath;
    }

    @Override
    public Optional<RuleManager> readRules() throws DataConversionException {
        return readRules(filePath);
    }

    /**
     * Similar to {@link #readRules()}.
     * @param filePath Location of the data. Cannot be null.
     * @throws DataConversionException If the data file is not in the correct format.
     */
    public Optional<RuleManager> readRules(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRuleManager> jsonRuleManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableRuleManager.class);
        if (jsonRuleManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRuleManager.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveRules(RuleManager ruleManager) throws IOException {
        saveRules(ruleManager, filePath);
    }

    /**
     * Similar to {@link #saveRules(RuleManager)}
     *
     * @param filePath Location of the data. Cannot be null.
     */
    public void saveRules(RuleManager ruleManager, Path filePath) throws IOException {
        requireAllNonNull(ruleManager, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRuleManager(ruleManager), filePath);
    }
}
