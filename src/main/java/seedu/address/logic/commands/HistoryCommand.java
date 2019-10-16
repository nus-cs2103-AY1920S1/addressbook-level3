package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.collectionToString;

import javafx.collections.ObservableList;
import seedu.address.model.HistoryRecord;
import seedu.address.model.Model;

/**
 * Displays the application data-changing command history to the user.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    public static final String MESSAGE_SUCCESS = "History shown.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<HistoryRecord> history = model.getHistory();
        return new CommandResult(String.format("There are %d records in the history:\n%s",
                history.size(), collectionToString(history)));
    }
}
