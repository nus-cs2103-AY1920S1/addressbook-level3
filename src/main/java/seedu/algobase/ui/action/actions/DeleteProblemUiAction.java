package seedu.algobase.ui.action.actions;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.algobase.model.Id;
import seedu.algobase.model.Model;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.ui.UiActionException;
import seedu.algobase.ui.action.UiAction;
import seedu.algobase.ui.action.UiActionResult;

/**
 * Deletes an existing Problem in the algobase.
 */
public class DeleteProblemUiAction extends UiAction {

    public static final String MESSAGE_DELETE_PROBLEM_SUCCESS = "Problem [%1$s] deleted.";

    private final Id id;
    private final boolean isForced;

    /**
     * @param id of the Problem in the filtered Problem list to delete
     */
    public DeleteProblemUiAction(Id id, boolean isForced) {
        requireNonNull(id);

        this.id = id;
        this.isForced = isForced;
    }


    /**
     * Retrieves the problem to be deleted from the problem list.
     */
    private Problem retrieveProblemToDelete(List<Problem> problemList) throws NoSuchElementException {
        for (Problem problem : problemList) {
            if (problem.getId().equals(id)) {
                return problem;
            }
        }
        throw new NoSuchElementException("No Problem Found");
    }

    @Override
    public UiActionResult execute(Model model) throws UiActionException {
        requireNonNull(model);
        List<Problem> lastShownList = model.getFilteredProblemList();

        Problem problemToDelete = retrieveProblemToDelete(lastShownList);

        if (model.checkIsProblemUsed(problemToDelete) && isForced) {
            model.removeProblemFromAllPlans(problemToDelete);
        }
        model.deleteProblem(problemToDelete);

        return new UiActionResult(
            true,
            Optional.of(String.format(MESSAGE_DELETE_PROBLEM_SUCCESS, problemToDelete.getName()))
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteProblemUiAction)) {
            return false;
        }

        // state check
        DeleteProblemUiAction e = (DeleteProblemUiAction) other;
        return id.equals(e.id);
    }
}
