package seedu.address.model;

import java.io.IOException;

public interface ElisaState {
    public ElisaState deepCopy() throws IOException, ClassNotFoundException;
}
