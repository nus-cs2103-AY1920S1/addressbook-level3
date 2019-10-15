package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.cca.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.cca.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.cca.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;

/**
 * Adds a cca to Jarvis.
 */
public class AddCcaCommand extends Command {

    public static final String COMMAND_WORD = "add-cca";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a cca to Jarvis. "
            + "Parameters: "
            + PREFIX_CCA_NAME + "NAME "
            + PREFIX_CCA_TYPE + "TYPE "
            + "[" + PREFIX_EQUIPMENT_NAME + "EQUIPMENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA_NAME + "Swimming "
            + PREFIX_CCA_TYPE + "sport "
            + PREFIX_EQUIPMENT_NAME + "swimming trunks"
            + PREFIX_EQUIPMENT_NAME + "goggles";

    public static final String MESSAGE_SUCCESS = "New Cca added: %1$s";
    public static final String MESSAGE_DUPLICATE_CCA = "This cca already exists in the cca tracker.";

    private final Cca toAddCca;

    /**
     * Creates an {@code AddCcaCommand} to add the specified {@code Person}.
     */
    public AddCcaCommand(Cca cca) {
        requireNonNull(cca);
        toAddCca = cca;
    }

    @Override
    public boolean hasInverseExecution() {
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCca(toAddCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        model.addCca(toAddCca);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddCca));

    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCcaCommand)) {
            return false;
        }

        // state check
        AddCcaCommand e = (AddCcaCommand) other;
        return toAddCca.equals(e.toAddCca);
    }
}
