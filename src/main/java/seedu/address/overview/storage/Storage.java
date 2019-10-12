package seedu.address.overview.storage;

import seedu.address.overview.model.Model;
import seedu.address.overview.util.TransactionList;

import java.io.IOException;

/**
 * API of the Storage component
 */
public interface Storage {

    double[] readFromFile();

    void writeToFile(Model model) throws IOException;
}
