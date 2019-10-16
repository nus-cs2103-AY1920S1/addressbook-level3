package seedu.address.logic.commands.templateList;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.food.Name;
import seedu.address.model.food.UniqueTemplateItems;

/**
 * Edits the details of an existing template item in the template list.
 */
public class EditTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the name of the template identified "
            + "by the index number used in the displayed template list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + " " + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " 1 n/Meat ";

    public static final String MESSAGE_EDIT_TEMPLATE_SUCCESS = "Edited template: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Name field must be provided.";

    private final Index index;
    private final EditTemplateListDescriptor editTemplateListDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editTemplateListDescriptor details to edit the person with
     */
    public EditTemplateListCommand(Index index, EditTemplateListDescriptor editTemplateListDescriptor) {
        requireNonNull(index);
        requireNonNull(editTemplateListDescriptor);

        this.index = index;
        this.editTemplateListDescriptor = new EditTemplateListDescriptor(editTemplateListDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        UniqueTemplateItems templateToEdit = lastShownList.get(index.getZeroBased());
        UniqueTemplateItems editedTemplate = createEditedTemplate(templateToEdit, editTemplateListDescriptor);

        model.setTemplate(templateToEdit, editedTemplate);
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
        return new CommandResult(String.format(MESSAGE_EDIT_TEMPLATE_SUCCESS, editedTemplate));
    }

    /**
     * Creates and returns a {@code Template} with the details of {@code templateToEdit}
     * edited with {@code editTemplateListDescriptor}.
     */
    private static UniqueTemplateItems createEditedTemplate(UniqueTemplateItems templateToEdit,
                                                            EditTemplateListDescriptor editTemplateListDescriptor) {
        assert templateToEdit != null;
        Name updatedName = editTemplateListDescriptor.getName().orElse(templateToEdit.getName());

        return new UniqueTemplateItems(updatedName);
    }

    /**
     * Stores the details to edit the template with. Each non-empty field value will replace the corresponding field
     * value of the template.
     */
    public static class EditTemplateListDescriptor {
        private Name name;

        public EditTemplateListDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTemplateListDescriptor(EditTemplateListDescriptor toCopy) {
            setName(toCopy.name);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }
    }
}
