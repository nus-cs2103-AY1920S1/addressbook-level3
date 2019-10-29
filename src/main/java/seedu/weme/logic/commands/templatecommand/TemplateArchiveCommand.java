package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.Model.PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES;

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
 * Archives a template in the display window.
 */
public class TemplateArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archive a template by index."
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_ARCHIVE_TEMPLATE_SUCCESS = "Archived Template: %1$s";
    public static final String MESSAGE_ALREADY_ARCHIVED = "This template is already archived!";

    private final Index index;

    /**
     * @param index of the template in the filtered template list to archive
     */
    public TemplateArchiveCommand(Index index) {
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

        Template templateToArchive = lastShownList.get(index.getZeroBased());

        if (templateToArchive.isArchived()) {
            throw new CommandException(MESSAGE_ALREADY_ARCHIVED);
        }

        Template archivedTemplate = createArchivedTemplate(templateToArchive);

        model.setTemplate(templateToArchive, archivedTemplate);
        CommandResult result = new CommandResult(String.format(MESSAGE_ARCHIVE_TEMPLATE_SUCCESS, templateToArchive));
        model.commitWeme(result.getFeedbackToUser());
        model.updateFilteredTemplateList(PREDICATE_SHOW_ALL_UNARCHIVED_TEMPLATES);

        return result;
    }

    /**
     * Creates an archived Template using the input unarchived Template.
     */
    private Template createArchivedTemplate(Template template) {
        assert template != null;

        Name name = template.getName();
        ImagePath imagePath = template.getImagePath();
        return new Template(name, imagePath, true);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TemplateArchiveCommand)) {
            return false;
        }

        // state check
        TemplateArchiveCommand e = (TemplateArchiveCommand) other;
        return index.equals(e.index);
    }

}
