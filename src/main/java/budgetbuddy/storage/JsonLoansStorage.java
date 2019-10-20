package budgetbuddy.storage;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.commons.util.FileUtil;
import budgetbuddy.commons.util.JsonUtil;
import budgetbuddy.model.LoansManager;

/**
 * A class to access LoansManager data stored as a json file on the hard disk.
 */
public class JsonLoansStorage implements LoansStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLoansStorage.class);

    private Path filePath;

    public JsonLoansStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLoansFilePath() {
        return filePath;
    }

    @Override
    public void saveLoans(LoansManager loansManager) throws IOException {
        saveLoans(loansManager, filePath);
    }

    /**
     * Similar to {@link #saveLoans(LoansManager)}
     *
     * @param filePath Location of the data. Cannot be null.
     */
    public void saveLoans(LoansManager loansManager, Path filePath) throws IOException {
        requireAllNonNull(loansManager, filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLoansManager(loansManager), filePath);
    }
}
