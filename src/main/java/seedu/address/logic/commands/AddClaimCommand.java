package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.claim.Claim;

/**
 * Adds a person to the address book.
 */
public class AddClaimCommand extends Command {

    public static final String COMMAND_WORD = "add_claim";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a claim. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_CASH + "CASH AMOUNT "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Logistics for Hackathon"
            + PREFIX_CASH + "102.50"
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New claim added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This claim already exists";

    private final Claim toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddClaimCommand(Claim claim) {
        requireNonNull(claim);
        toAdd = claim;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasClaim(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addClaim(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddClaimCommand // instanceof handles nulls
                && toAdd.equals(((AddClaimCommand) other).toAdd));
    }
}
