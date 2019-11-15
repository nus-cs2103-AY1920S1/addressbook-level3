package seedu.pluswork.logic.commands.inventory;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_INDEX;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.mapping.InvMemMapping;
import seedu.pluswork.model.mapping.InvTasMapping;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.Task;

//import seedu.address.commons.core.index.InvIndex;

/**
 * Deletes a inventory identified using it's displayed index from the address book.
 */
public class DeleteInventoryCommand extends Command {
    public static final String COMMAND_WORD = "delete-inv";
    public static final String PREFIX_USAGE = PREFIX_INVENTORY_INDEX.getPrefix();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the inventory identified by the index number used in the displayed task list.\n"
            + "Parameters:" + PREFIX_INVENTORY_INDEX + "INVENTORY INDEX \n"
            + "Example: " + COMMAND_WORD + "ii/1";

    public static final String MESSAGE_DELETE_INVENTORY_SUCCESS = "Deleted Inventory: %1$s";

    private final Index targetIndex;

    public DeleteInventoryCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownTaskList = model.getFilteredTasksList();
        List<Member> lastShownMemberList = model.getFilteredMembersList();
        ObservableList<Inventory> lastShownList = model.getFilteredInventoriesList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INVENTORY_DISPLAYED_INDEX);
        }

        Inventory inventoryToDelete = lastShownList.get(targetIndex.getZeroBased());
        //Mappings Section start
        for (int i = 0; i < lastShownTaskList.size(); i++) {
            InvTasMapping mapping = new InvTasMapping(i, targetIndex.getZeroBased());
            if (model.hasMapping(mapping)) {
                model.deleteMapping(mapping);
            }
        }
        for (int j = 0; j < lastShownMemberList.size(); j++) {
            InvMemMapping mapping = new InvMemMapping(j, targetIndex.getZeroBased());
            if (model.hasMapping(mapping)) {
                model.deleteMapping(mapping);
            }
        }
        //Mappings section ends

        model.deleteInventory(inventoryToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_INVENTORY_SUCCESS, inventoryToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteInventoryCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteInventoryCommand) other).targetIndex)); // state check
    }
}
