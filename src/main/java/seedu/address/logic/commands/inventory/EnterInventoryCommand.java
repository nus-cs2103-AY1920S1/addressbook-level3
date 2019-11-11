package seedu.address.logic.commands.inventory;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * This command class's execute function is called whenever the InventoryPage is entered
 */
public class EnterInventoryCommand extends Command {

    public static final String COMMAND_WORD = "inventory";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the inventory page";

    public static final String MESSAGE_SUCCESS = "entering the inventory page IS A SUCCESS";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        model.setPageStatus(
                model.getPageStatus().withNewPageType(PageType.PRETRIP_INVENTORY));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterInventoryCommand;
    }
}
