package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;

/**
 * Adds a training session of players specified by the indexes on the specified date.
 */
public abstract class TrainingCommand extends Command {

    public static final String COMMAND_WORD = "training";
    public static final String TRAINING_ADD_SUCCESS = "Training successfully added on %s.";
    public static final String TRAINING_REPLACE_SUCCESS = "Training successfully replaced on %s.";

    private AthletickDate date;
    private List<Index> indexList;

    /**
     * Creates a TrainingCommand to add a training session on {@code date} using the {@code indexList}.
     * @param date Date of training.
     * @param indexList List on indexes used to indicate people who were present/absent.
     */
    public TrainingCommand(AthletickDate date, List<Index> indexList) {
        requireNonNull(date);
        requireNonNull(indexList);
        this.date = date;
        this.indexList = indexList;
    }

    /**
     * Getter method of date
     * @return Date used to create Training.
     */
    public AthletickDate getDate() {
        return date;
    }

    /**
     * Getter method of indexList
     * @return List of Index used to create Training.
     */
    public List<Index> getIndexList() {
        return indexList;
    }

    /**
     * Executes the TrainingCommand which adds a training to the TrainingManger in model.
     * @param model {@code Model} where Training is saved.
     * @return Outcome of executed command.
     * @throws CommandException Thrown when specified indexes are invalid.
     */
    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Indicates whether a TrainingCommand can be undone.
     * @return Returns true as a TrainingCommand is undoable.
     */
    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public String toString() {
        return "Add Training Command";
    }
    /**
     * Checks with the model if person indexes are valid.
     */
    protected static void checkIndexesValid(List<Index> indexes, Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }
}
