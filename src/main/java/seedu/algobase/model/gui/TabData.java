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

    /**
     * Returns true if both problems of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two problems.
     */
    public boolean isSameTabData(TabData otherTabData) {
        if (otherTabData == this) {
            return true;
        }

        return otherTabData != null
            && otherTabData.getModelType() == getModelType() && otherTabData.getModelIndex().equals(modelIndex);
    }

}
