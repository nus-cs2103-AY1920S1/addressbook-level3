package seedu.algobase.ui.action.actions;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabCommandType;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.WriteOnlyTabManager;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Close tabs in the GUI.
 */
public class OpenTabUiAction extends UiAction {

    public static final String MESSAGE_INVALID_MODEL = "Model [%1$s] does not exist.";
    public static final String MESSAGE_INVALID_INDEX = "Tab at index [%1$s] does not exist.";

    private Id modelId;
    private ModelType modelType;

    public OpenTabUiAction(ModelType modelType, Id modelId) {
        requireAllNonNull(modelType, modelId);
        this.modelType = modelType;
        this.modelId = modelId;
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        WriteOnlyTabManager tabManager = model.getGuiState().getTabManager();
        try {
            TabData tabData = new TabData(modelType, modelId);
            TabCommandType result = tabManager.openDetailsTab(tabData);
            return new UiActionResult(Optional.empty());
        } catch (IndexOutOfBoundsException exception) {
            throw new UiActionException(String.format(MESSAGE_INVALID_INDEX));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL, modelType.getTabName()));
        }
    }
}
