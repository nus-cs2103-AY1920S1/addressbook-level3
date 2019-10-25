package seedu.ifridge.logic.commands.templatelist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ifridge.model.Model.PREDICATE_SHOW_ALL_TEMPLATES;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.ifridge.commons.core.Messages;
import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.commons.util.CollectionUtil;
import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.Name;
import seedu.ifridge.model.food.TemplateItem;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Edits the details of an existing template item in the template list.
 */
public class EditTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = "tlist " + COMMAND_WORD
            + ": Edits the name of the template identified "
            + "by the index number used in the displayed template list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + " " + PREFIX_NAME + "NAME\n"
            + "Example: tlist " + COMMAND_WORD + " 1 n/Meat ";

    public static final String MESSAGE_SUCCESS = "Edited template: %1$s";
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

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, templateToEdit));
        commandResult.setTemplateListCommand();
        return commandResult;
    }

    /**
     * Creates and returns a {@code Template} with the details of {@code templateToEdit}
     * edited with {@code editTemplateListDescriptor}.
     */
    private static UniqueTemplateItems createEditedTemplate(UniqueTemplateItems templateToEdit,
                                                            EditTemplateListDescriptor editTemplateListDescriptor) {
        assert templateToEdit != null;

        UniqueTemplateItems editedTemplate;

        Name updatedName = editTemplateListDescriptor.getName().orElse(templateToEdit.getName());
        ObservableList<TemplateItem> templateItems = templateToEdit.getTemplate();

        editedTemplate = new UniqueTemplateItems(updatedName);
        editedTemplate.setTemplateItems(templateItems);

        return editedTemplate;
    }

    /**
     * Stores the details to edit the template with. Each non-empty field value will replace the corresponding field
     * value of the template.
     */
    public static class EditTemplateListDescriptor {
        private Name name;
        private UniqueTemplateItems templateItems;

        public EditTemplateListDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTemplateListDescriptor(EditTemplateListDescriptor toCopy) {
            templateItems = new UniqueTemplateItems(toCopy.name);
            setName(toCopy.name);
            setTemplateItems(toCopy.templateItems);
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

        public void setTemplateItems(UniqueTemplateItems template) {
            if (template != null) {
                this.templateItems.setTemplateItems(template);
            }
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }
    }
}
