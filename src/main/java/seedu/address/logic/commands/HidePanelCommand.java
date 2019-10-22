package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import seedu.address.model.Model;
import seedu.address.model.person.PanelName;

/**
 * Lists all persons in the address book to the user.
 */
public class HidePanelCommand extends Command {

    public static final String COMMAND_WORD = "hide";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Hides the specified panel. "
            + "PARAMETERS: wishlist, budget or reminder. "
            + "EXAMPLE: " + COMMAND_WORD + " wishlist";
    public static final String MESSAGE_SUCCESS = "Hid %1$s panel";

    private final PanelName panelName;

    public HidePanelCommand(PanelName panelName) {
        this.panelName = panelName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, panelName), panelName, true);
    }
}
