package seedu.ifridge.logic.commands.templatelist.template;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_ITEM_INDEX;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import java.util.List;
import java.util.Optional;

import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.commons.util.CollectionUtil;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.Food;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;
import seedu.ifridge.model.food.exceptions.InvalidUnitException;

/**
 * Edits the details of an existing template item in the specified template in the template list.
 */
public class EditTemplateItemCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = "tlist template " + COMMAND_WORD
            + ": Edits the details of the food item identified "
            + "by the index number used in the displayed template list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ITEM_INDEX + "ITEMINDEX "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AMOUNT + "AMOUNT]\n"
            + "Example: tlist template " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM_INDEX + " 1 " + PREFIX_NAME + "Whole Milk";

    public static final String MESSAGE_SUCCESS = "Food item %1$s edited.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_INCORRECT_UNIT = "This food item's unit conflicts with another food entry "
            + "with the same name";
    public static final String MESSAGE_DUPLICATE_ITEM = "This template already has another template item with "
            + "the same name";

    private final Index targetTemplateIndex;
    private final Index targetItemIndex;
    private final EditTemplateItemDescriptor editTemplateItemDescriptor;

    /**
     * @param targetTemplate of the template in the filtered template list to edit
     * @param targetItem of the template item in the template to edit
     * @param editTemplateItemDescriptor details to edit the template with
     */
    public EditTemplateItemCommand(Index targetTemplate, Index targetItem,
                                   EditTemplateItemDescriptor editTemplateItemDescriptor) {
        requireNonNull(targetItem);
        requireNonNull(targetTemplate);
        requireNonNull(editTemplateItemDescriptor);

        this.targetItemIndex = targetItem;
        this.targetTemplateIndex = targetTemplate;
        this.editTemplateItemDescriptor = new EditTemplateItemDescriptor(editTemplateItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        if (targetTemplateIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        if (editTemplateItemDescriptor.getAmount().isPresent()
                && Amount.isEmptyAmount(editTemplateItemDescriptor.amount)) {
            throw new CommandException(Amount.MESSAGE_ZERO_AMOUNT);
        }

        UniqueTemplateItems templateToEdit = lastShownList.get(targetTemplateIndex.getZeroBased());
        UniqueTemplateItems editedTemplate = new UniqueTemplateItems(templateToEdit.getName());
        editedTemplate.setTemplateItems(templateToEdit);

        if (targetItemIndex.getZeroBased() >= templateToEdit.getSize()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_ITEM_DISPLAYED_INDEX);
        }

        TemplateItem itemToEdit = templateToEdit.get(targetItemIndex.getZeroBased());
        TemplateItem editedItem = createEditedItem(itemToEdit, editTemplateItemDescriptor);

        if (!editTemplateItemDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
        if (templateToEdit.contains(editedItem) && editTemplateItemDescriptor.isNameFieldEdited(itemToEdit)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        UnitDictionary unitDictionary = model.getUnitDictionary();
        try {
            unitDictionary.checkUnitDictionary(editedItem, model);
        } catch (InvalidUnitException e) {
            throw new CommandException(MESSAGE_INCORRECT_UNIT);
        }

        editedTemplate.setTemplateItem(itemToEdit, editedItem);

        model.setTemplate(templateToEdit, editedTemplate);
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        model.setShownTemplate(editedTemplate);
        model.updateFilteredTemplateToBeShown();
        model.commitTemplateList(templateToEdit, editedTemplate, targetTemplateIndex.getZeroBased());

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, itemToEdit, editedItem));
        commandResult.setTemplateListItemCommand();

        return commandResult;
    }

    /**
     * Creates and returns a {@code TemplateItem} with the details of {@code itemToEdit}
     * edited with {@code editTemplateItemDescriptor}.
     */
    private static TemplateItem createEditedItem(TemplateItem itemToEdit,
                                                 EditTemplateItemDescriptor editTemplateItemDescriptor) {
        assert itemToEdit != null;

        Name updatedName = editTemplateItemDescriptor.getName().orElse(itemToEdit.getName());
        Amount updatedAmount = editTemplateItemDescriptor.getAmount().orElse(itemToEdit.getAmount());

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

        /**
         * Returns true if the name field has been edited
         */
        public boolean isNameFieldEdited(TemplateItem itemToEdit) {
            if (CollectionUtil.isAnyNonNull(name)) {
                return !itemToEdit.isSameName(new Food(name, itemToEdit.getAmount()));
            }

            return false;
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

        /**
         * Checks if the object is identical
         */
        public boolean equals(EditTemplateItemDescriptor other) {
            if (other == this) {
                return true;
            }

            if (other instanceof EditTemplateItemDescriptor) {
                if (this.name == null) {
                    return this.amount.equals(other.amount);
                } else if (this.amount == null) {
                    return this.name.equals(other.name);
                } else {
                    return this.name.equals(other.name) && this.amount.equals(other.amount);
                }
            }
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EditTemplateItemCommand
                && this.targetTemplateIndex.equals(((EditTemplateItemCommand) other).targetTemplateIndex)
                && this.targetItemIndex.equals(((EditTemplateItemCommand) other).targetItemIndex)
                && this.editTemplateItemDescriptor
                    .equals(((EditTemplateItemCommand) other).editTemplateItemDescriptor));
    }
}
