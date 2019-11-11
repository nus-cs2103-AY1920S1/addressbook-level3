package seedu.address.logic.commands.verification;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_MODULE;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Describes a particular module.
 */
public class DescriptionCommand extends Command {

    public static final String COMMAND_WORD = "description";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Viewing description of a module";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Describes a particular module.\n"
            + "Parameters: MODULE (must be a valid module code)\n"
            + "Example: " + COMMAND_WORD + " CS2040S";

    private final String moduleCode;

    public DescriptionCommand(String moduleCode) {
        requireAllNonNull(moduleCode);
        this.moduleCode = moduleCode.toUpperCase();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String result = model.getModuleInformation(this.moduleCode);
        if (result == null) {
            throw new CommandException(MESSAGE_INVALID_MODULE);
        }
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DescriptionCommand)) {
            return false;
        }

        // state check
        DescriptionCommand e = (DescriptionCommand) other;
        return moduleCode.equals(e.moduleCode);
    }
}
