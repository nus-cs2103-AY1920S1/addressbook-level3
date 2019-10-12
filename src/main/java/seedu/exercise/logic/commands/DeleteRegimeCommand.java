package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.UniqueExerciseList;
import seedu.exercise.model.regime.Regime;
import seedu.exercise.model.regime.RegimeName;

/**
 * Deletes a regime identified using it's name or deletes exercises in regime.
 */
public class DeleteRegimeCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_REGIME_SUCCESS = "Deleted Regime: %1$s";
    private static final String MESSAGE_REGIME_DOES_NOT_EXIST = "No such regime in regime book.";
    private static final String MESSAGE_DELETE_EXERCISE_IN_REGIME_SUCCESS = "Deleted exercises in regime.";

    private final List<Index> indexes;
    private final RegimeName name;

    public DeleteRegimeCommand(RegimeName name, List<Index> indexes) {
        this.name = name;
        this.indexes = indexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Regime> lastShownList = model.getFilteredRegimeList();
        Regime regime = new Regime(name, new UniqueExerciseList());

        if (!model.hasRegime(regime)) {
            throw new CommandException(MESSAGE_REGIME_DOES_NOT_EXIST);
        }

        int indexOfRegime = model.getRegimeIndex(regime);
        Regime regimeToDelete = lastShownList.get(indexOfRegime);

        //no index provided, delete regime
        if (indexes == null) {

            model.deleteRegime(regimeToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_REGIME_SUCCESS, regimeToDelete));

        } else { //index provided, delete exercise in regime

            List<Exercise> currentExerciseList = regimeToDelete.getExercises().asUnmodifiableObservableList();
            //check all index valid
            for (Index targetIndex : indexes) {
                if (targetIndex.getZeroBased() >= currentExerciseList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
                }
            }

            // delete exercise identified by index
            for (Index targetIndex : indexes) {
                Exercise exerciseToDelete = currentExerciseList.get(targetIndex.getZeroBased());
                regimeToDelete.deleteExercise(exerciseToDelete);
            }

            model.setRegime(regime, regimeToDelete);
            model.updateFilteredRegimeList(Model.PREDICATE_SHOW_ALL_REGIMES);
            return new CommandResult(String.format(MESSAGE_DELETE_EXERCISE_IN_REGIME_SUCCESS, regimeToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRegimeCommand // instanceof handles nulls
                && indexes.equals(((DeleteRegimeCommand) other).indexes)); // state check
    }
}
