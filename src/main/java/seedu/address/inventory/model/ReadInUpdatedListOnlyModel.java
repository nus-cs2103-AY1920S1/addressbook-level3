package seedu.address.inventory.model;

/**
 * Acts as a facade that allows only some methods from the Model Manager.
 */
public interface ReadInUpdatedListOnlyModel {

    /**
     * Allows the inventory model to read in the updated file from its storage.
     */
    void readInUpdatedList();
}


