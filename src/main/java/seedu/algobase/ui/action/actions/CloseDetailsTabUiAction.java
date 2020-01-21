package seedu.algobase.ui.action.actions;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.WriteOnlyTabManager;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Closes a tab in the GUI.
 */
public class CloseDetailsTabUiAction extends UiAction {

    public static final String MESSAGE_INVALID_MODEL = "Model does not exist.";
    public static final String MESSAGE_INVALID_ELEMENT = "No such tab exists!";

    private Id modelId;
    private ModelType modelType;

    public CloseDetailsTabUiAction(ModelType modelType, Id modelId) {
        requireAllNonNull(modelType, modelId);
        this.modelType = modelType;
        this.modelId = modelId;
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        try {
            WriteOnlyTabManager tabManager = model.getGuiState().getTabManager();
            tabManager.closeDetailsTab(new TabData(modelType, modelId));
            return new UiActionResult(true, Optional.empty());
        } catch (NoSuchElementException exception) {
            throw new UiActionException(String.format(MESSAGE_INVALID_ELEMENT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL, modelType.getTabName()));
        }
    }
}
