package seedu.address.model;

import java.io.IOException;

/**
 * Interface for current app state
 */

public interface ElisaState {
    public ElisaState deepCopy() throws IOException, ClassNotFoundException;
}
