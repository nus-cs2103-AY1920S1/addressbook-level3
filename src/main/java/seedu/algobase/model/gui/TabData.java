package seedu.algobase.model.gui;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;

/**
 * Store details about a tab in the GUI.
 */
public class TabData {
    private final ModelType modelType;
    private final Id modelId;

    public TabData(ModelType modelType, Id modelId) {
        requireAllNonNull(modelType, modelId);

        this.modelId = modelId;
        this.modelType = modelType;
    }

    public ModelType getModelType() {
        return modelType;
    }

    public Id getModelId() {
        return modelId;
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
            && otherTabData.getModelType().equals(getModelType()) && otherTabData.getModelId().equals(modelId);
    }

    @Override
    public boolean equals(Object otherTabData) {
        if (this == otherTabData) {
            return true;
        }

        if (!(otherTabData instanceof TabData)) {
            return false;
        }

        return this.isSameTabData((TabData) otherTabData);
    }
}
