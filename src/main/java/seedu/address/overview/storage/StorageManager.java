package seedu.address.overview.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.overview.model.Model;

/**
 * Manages storage of transaction data in local storage.
 */
public class StorageManager implements Storage {
    private final File file;
    private double[] values;

    public StorageManager(File file) {
        values = new double[6];
        this.file = file;
    }

    @Override
    public double[] readFromFile() throws IOException, NumberFormatException {
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = bfr.readLine()) != null) {
            readInFileLine(line);
        }
        return values;
    }

    @Override
    public void writeToFile(Model model) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(this.file);
        StringBuilder s = new StringBuilder();

        s.append("budgetTarget|");
        s.append(model.getBudgetTarget());
        s.append(System.getProperty("line.separator"));

        s.append("expenseTarget|");
        s.append(model.getExpenseTarget());
        s.append(System.getProperty("line.separator"));

        s.append("salesTarget|");
        s.append(model.getSalesTarget());
        s.append(System.getProperty("line.separator"));

        s.append("budgetThreshold|");
        s.append(model.getBudgetThreshold());
        s.append(System.getProperty("line.separator"));

        s.append("expenseThreshold|");
        s.append(model.getExpenseThreshold());
        s.append(System.getProperty("line.separator"));

        s.append("salesThreshold|");
        s.append(model.getSalesThreshold());
        s.append(System.getProperty("line.separator"));

        fw.write(s.toString());
        fw.close();
    }

    /**
     * Reads in the current file line and stores the value from that line.
     * @param line The current file line.
     */
    void readInFileLine(String line) {
        String[] params = line.split("\\|");

        if (params[0].equals("budgetTarget")) {
            values[0] = Double.parseDouble(params[1]);
        } else if (params[0].equals("expenseTarget")) {
            values[1] = Double.parseDouble(params[1]);
        } else if (params[0].equals("salesTarget")) {
            values[2] = Double.parseDouble(params[1]);
        } else if (params[0].equals("budgetThreshold")) {
            values[3] = Double.parseDouble(params[1]);
        } else if (params[0].equals("expenseThreshold")) {
            values[4] = Double.parseDouble(params[1]);
        } else if (params[0].equals("salesThreshold")) {
            values[5] = Double.parseDouble(params[1]);
        }
    }

}
