package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.food.UniqueTemplateItems;

/**
 * Unmodifiable view of an template
 */
public interface ReadOnlyTemplateList {

    /**
     * Returns an unmodifiable view of the template items list.
     * This list will not contain any duplicate template items.
     */
    ObservableList<UniqueTemplateItems> getTemplateList();

}
