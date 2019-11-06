package seedu.address.overview.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalOverview.BLANK_OVERVIEW_MODEL;
import static seedu.address.testutil.TypicalOverview.OVERVIEW_MODEL_WITH_DATA;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.overview.model.Model;
import seedu.address.overview.model.ModelManager;

public class StorageManagerTest {
    private StorageManager storageManager;

    public StorageManagerTest() {
        try {
            File file = File.createTempFile("testing", "tempOverview.txt");
            storageManager = new StorageManager(file);
        } catch (IOException e) {
            throw new AssertionError("This constructor should not throw an exception.");
        }
    }

    @Test
    public void fileReadSave_blankModel_success() throws Exception {

        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        storageManager.writeToFile(model);
        double[] retrieved = storageManager.readFromFile();
        assertEquals(model.getBudgetTarget(), retrieved[0]);
        assertEquals(model.getExpenseTarget(), retrieved[1]);
        assertEquals(model.getSalesTarget(), retrieved[2]);
        assertEquals(model.getBudgetThreshold(), retrieved[3]);
        assertEquals(model.getExpenseThreshold(), retrieved[4]);
        assertEquals(model.getSalesThreshold(), retrieved[5]);
    }

    @Test
    public void fileReadSave_modelWithData_success() throws Exception {

        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        storageManager.writeToFile(model);
        double[] retrieved = storageManager.readFromFile();
        assertEquals(model.getBudgetTarget(), retrieved[0]);
        assertEquals(model.getExpenseTarget(), retrieved[1]);
        assertEquals(model.getSalesTarget(), retrieved[2]);
        assertEquals(model.getBudgetThreshold(), retrieved[3]);
        assertEquals(model.getExpenseThreshold(), retrieved[4]);
        assertEquals(model.getSalesThreshold(), retrieved[5]);
    }
}
