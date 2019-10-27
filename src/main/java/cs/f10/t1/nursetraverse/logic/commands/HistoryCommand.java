package cs.f10.t1.nursetraverse.logic.commands;

import static java.util.Objects.requireNonNull;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.HistoryRecord;
import cs.f10.t1.nursetraverse.model.Model;
import javafx.collections.ObservableList;

/**
 * Displays the application data-changing command history to the user.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "app-history";

    public static final String MESSAGE_SUCCESS = "History shown.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<HistoryRecord> history = model.getHistory();
        return new CommandResult(String.format("There are %d records in the history:\n%s",
                history.size(), CollectionUtil.collectionToStringShowingIndexes(history)));
    }
}
