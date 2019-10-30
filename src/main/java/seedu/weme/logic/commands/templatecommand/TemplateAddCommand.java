package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_NAME;

import java.io.IOException;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.template.Template;
import seedu.weme.model.util.ImageUtil;

/**
 * Adds a template to Weme.
 */
public class TemplateAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a template to Weme.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_FILEPATH + "PATH\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "Drake Reaction "
        + PREFIX_FILEPATH + "C:\\Users\\username\\Downloads\\drake_template.jpg";

    public static final String MESSAGE_SUCCESS = "New template added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "A template with the same name already exists in Weme";
    public static final String MESSAGE_COPY_FAILURE =
        "Error encountered while copying the template image to data folder";

    private final Template toAdd;

    /**
     * Creates an TemplateAddCommand to add the specified {@code Template}
     */
    public TemplateAddCommand(Template template) {
        requireNonNull(template);
        toAdd = template;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Copy the template image to internal data directory
        Template copiedTemplate;
        try {
            copiedTemplate = ImageUtil.copyTemplate(toAdd, model.getTemplateImagePath());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_COPY_FAILURE);
        }

        if (model.hasTemplate(copiedTemplate)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEMPLATE);
        }

        model.addTemplate(copiedTemplate);
        model.addTemplateToRecords(copiedTemplate);
        CommandResult result = new CommandResult(String.format(MESSAGE_SUCCESS, copiedTemplate));
        model.commitWeme(result.getFeedbackToUser());

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TemplateAddCommand // instanceof handles nulls
            && toAdd.equals(((TemplateAddCommand) other).toAdd));
    }
}

