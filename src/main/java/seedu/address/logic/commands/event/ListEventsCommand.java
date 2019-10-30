package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.address.logic.commands.CommandGroup;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GenericCommandWord;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.ui.event.EventListPanel;

/**
 * Lists all events to the user.
 */
public class ListEventsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.EVENT + "s";
    public static final String COMMAND_DESCRIPTION = "List events";
    public static final String MESSAGE_SUCCESS = "Listed all events";

    @Override
    public String getDescription() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    protected void validate(Model model) {
        // No validation necessary.
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS, EventListPanel.PANEL_NAME);
    }
}
