package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.competition.Competition;

/**
 * Adds a Competition to the address book.
 */
public class AddCompCommand extends Command {
    public static final String COMMAND_WORD = "addComp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a competition to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "COMPETITION NAME "
            + PREFIX_START_DATE + "START DATE OF COMPETITION "
            + PREFIX_END_DATE + "END DATE OF COMPETITION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "IPF World's "
            + PREFIX_START_DATE + "12/02/1995 "
            + PREFIX_END_DATE + "15/02/1995 ";

    public static final String MESSAGE_SUCCESS = "New competition added: %1$s";
    public static final String MESSAGE_DUPLICATE_COMPETITION = "This competition already exists in the address book";

    private final Competition toAdd;

    /**
     * Creates an CreateComp to add the specified {@code newCompetition}
     */

    public AddCompCommand(Competition newCompetition) {
        requireNonNull(newCompetition);
        toAdd = newCompetition;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCompetition(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPETITION);
        }

        model.addCompetition(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCompCommand // instanceof handles nulls
                && toAdd.equals(((AddCompCommand) other).toAdd));
    }
}
