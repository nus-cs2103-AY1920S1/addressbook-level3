package seedu.algobase.model.gui;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.ModelType;

/**
 * Store details about a tab in the GUI.
 */
public class TabData {
    private final ModelType modelType;
    private final Index modelIndex;

    public TabData(ModelType modelType, Index modelIndex) {
        requireAllNonNull(modelType, modelIndex);

        this.modelIndex = modelIndex;
        this.modelType = modelType;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public Index getModelIndex() {
        return modelIndex;
    }
}
