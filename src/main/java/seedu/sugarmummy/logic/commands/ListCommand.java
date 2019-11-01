package seedu.sugarmummy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.model.Model.PREDICATE_SHOW_ALL_RECORDS;

import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Lists all records in the record list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all records";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredRecordList(PREDICATE_SHOW_ALL_RECORDS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.LIST;
    }

}
