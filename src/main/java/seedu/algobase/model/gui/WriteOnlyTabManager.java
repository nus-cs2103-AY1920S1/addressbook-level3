package seedu.algobase.model.gui;

import java.util.function.Consumer;

import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;

/**
 * Write-only view of a tabmanager
 */
public interface WriteOnlyTabManager {
    /**
     * A Consumer that adds details tabdata to the TabManager
     */
    Consumer<Id> addDetailsTabConsumer(ModelType modelType);
}
