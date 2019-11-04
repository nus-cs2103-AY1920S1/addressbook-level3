package seedu.moolah.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import seedu.moolah.logic.commands.Command;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.model.Model;
import seedu.moolah.ui.event.EventListPanel;

/**
 * Lists all events to the user.
 */
public class ListEventsCommand extends Command {

    public static final String COMMAND_WORD = GenericCommandWord.LIST + CommandGroup.EVENT;
    public static final String COMMAND_DESCRIPTION = "List events";
    public static final String MESSAGE_SUCCESS = "Listed all events";

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
