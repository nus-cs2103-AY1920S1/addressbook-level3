package seedu.algobase.ui.action.actions;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

import java.util.NoSuchElementException;
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
 * Opens a tab in the GUI.
 */
public class OpenDetailsTabUiAction extends UiAction {

    public static final String MESSAGE_INVALID_MODEL = "Model does not exist.";
    public static final String MESSAGE_INVALID_ELEMENT = "No such tab exists!";

    private Id modelId;
    private ModelType modelType;

    public OpenDetailsTabUiAction(ModelType modelType, Id modelId) {
        requireAllNonNull(modelType, modelId);
        this.modelType = modelType;
        this.modelId = modelId;
    }

    /**
     * Checks if a model item exists within a {@code Model}.
     */
    public boolean isModelItemPresent(Model model) {
        switch (modelType) {
        case PROBLEM:
            return model.getFilteredProblemList()
                .stream()
                .map(problem -> problem.getId())
                .anyMatch((id) -> id.equals(modelId));
        case PLAN:
            return model.getFilteredPlanList()
                .stream()
                .map(plan -> plan.getId())
                .anyMatch((id) -> id.equals(modelId));
        default:
            return false;
        }
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        WriteOnlyTabManager tabManager = model.getGuiState().getTabManager();
        try {
            if (!isModelItemPresent(model)) {
                throw new NoSuchElementException();
            }
            TabData tabData = new TabData(modelType, modelId);
            TabCommandType result = tabManager.openDetailsTab(tabData);
            return new UiActionResult(true, Optional.empty());
        } catch (NoSuchElementException exception) {
            throw new UiActionException(String.format(MESSAGE_INVALID_ELEMENT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL, modelType.getTabName()));
        }
    }
}
