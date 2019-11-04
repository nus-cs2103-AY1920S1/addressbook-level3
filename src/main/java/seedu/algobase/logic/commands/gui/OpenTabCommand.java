package seedu.algobase.logic.commands.gui;

import static seedu.algobase.commons.util.CollectionUtil.isWithinListRange;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_TYPE;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabCommandType;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.WriteOnlyTabManager;

/**
 * Close tabs in the GUI.
 */
public class OpenTabCommand extends Command {

    public static final String COMMAND_WORD = "opentab";
    public static final String SHORT_COMMAND_WORD = "ot";
    public static final String MESSAGE_SUCCESS = "Tab [%1$s] opened.";
    public static final String MESSAGE_SWITCH_SUCCESS = "Switched to tab [%1$s].";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": opens a new Details Tab in the GUI\n"
        + "Parameters:\n"
        + PREFIX_MODEL_TYPE + "NAME OF MODEL (problem, plan, tag) "
        + PREFIX_MODEL_INDEX + "INDEX OF MODEL (must be a positive integer)\n"
        + "Example:\n"
        + COMMAND_WORD + " "
        + PREFIX_MODEL_TYPE + "problem "
        + PREFIX_MODEL_INDEX + "1\n";

    public static final String MESSAGE_INVALID_TAB_COMMAND_TYPE = "Invalid Tab Command Type";
    public static final String MESSAGE_INVALID_MODEL = "Model [%1$s] does not exist.";
    public static final String MESSAGE_INVALID_INDEX = "Tab at index [%1$s] does not exist.";

    private Index modelIndex = Index.fromZeroBased(0);
    private ModelType modelType;

    public OpenTabCommand(ModelType modelType, Index modelIndex) {
        requireAllNonNull(modelType, modelIndex);
        this.modelType = modelType;
        this.modelIndex = modelIndex;
    }

    /**
     * Retrieves an Id for a model of modelType at a given index.
     *
     * @param model
     * @param modelType
     * @param modelIndex
     * @return
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    private Id retrieveId(Model model, ModelType modelType, Index modelIndex)
        throws IndexOutOfBoundsException, IllegalArgumentException {
        switch (modelType) {
        case PROBLEM:
            if (!isWithinListRange(modelIndex, model.getFilteredProblemList())) {
                throw new IndexOutOfBoundsException("Index does not exist within list!");
            }
            return model.getFilteredProblemList().get(modelIndex.getZeroBased()).getId();
        case PLAN:
            if (!isWithinListRange(modelIndex, model.getFilteredPlanList())) {
                throw new IndexOutOfBoundsException("Index does not exist within list!");
            }
            return model.getFilteredPlanList().get(modelIndex.getZeroBased()).getId();
        case TAG:
            if (!isWithinListRange(modelIndex, model.getFilteredTagList())) {
                throw new IndexOutOfBoundsException("Index does not exist within list!");
            }
            return model.getFilteredTagList().get(modelIndex.getZeroBased()).getId();
        default:
            throw new IllegalArgumentException("Unknown model");
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        WriteOnlyTabManager tabManager = model.getGuiState().getTabManager();
        try {
            Id modelId = retrieveId(model, modelType, modelIndex);
            TabData tabData = new TabData(modelType, modelId);
            TabCommandType result = tabManager.openDetailsTab(tabData);
            switch(result) {
            case OPEN_DETAILS:
                return new CommandResult(String.format(MESSAGE_SUCCESS, modelIndex.getOneBased()));
            case SWITCH_DETAILS:
                return new CommandResult(String.format(MESSAGE_SWITCH_SUCCESS, modelIndex.getOneBased()));
            default:
                throw new CommandException(MESSAGE_INVALID_TAB_COMMAND_TYPE);
            }
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, modelIndex.getOneBased()));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL, modelType.getTabName()));
        }
    }
}
