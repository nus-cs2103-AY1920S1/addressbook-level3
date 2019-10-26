package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.date.AthletickDate;

/**
 * Adds a training session of players specified by the index.
 */
public abstract class TrainingCommand extends Command {

    public static final String COMMAND_WORD = "training";

    public static final String TRAINING_ADD_SUCCESS = "Training successfully added.";

    private AthletickDate date;
    private List<Index> indexList;

    public TrainingCommand(AthletickDate date, List<Index> indexList) {
        this.date = date;
        this.indexList = indexList;
    }

    public AthletickDate getDate() {
        return date;
    }

    public List<Index> getIndexList() {
        return indexList;
    }

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public boolean isUndoable() {
        return true;
    }
}
