package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabData;

/**
 * Jackson-friendly version of {@link TabData}.
 */
public class JsonAdaptedTab {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Tab Data's %s field is missing!";

    private final String modelType;
    private final String modelId;

    /**
     * Constructs a {@code JsonAdaptedTab} with the given TabData.
     */
    @JsonCreator
    public JsonAdaptedTab(@JsonProperty("name") String modelType,
                           @JsonProperty("index") String modelId) {
        this.modelType = modelType;
        this.modelId = modelId;
    }

    /**
     * Converts a given {@code TabData} into this class for Jackson use.
     */
    public JsonAdaptedTab(TabData tabData) {
        modelType = tabData.getModelType().toString();
        modelId = tabData.getModelId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted TabData object into the model's {@code TabData} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TabData.
     */
    public TabData toModelType(AlgoBase algoBase) throws IllegalValueException {

        ModelType modelType = retrieveModelType(this.modelType);
        Id modelIndex = retrieveModelId(this.modelId);

        return new TabData(modelType, modelIndex);
    }

    /**
     * Converts an model type in string format to a ModelType enum.
     *
     * @param modelType Model Type in String format.
     * @return id in long format.
     * @throws IllegalValueException if string format is invalid.
     */
    public ModelType retrieveModelType(String modelType) throws IllegalValueException {
        if (modelType == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Model Type"));
        }

        try {
            return ModelType.valueOf(modelType);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Model Type"));
        }
    }

    /**
     * Converts a model id in string format to an Id Object.
     *
     * @param modelId model id in integer format.
     * @return the corresponding Index Object.
     * @throws IllegalValueException if string format is invalid.
     */
    public Id retrieveModelId(String modelId) throws IllegalValueException {
        return Id.generateId(modelId);
    }
}
