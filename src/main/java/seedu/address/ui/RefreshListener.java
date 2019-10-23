package seedu.address.ui;

import seedu.address.logic.Logic;

/**
 * API of refresh listener.
 */
public interface RefreshListener {

    /**
     * Refresh the Ui when data is imported from .csv file.
     */
    void dataImported(MainWindow window, Logic logic);

}
