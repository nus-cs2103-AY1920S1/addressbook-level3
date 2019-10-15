package seedu.address.logic.commands.templateList.template;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.Amount;
import seedu.address.model.food.Name;
import seedu.address.model.food.TemplateItem;

/**
 * Edits the details of an existing template item in the template list.
 */
public class EditTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the food item identified "
            + "by the index number used in the displayed template list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT] "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_TEMPLATE_ITEM_SUCCESS = "Edited food item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditTemplateItemDescriptor editTemplateItemDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editTemplateItemDescriptor details to edit the person with
     */
    public EditTemplateItemCommand(Index index, EditTemplateItemDescriptor editTemplateItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editTemplateItemDescriptor);

        this.index = index;
        this.editTemplateItemDescriptor = new EditTemplateItemDescriptor(editTemplateItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        /*List<TemplateItem> lastShownList = model.getFilteredTemplateItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
        }

        TemplateItem foodToEdit = lastShownList.get(index.getZeroBased());
        TemplateItem editedFood = createEditedFood(foodToEdit, editTemplateItemDescriptor);

        model.setTemplateItem(foodToEdit, editedFood);
        model.updateFilteredTemplateItemList(PREDICATE_SHOW_ALL_FOODS);
        return new CommandResult(String.format(MESSAGE_EDIT_TEMPLATE_ITEM_SUCCESS, editedFood));**/
        return new CommandResult("Method not implemented yet.");
    }

    /**
     * Creates and returns a {@code TemplateItem} with the details of {@code foodToEdit}
     * edited with {@code editTemplateItemDescriptor}.
     */
    private static TemplateItem createEditedFood(TemplateItem foodToEdit,
                                                 EditTemplateItemDescriptor editTemplateItemDescriptor) {
        assert foodToEdit != null;

        Name updatedName = editTemplateItemDescriptor.getName().orElse(foodToEdit.getName());
        Amount updatedAmount = editTemplateItemDescriptor.getAmount().orElse(foodToEdit.getAmount());

        return new TemplateItem(updatedName, updatedAmount);
    }

    /**
     * Stores the details to edit the template item with. Each non-empty field value will replace the
     * corresponding field value of the template item.
     */
    public static class EditTemplateItemDescriptor {
        private Name name;
        private Amount amount;

        public EditTemplateItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTemplateItemDescriptor(EditTemplateItemDescriptor toCopy) {
            setName(toCopy.name);
            setAmount(toCopy.amount);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, amount);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Amount> getAmount() {
            return Optional.ofNullable(amount);
        }
    }
}
