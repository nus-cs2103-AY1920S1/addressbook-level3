package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * Likes a template in the display window.
 */
public class TemplateUnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchive a template by index."
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_UNARCHIVE_TEMPLATE_SUCCESS = "Unarchived Template: %1$s";
    public static final String MESSAGE_ALREADY_UNARCHIVED = "This template is already unarchived!";

    private final Index index;

    /**
     * @param index of the template in the filtered template list to unarchive
     */
    public TemplateUnarchiveCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Template> lastShownList = model.getFilteredTemplateList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEMPLATE_DISPLAYED_INDEX);
        }

        Template templateToUnarchive = lastShownList.get(index.getZeroBased());

        if (!templateToUnarchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_UNARCHIVED);
        }

        Template unarchivedTemplate = createUnarchivedTemplate(templateToUnarchive);

        model.setTemplate(templateToUnarchive, unarchivedTemplate);
        CommandResult result = new CommandResult(String.format(MESSAGE_UNARCHIVE_TEMPLATE_SUCCESS,
                templateToUnarchive));
        model.commitWeme(result.getFeedbackToUser());
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_ARCHIVED_TEMPLATES);

        return result;
    }

    /**
     * Creates an unarchived Template using the input archived Template.
     */
    private Template createUnarchivedTemplate(Template template) {
        assert template != null;

        Name name = template.getName();
        ImagePath imagePath = template.getImagePath();
        return new Template(name, imagePath, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateUnarchiveCommand)) {
            return false;
        }

        // state check
        TemplateUnarchiveCommand e = (TemplateUnarchiveCommand) other;
        return index.equals(e.index);
    }

}
