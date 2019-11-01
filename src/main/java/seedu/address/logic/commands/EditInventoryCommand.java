package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INVENTORIES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.inventory.InvName;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.inventory.Price;
import seedu.address.model.mapping.InvMemMapping;
import seedu.address.model.mapping.InvTasMapping;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing inventory in the Dashboard.
 */
public class EditInventoryCommand extends Command{

    public static final String COMMAND_WORD = "edit-inv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the inventory identified "
            + "by the index number used in the displayed inventory list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:" + PREFIX_INVENTORY_INDEX + "INVENTORY INDEX "
            + PREFIX_INVENTORY_NAME + "NAME "
            + PREFIX_INVENTORY_PRICE + "PRICE "
            + PREFIX_TASK_INDEX + "INDEX "
            + PREFIX_MEMBER_ID + "ID "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_INVENTORY_SUCCESS = "Edited Inventory: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INVENTORY = "This Inventory already exists in the DashBoard.";

    private final Index index;
    private final EditInventoryCommand.EditInventoryDescriptor editInventoryDescriptor;

    /**
     * @param index of the inventory in the filtered inventory list to edit
     * @param editInventoryDescriptor details to edit the task with
     */
    public EditInventoryCommand(Index index, EditInventoryCommand.EditInventoryDescriptor editInventoryDescriptor) {
        requireNonNull(index);
        requireNonNull(editInventoryDescriptor);

        this.index = index;
        this.editInventoryDescriptor = new EditInventoryCommand.EditInventoryDescriptor(editInventoryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownTaskList = model.getFilteredTasksList();
        List<Member> lastShownMemberList = model.getFilteredMembersList();
        List<Inventory> lastShownList = model.getFilteredInventoriesList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INVENTORY_DISPLAYED_INDEX);
        }

        Inventory inventoryToEdit = lastShownList.get(index.getZeroBased());

        Inventory editedInventory = createEditedInventory(inventoryToEdit, editInventoryDescriptor);

        //Mappings Section start
        if(editInventoryDescriptor.getTaskId().isPresent()) {
            for (int i = 0; i < lastShownTaskList.size(); i++) {
                InvTasMapping mapping = new InvTasMapping(i, index.getZeroBased());
                if (model.hasMapping(mapping)) {
                    model.deleteMapping(mapping);
                }
            }
            InvTasMapping newMapping = new InvTasMapping(editInventoryDescriptor.getTaskId().get().getZeroBased(),
                                                            index.getZeroBased());
            model.addMapping(newMapping);
        }
        if(editInventoryDescriptor.getMemberId().isPresent()) {
            for (int j = 0; j < lastShownMemberList.size(); j++) {
                InvMemMapping mapping = new InvMemMapping(j, index.getZeroBased());
                if (model.hasMapping(mapping)) {
                    model.deleteMapping(mapping);
                }
            }
            Integer memberIndex = null;
            for (int i = 0; i < lastShownMemberList.size(); i++) {
                if (lastShownMemberList.get(i).getId().equals(editInventoryDescriptor.getMemberId().get())) {
                    memberIndex = i;
                    break;
                }
            }
            InvMemMapping newMapping = new InvMemMapping(memberIndex, index.getZeroBased());
            model.addMapping(newMapping);
        }
        //Mappings section ends

        if (!inventoryToEdit.isSameInventory(editedInventory) && model.hasInventory(editedInventory)) {
            throw new CommandException(MESSAGE_DUPLICATE_INVENTORY);
        }

        model.setInventory(inventoryToEdit, editedInventory);
        //model.updateFilteredInventoriesList(PREDICATE_SHOW_ALL_INVENTORIES);
        //model.replaceExistingMappingsWithNewInventory(taskToEdit, editedTask);
        return new CommandResult(String.format(MESSAGE_EDIT_INVENTORY_SUCCESS, editedInventory));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     */
    private static Inventory createEditedInventory(Inventory inventoryToEdit,
                                                   EditInventoryCommand.EditInventoryDescriptor editInventoryDescriptor) {
        assert inventoryToEdit != null;

        InvName updatedName = editInventoryDescriptor.getName().orElse(inventoryToEdit.getName());
        Price updatedPrice = editInventoryDescriptor.getPrice().orElse(inventoryToEdit.getPrice());


        return new Inventory(updatedName, updatedPrice);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditInventoryCommand e = (EditInventoryCommand) other;
        return index.getZeroBased() == (e.index.getZeroBased())
                && editInventoryDescriptor.equals(e.editInventoryDescriptor);
    }

    /**
     * Stores the details to edit the inventory with. Each non-empty field value will replace the
     * corresponding field value of the inventory.
     */
    public static class EditInventoryDescriptor {
        private InvName name;
        private Price price;
        private Index taskId;
        private MemberId memId;

        public EditInventoryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditInventoryDescriptor(EditInventoryCommand.EditInventoryDescriptor toCopy) {
            setName(toCopy.name);
            setPrice(toCopy.price);
            setTaskId(toCopy.taskId);
            setMemId(toCopy.memId);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, price, taskId, memId);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setName(InvName name) {
            this.name = name;
        }

        public Optional<InvName> getName() {
            return Optional.ofNullable(name);
        }

        public void setTaskId(Index taskId) {
            this.taskId = taskId;
        }

        public Optional<Index> getTaskId() {
            return Optional.ofNullable(taskId);
        }

        public void setMemId(MemberId memId) {
            this.memId = memId;
        }

        public Optional<MemberId> getMemberId() {
            return Optional.ofNullable(memId);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditInventoryCommand.EditInventoryDescriptor)) {
                return false;
            }

            // state check
            EditInventoryCommand.EditInventoryDescriptor e = (EditInventoryCommand.EditInventoryDescriptor) other;

            return getName().get().equals(e.getName().get())
                    && (getPrice().get().equals(e.getPrice().get()));
        }
    }
}
