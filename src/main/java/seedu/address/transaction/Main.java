package seedu.address.transaction;

import seedu.address.transaction.logic.LogicManager;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        StorageManager storage = new StorageManager("data/transactionHistory.txt");

        ModelManager mm = new ModelManager(storage);

        LogicManager lm = new LogicManager(mm, storage);

        MainWindow mw = new MainWindow(lm);

        mw.show();
    }
}
