package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.exercise.ui.ListResourceType.EXERCISE;
import static seedu.exercise.ui.ListResourceType.REGIME;
import static seedu.exercise.ui.ListResourceType.SCHEDULE;

import java.util.List;
import java.util.Optional;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.commons.core.index.IndexUtil;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.ui.ListResourceType;

/**
 * Selects the resource at the given index of the desired resource list.
 */
public class SelectCommand extends Command {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Selects the exercise/regime/schedule/suggestion"
        + " identified" + " by the index number in their respective list.\n"
        + "Parameters: "
        + PREFIX_CATEGORY + "LIST_TYPE " + PREFIX_INDEX + "INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_CATEGORY + "exercise "
        + PREFIX_INDEX + "1";
    public static final String MESSAGE_SUCCESS = "Selected %1$s %2$s";

    private final Index index;
    private final ListResourceType listResourceType;

    public SelectCommand(Index index, ListResourceType listResourceType) {
        requireAllNonNull(index, listResourceType);
        this.index = index;
        this.listResourceType = listResourceType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (listResourceType == EXERCISE) {
            checkValidExerciseIndex(model);
        } else if (listResourceType == REGIME) {
            checkValidRegimeIndex(model);
        } else if (listResourceType == SCHEDULE) {
            checkValidScheduleIndex(model);
        } else {
            checkValidSuggestionIndex(model);
        }

        String selectedResource = listResourceType.toString().toLowerCase();
        int selectedIndex = index.getOneBased();
        return new CommandResult(String.format(MESSAGE_SUCCESS, selectedResource, selectedIndex), listResourceType,
            Optional.of(index));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SelectCommand)) {
            return false;
        }

        SelectCommand selectCommand = (SelectCommand) other;
        return index.equals(selectCommand.index)
            && listResourceType.equals(selectCommand.listResourceType);
    }

    /**
     * Checks if the given index is within the range of the exercise list in the model.
     *
     * @throws CommandException if the given index is out of range in the exercise list
     */
    private void checkValidExerciseIndex(Model model) throws CommandException {
        List<Exercise> exerciseList = model.getSortedExerciseList();
        if (IndexUtil.isIndexOutOfBounds(index, exerciseList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if the given index is within the range of the regime list in the model.
     *
     * @throws CommandException if the given index is out of range in the regime list
     */
    private void checkValidRegimeIndex(Model model) throws CommandException {
        List<Regime> regimeList = model.getSortedRegimeList();
        if (IndexUtil.isIndexOutOfBounds(index, regimeList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_REGIME_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if the given index is within the range of the schedule list in the model.
     *
     * @throws CommandException if the given index is out of range in the schedule list
     */
    private void checkValidScheduleIndex(Model model) throws CommandException {
        List<Schedule> scheduleList = model.getSortedScheduleList();
        if (IndexUtil.isIndexOutOfBounds(index, scheduleList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }
    }

    /**
     * Checks if the given index is within the range of the suggestion list in the model.
     *
     * @throws CommandException if the given index is out of range in the suggestion list
     */
    private void checkValidSuggestionIndex(Model model) throws CommandException {
        List<Exercise> suggestionList = model.getSuggestedExerciseList();
        if (IndexUtil.isIndexOutOfBounds(index, suggestionList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUGGESTION_DISPLAYED_INDEX);
        }
    }

}
