package seedu.algobase.logic.commands;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_INDEX;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_MODEL_TAB;

import javafx.collections.ObservableList;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelEnum;
import seedu.algobase.model.gui.AlgoBaseTab;

/**
 * Close tabs in the GUI.
 */
public class OpenTabCommand extends Command {

    public static final String COMMAND_WORD = "opentab";
    public static final String MESSAGE_SUCCESS = "opened tab %1$s!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": opens a new Details Tab in the GUI\n"
        + "Parameters:\n"
        + PREFIX_MODEL_TAB + "NAME OF MODEL (problem, plan, tag) "
        + PREFIX_MODEL_INDEX + "INDEX OF MODEL (must be a positive integer)\n"
        + "Example:\n"
        + COMMAND_WORD + " "
        + PREFIX_MODEL_TAB + "problem "
        + PREFIX_MODEL_INDEX + "1\n";

    public static final String MESSAGE_INVALID_MODEL = "There is no such model!";
    public static final String MESSAGE_INVALID_INDEX = "There is no tab at index %1$s!";

    private Index index = Index.fromZeroBased(0);
    private ModelEnum modelEnum;

    public OpenTabCommand(ModelEnum modelEnum, Index displayTabIndex) {
        requireAllNonNull(modelEnum, displayTabIndex);
        this.modelEnum = modelEnum;
        this.index = displayTabIndex;
    }

    /**
     * Checks if an observable list contains an index.
     *
     * @param index index to be checked.
     * @param items observable list of items.
     * @param <T> type of items that list contains.
     * @throws IndexOutOfBoundsException if list of items does not contain the index.
     */
    private <T> void checkIfWithinListRange(Index index, ObservableList<T> items) throws IndexOutOfBoundsException {
        int itemIndex = index.getZeroBased();
        if (itemIndex < 0 || itemIndex >= items.size()) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    /**
     * Checks if an AlgoBase Tab is valid.
     *
     * @param model The models that the tab will be checked against for validity.
     * @param algoBaseTab The tabs to be checked.
     * @throws IndexOutOfBoundsException if the tab index does not exist within the models.
     * @throws IllegalArgumentException if the model does not exist.
     */
    private void checkIfValidAlgoBaseTab(Model model, AlgoBaseTab algoBaseTab)
        throws IndexOutOfBoundsException, IllegalArgumentException {
        Index tabIndex = algoBaseTab.getModelIndex();
        switch (algoBaseTab.getModelType()) {
        case PROBLEM:
            checkIfWithinListRange(tabIndex, model.getFilteredProblemList());
            return;
        case PLAN:
            checkIfWithinListRange(tabIndex, model.getFilteredPlanList());
            return;
        case TAG:
            checkIfWithinListRange(tabIndex, model.getFilteredTagList());
            return;
        default:
            throw new IllegalArgumentException("Unknown model");
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            AlgoBaseTab algoBaseTab = new AlgoBaseTab(modelEnum, index);
            checkIfValidAlgoBaseTab(model, algoBaseTab);
            model.getGuiState().getTabManager().addTab(algoBaseTab);
            return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
        } catch (IndexOutOfBoundsException exception) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, index.getOneBased()));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(String.format(MESSAGE_INVALID_MODEL));
        }
    }
}
