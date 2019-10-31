package seedu.address.ui;

/**
 * API of refresh listener.
 */
public interface RefreshListener {

    /**
     * Refresh the Ui when data is imported from .csv file.
     */
    void dataImported();

}
