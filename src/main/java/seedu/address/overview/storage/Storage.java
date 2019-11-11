package seedu.address.overview.storage;

import java.io.IOException;

import seedu.address.overview.model.Model;

/**
 * API of the Storage component
 */
public interface Storage {

    double[] readFromFile() throws IOException, NumberFormatException;

    void writeToFile(Model model) throws IOException;
}
