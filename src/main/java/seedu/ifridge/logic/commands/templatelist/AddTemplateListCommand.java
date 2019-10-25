package seedu.ifridge.logic.commands.templatelist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.CommandResult;
import seedu.ifridge.logic.commands.exceptions.CommandException;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.food.UniqueTemplateItems;

/**
 * Adds a template to the template list.
 */
public class AddTemplateListCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new template to the template list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Weekly Necessities ";

    public static final String MESSAGE_SUCCESS = "New template added into template: %1$s";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "This template already exists in the template list";

    private final UniqueTemplateItems toAdd;

    /**
     * Creates an AddTemplateListCommand to add the specified {@code TemplateList List}
     */
    public AddTemplateListCommand(UniqueTemplateItems template) {
        requireNonNull(template);
        toAdd = template;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTemplate(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEMPLATE);
        }

        model.addTemplate(toAdd);

        CommandResult commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        commandResult.setTemplateListCommand();

        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateListCommand // instanceof handles nulls
                && toAdd.equals(((AddTemplateListCommand) other).toAdd));
    }
}
