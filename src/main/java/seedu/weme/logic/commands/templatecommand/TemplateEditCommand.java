package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * Edits the details of an existing template in Weme.
 */
public class TemplateEditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_DESCRIPTION = COMMAND_WORD + ": edits the details of the template identified "
            + "by the index number used in the displayed template list.";

    public static final String MESSAGE_USAGE = MESSAGE_DESCRIPTION
            + " Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " 1 n/Drake";

    public static final String MESSAGE_EDIT_TEMPLATE_SUCCESS = "Edited Template: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "A template with the same name already exists in Weme";

    private final Index index;
    private final Name name;

    /**
     * @param index of the template in the filtered template list to edit
     * @param name the new name of the template
     */
    public TemplateEditCommand(Index index, Name name) {
        requireAllNonNull(index, name);
        this.index = index;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Template> lastShownList = model.getFilteredTemplateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        Template templateToEdit = lastShownList.get(index.getZeroBased());
        Template editedTemplate = new Template(name, templateToEdit.getImagePath(), templateToEdit.isArchived());

        if (!templateToEdit.isSameTemplate(editedTemplate) && model.hasTemplate(editedTemplate)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEMPLATE);
        }

        model.setTemplate(templateToEdit, editedTemplate);
        model.addTemplateToRecords(editedTemplate);
        CommandResult result = new CommandResult(String.format(MESSAGE_EDIT_TEMPLATE_SUCCESS, editedTemplate));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateEditCommand)) {
            return false;
        }

        // state check
        TemplateEditCommand e = (TemplateEditCommand) other;
        return index.equals(e.index)
                && name.equals(e.name);
    }
}
