package seedu.address.overview.storage;

import seedu.address.overview.model.Model;

import java.io.IOException;

/**
 * API of the Storage component
 */
public interface Storage {

    double[] readFromFile();

    void writeToFile(Model model) throws IOException;
}
