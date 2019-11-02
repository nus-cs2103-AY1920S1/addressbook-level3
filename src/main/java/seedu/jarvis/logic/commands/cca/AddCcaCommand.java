package seedu.jarvis.logic.commands.cca;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_NAME;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_CCA_TYPE;
import static seedu.jarvis.logic.parser.CliSyntax.CcaTrackerCliSyntax.PREFIX_EQUIPMENT_NAME;
import static seedu.jarvis.model.cca.CcaTrackerModel.PREDICATE_SHOW_ALL_CCAS;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.cca.JsonAdaptedAddCcaCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

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
            + PREFIX_EQUIPMENT_NAME + "swimming trunks "
            + PREFIX_EQUIPMENT_NAME + "goggles";

    public static final String MESSAGE_SUCCESS = "New Cca added: %1$s";
    public static final String MESSAGE_DUPLICATE_CCA = "This cca already exists in the cca tracker.";

    public static final String MESSAGE_INVERSE_SUCCESS_DELETE = "Deleted Cca: %1$s";
    public static final String MESSAGE_INVERSE_CCA_NOT_FOUND = "Cca already deleted: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Cca toAddCca;

    /**
     * Creates an {@code AddCcaCommand} to add the specified {@code Person}.
     */
    public AddCcaCommand(Cca cca) {
        requireNonNull(cca);
        toAddCca = cca;
    }

    /**
     * Gets the command word of the command.
     *
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Gets the {@code Cca} that was added.
     *
     * @return {@code Cca} that was added.
     */
    public Cca getAddedCca() {
        return toAddCca;
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.containsCca(toAddCca)) {
            throw new CommandException(MESSAGE_DUPLICATE_CCA);
        }

        model.addCca(toAddCca);
        model.updateFilteredCcaList(PREDICATE_SHOW_ALL_CCAS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddCca), false);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.containsCca(toAddCca)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_CCA_NOT_FOUND, toAddCca));
        }

        model.removeCca(toAddCca);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_DELETE, toAddCca));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedAddCcaCommand(this);
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
