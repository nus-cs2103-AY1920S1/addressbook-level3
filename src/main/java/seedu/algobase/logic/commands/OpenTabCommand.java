package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.CollectionUtil.isWithinListRange;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_TYPE;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabData;

/**
 * Close tabs in the GUI.
 */
public class OpenTabCommand extends Command {

    public static final String COMMAND_WORD = "opentab";
    public static final String MESSAGE_SUCCESS = "opened tab %1$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": opens a new Details Tab in the GUI\n"
        + "Parameters:\n"
        + PREFIX_MODEL_TYPE + "NAME OF MODEL (problem, plan, tag) "
        + PREFIX_MODEL_INDEX + "INDEX OF MODEL (must be a positive integer)\n"
        + "Example:\n"
        + COMMAND_WORD + " "
        + PREFIX_MODEL_TYPE + "problem "
        + PREFIX_MODEL_INDEX + "1\n";

    public static final String MESSAGE_INVALID_MODEL = "There is no such model!";
    public static final String MESSAGE_INVALID_INDEX = "There is no tab at index %1$s!";

    private Index index = Index.fromZeroBased(0);
    private ModelType modelType;

    public OpenTabCommand(ModelType modelType, Index displayTabIndex) {
        requireAllNonNull(modelType, displayTabIndex);
        this.modelType = modelType;
        this.index = displayTabIndex;
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
    public CommandResult execute(Model model) throws CommandException {
        try {
            Id modelId = retrieveId(model, modelType, index);
            TabData tabData = new TabData(modelType, modelId);
            model.getGuiState().getTabManager().addTab(tabData);
            return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL));
        }
    }
}
