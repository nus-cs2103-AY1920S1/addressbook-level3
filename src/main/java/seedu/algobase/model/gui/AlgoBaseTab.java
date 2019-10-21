package seedu.algobase.model.gui;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.model.ModelEnum;

/**
 * Store details about a tab in the GUI.
 */
public class AlgoBaseTab {
    private final ModelEnum modelType;
    private final Index modelIndex;

    public AlgoBaseTab(ModelEnum modelType, Index modelIndex) {
        requireAllNonNull(modelType, modelIndex);

        this.modelIndex = modelIndex;
        this.modelType = modelType;
    }

    public ModelEnum getModelType() {
        return modelType;
    }

    public Index getModelIndex() {
        return modelIndex;
    }
}
