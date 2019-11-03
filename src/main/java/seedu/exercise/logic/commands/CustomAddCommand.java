package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_CUSTOM_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_FULL_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_PARAMETER_TYPE;

import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.Model;
import seedu.exercise.model.property.CustomProperty;
import seedu.exercise.model.property.PropertyBook;

/**
 * Adds a custom property for the exercises.
 */
public class CustomAddCommand extends CustomCommand {

    public static final String MESSAGE_USAGE_CUSTOM_ADD = "Parameters: "
        + PREFIX_CUSTOM_NAME + "PREFIX NAME "
        + PREFIX_FULL_NAME + "FULL NAME "
        + PREFIX_PARAMETER_TYPE + "PARAMETER TYPE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_CUSTOM_NAME + "a "
        + PREFIX_FULL_NAME + "Ratings "
        + PREFIX_PARAMETER_TYPE + "Number";

    public static final String MESSAGE_SUCCESS = "New custom property added: %1$s";
    public static final String MESSAGE_DUPLICATE_FULL_NAME = "This full name has been used for an "
        + "existing property";
    public static final String MESSAGE_DUPLICATE_PREFIX_NAME = "This prefix name has been used for an "
        + "existing parameter in add/edit command";

    private static final Logger logger = LogsCenter.getLogger(CustomAddCommand.class);
    private final CustomProperty toAdd;

    /**
     * Creates a CustomAddCommand to add the specified {@code CustomProperty}.
     */
    public CustomAddCommand(CustomProperty customProperty) {
        requireNonNull(customProperty);
        toAdd = customProperty;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String fullName = toAdd.getFullName();
        PropertyBook propertyBook = PropertyBook.getInstance();
        if (propertyBook.isFullNameUsed(fullName)) {
            logger.warning("Full name has been used by a property/parameter");
            throw new CommandException(MESSAGE_DUPLICATE_FULL_NAME);
        }

        Prefix prefix = toAdd.getPrefix();
        if (propertyBook.isPrefixUsed(prefix)) {
            logger.warning("Prefix has been used by a property/parameter");
            throw new CommandException(MESSAGE_DUPLICATE_PREFIX_NAME);
        }

        logger.info("Custom property added to model: " + fullName);
        propertyBook.addCustomProperty(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CustomAddCommand // instanceof handles nulls
            && toAdd.equals(((CustomAddCommand) other).toAdd));
    }
}
