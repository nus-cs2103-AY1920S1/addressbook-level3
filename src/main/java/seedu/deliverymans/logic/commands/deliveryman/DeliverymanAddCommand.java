package seedu.deliverymans.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.deliverymans.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.deliveryman.Deliveryman;

/**
 * Adds a deliveryman to the deliveryman book.
 * User must be in deliveryman context to use this command.
 */
public class DeliverymanAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deliveryman to the deliveryman book.\n "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "96177613";

    public static final String MESSAGE_SUCCESS = "New deliveryman added: %1$s";
    public static final String MESSAGE_DUPLICATE_DELIVERYMAN =
            "This deliveryman already exists in the deliveryman book.";

    private final Deliveryman toAdd;

    /**
     * Creates a DeliverymanAddCommand to add the specified {@code Deliveryman}
     */
    public DeliverymanAddCommand(Deliveryman deliveryman) {
        requireNonNull(deliveryman);
        toAdd = deliveryman;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasDeliveryman(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DELIVERYMAN);
        }

        model.addDeliveryman(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliverymanAddCommand // instanceof handles null
                && toAdd.equals(((DeliverymanAddCommand) other).toAdd));
    }

}
