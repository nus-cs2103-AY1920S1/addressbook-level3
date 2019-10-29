package seedu.tarence.storage;

import java.io.IOException;

import seedu.tarence.model.ReadOnlyApplication;

/**
 * Represents state storage for Tarence.
 */
public interface ApplicationStateStorage {

    Boolean isValidNumberOfRollbacks(Integer index);

    Integer maxNumberOfRollbacksAllowed();

    Integer getLatestStateIndex();

    ReadOnlyApplication getSpecifiedState (Integer index) throws IOException;

    void clearStateFolder() throws IOException;

}
