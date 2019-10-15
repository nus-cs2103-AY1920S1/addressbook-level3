package seedu.ezwatchlist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_ACTOR;

import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.show.Show;

/**
 * Adds a show to the watchlist.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a show to the watchlist. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE_OF_RELEASE + "DATE OF RELEASE "
            + PREFIX_IS_WATCHED + "WATCHED? "
            + PREFIX_RUNNING_TIME + "RUNNING TIME "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_ACTOR + "ACTOR]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Joker "
            + PREFIX_DATE_OF_RELEASE + "4 October 2019 "
            + PREFIX_IS_WATCHED + "true "
            + PREFIX_RUNNING_TIME + "122 "
            + PREFIX_DESCRIPTION + "Joker is funny "
            + PREFIX_ACTOR + "Joaquin Phoenix"
            + PREFIX_ACTOR + "Robert De Niro";

    public static final String MESSAGE_SUCCESS = "New show added: %1$s";
    public static final String MESSAGE_DUPLICATE_SHOW = "This show already exists in the watchlist";

    private final Show toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Show}
     */
    public AddCommand(Show show) {
        requireNonNull(show);
        toAdd = show;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasShow(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHOW);
        }

        model.addShow(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
