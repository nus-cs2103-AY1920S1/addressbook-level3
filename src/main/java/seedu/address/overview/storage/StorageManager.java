package seedu.address.overview.storage;

import seedu.address.overview.model.Model;

import java.io.*;

/**
 * Manages storage of transaction data in local storage.
 */
public class StorageManager implements Storage {
    private final String filepath;
    private double[] values;

    public StorageManager(String filepath) {
        values = new double[6];
        this.filepath = filepath;
    }

    @Override
    public double[] readFromFile() {
        try {
            File f = new File(filepath);
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = bfr.readLine()) != null) {
                readInFileLine(line);
            }
        } catch (IOException e) {
            //do nothing since default values are initialised as optionals
        }
        return values;
    }

    @Override
    public void writeToFile(Model model) throws IOException {
        File f = new File(filepath);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }

        FileWriter fw = new FileWriter(this.filepath);
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
        s.append(model.getBudgetTarget());
        s.append(System.getProperty("line.separator"));

        s.append("salesThreshold|");
        s.append(model.getSalesThreshold());
        s.append(System.getProperty("line.separator"));

        s.append("expenseThreshold|");
        s.append(model.getExpenseThreshold());
        s.append(System.getProperty("line.separator"));

        fw.write(s.toString());
        fw.close();
    }

    void readInFileLine(String line) {
        String[] params = line.split("\\|");
        if (params[0] == "budgetTarget") {
            values[0] = Double.parseDouble(params[1]);
        } else if (params[0] == "expenseTarget") {
            values[1] = Double.parseDouble(params[1]);
        } else if (params[0] == "salesTarget") {
            values[2] = Double.parseDouble(params[1]);
        } else if (params[0] == "budgetThreshold") {
            values[3] = Double.parseDouble(params[1]);
        } else if (params[0] == "salesThreshold") {
            values[4] = Double.parseDouble(params[1]);
        } else if (params[0] == "expenseThreshold") {
            values[5] = Double.parseDouble(params[1]);
        }
    }

}
