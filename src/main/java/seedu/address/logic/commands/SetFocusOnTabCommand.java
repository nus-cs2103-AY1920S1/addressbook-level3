//@@author SakuraBlossom
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.NonActionableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Serves the next patient in queue.
 */
public class SetFocusOnTabCommand extends NonActionableCommand {
    private static final String MESSAGE_SUCCESS = "Focusing on $1%s tab";
    private final OmniPanelTab desiredTab;

    public SetFocusOnTabCommand(OmniPanelTab desiredTab) {
        this.desiredTab = desiredTab;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (desiredTab == null) {
            return new CommandResult("");
        }

        model.setTabListing(desiredTab);
        return new CommandResult(String.format(MESSAGE_SUCCESS, desiredTab));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SetFocusOnTabCommand)) {
            return false;
        }

        SetFocusOnTabCommand o = (SetFocusOnTabCommand) other;
        if (desiredTab == null) {
            return o.desiredTab == null;
        }

        return desiredTab.equals(o.desiredTab);
    }
}
