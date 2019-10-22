package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.CollectionUtil.isWithinListRange;
import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_TYPE;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
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
     * Checks if an AlgoBase Tab is valid.
     *
     * @param model The models that the tab will be checked against for validity.
     * @param tabData The tabs to be checked.
     * @throws IndexOutOfBoundsException if the tab index does not exist within the models.
     * @throws IllegalArgumentException if the model does not exist.
     */
    private boolean isValidTabData(Model model, TabData tabData)
        throws IndexOutOfBoundsException, IllegalArgumentException {
        Index tabIndex = tabData.getModelIndex();
        switch (tabData.getModelType()) {
        case PROBLEM:
            if (!isWithinListRange(tabIndex, model.getFilteredProblemList())) {
                throw new IndexOutOfBoundsException("Index does not exist within list!");
            }
            return true;
        case PLAN:
            if (!isWithinListRange(tabIndex, model.getFilteredPlanList())) {
                throw new IndexOutOfBoundsException("Index does not exist within list!");
            }
            return true;
        case TAG:
            if (!isWithinListRange(tabIndex, model.getFilteredTagList())) {
                throw new IndexOutOfBoundsException("Index does not exist within list!");
            }
            return true;
        default:
            throw new IllegalArgumentException("Unknown model");
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            TabData tabData = new TabData(modelType, index);
            isValidTabData(model, tabData);
            model.getGuiState().getTabManager().addTab(tabData);
            return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL));
        }
    }
}
