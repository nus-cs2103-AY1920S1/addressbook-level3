package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_FULL_NAME;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_PARAMETER_TYPE;
import static seedu.exercise.logic.parser.CliSyntax.PREFIX_SHORT_NAME;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.Model;
import seedu.exercise.model.exercise.CustomProperty;

/**
 * Adds a custom property for the exercises.
 */
public class CustomCommand extends Command {

    public static final String COMMAND_WORD = "custom";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a custom property for the exercises.\n"
        + "Parameters: "
        + PREFIX_SHORT_NAME + "SHORT NAME "
        + PREFIX_FULL_NAME + "FULL NAME "
        + PREFIX_PARAMETER_TYPE + "PARAMETER TYPE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_SHORT_NAME + "a "
        + PREFIX_FULL_NAME + "Ratings "
        + PREFIX_PARAMETER_TYPE + "Number";

    public static final String MESSAGE_SUCCESS = "New custom property added: %1$s";
    public static final String MESSAGE_DUPLICATE_FULL_NAME = "This full name has been used for an "
        + "existing property";
    public static final String MESSAGE_DUPLICATE_SHORT_NAME = "This short name has been used for an "
        + "existing property";

    private final CustomProperty toAdd;

    /**
     * Creates a CustomCommand to add the specified {@code CustomProperty}.
     */
    public CustomCommand(CustomProperty customProperty) {
        requireNonNull(customProperty);
        toAdd = customProperty;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String fullName = toAdd.getFullName();
        if (model.isFullNamePresent(fullName)) {
            throw new CommandException(MESSAGE_DUPLICATE_FULL_NAME);
        }

        Prefix shortName = toAdd.getPrefix();
        if (model.isPrefixPresent(shortName)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHORT_NAME);
        }

        model.addCustomProperty(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CustomCommand // instanceof handles nulls
            && toAdd.equals(((CustomCommand) other).toAdd));
    }
}
