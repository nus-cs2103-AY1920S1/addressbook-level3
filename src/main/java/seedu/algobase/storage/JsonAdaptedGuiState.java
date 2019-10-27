package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.gui.GuiState;

/**
 * Jackson-friendly version of {@link GuiState}.
 */
public class JsonAdaptedGuiState {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Gui State's %s field is missing!";

    private final JsonAdaptedTabManager tabManager;

    /**
     * Constructs a {@code JsonAdaptedGuiState} with the given Gui State.
     */
    @JsonCreator
    public JsonAdaptedGuiState(@JsonProperty("tabManager") JsonAdaptedTabManager tabManager) {
        this.tabManager = tabManager;
    }

    /**
     * Converts a given {@code GuiState} into this class for Jackson use.
     */
    public JsonAdaptedGuiState(GuiState guiState) {
        this.tabManager = new JsonAdaptedTabManager(guiState.getTabManager());
    }

    /**
     * Converts this Jackson-friendly adapted GuiState object into the model's {@code GuiState} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted GuiState.
     */
    public GuiState toModelType(AlgoBase algoBase) throws IllegalValueException {
        return new GuiState(tabManager.toModelType(algoBase));
    }
}
