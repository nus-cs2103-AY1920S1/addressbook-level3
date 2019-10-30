package seedu.address.stubs;

import seedu.address.overview.model.Model;
import seedu.address.overview.storage.Storage;

import java.io.*;

public class OverviewStorageStub implements Storage {
    private double[] values;

    public OverviewStorageStub(double[] values) {
        this.values = values;
    }

    @Override
    public double[] readFromFile() {
        return values;
    }

    @Override
    public void writeToFile(Model model) throws IOException {
    }
}
