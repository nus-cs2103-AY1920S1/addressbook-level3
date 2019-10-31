package seedu.algobase.model.gui;

import java.util.function.Consumer;

import seedu.algobase.commons.core.index.Index;
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

    /**
     * A Consumer that removes details tabdata to the TabManager
     */
    Consumer<Id> removeDetailsTabConsumer(ModelType modelType);

    void setDisplayTabPaneIndex(Index index) throws IndexOutOfBoundsException;

    void setDetailsTabPaneIndex(Index index) throws IndexOutOfBoundsException;

    Consumer<Id> switchDetailsTabConsumer(ModelType modelType);
}
