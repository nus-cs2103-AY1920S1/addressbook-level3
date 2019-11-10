package seedu.address.model;

import seedu.address.model.listeners.ModelDataListener;

/**
 * A ModelDataListener that copies whatever it is notified.
 */
public class ModelDataListenerStub implements ModelDataListener {

    private ModelData modelData;

    @Override
    public void onModelDataChange(ModelData modelData) {
        this.modelData = modelData;
    }

    public ModelData getModelData() {
        return modelData;
    }
}
