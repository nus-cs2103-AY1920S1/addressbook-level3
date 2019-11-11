package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.feed.Feed;

/**
 * Adds a feed to the feed list.
 */
public class AddFeedCommand extends Command {

    public static final String COMMAND_WORD = "addfeed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a feed to the feed list.\n"
            + "Parameters: "
            + PREFIX_NAME + " [name] "
            + PREFIX_ADDRESS + " [address]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Eatbook "
            + PREFIX_ADDRESS + " https://eatbook.sg/feed";

    public static final String MESSAGE_SUCCESS = "Feed successfully added: %s";
    public static final String MESSAGE_DUPLICATE_FEED = "This feed already exists!";

    private final Feed toAdd;

    /**
     * Creates an AddFeedCommand to add the specified {@code Feed}
     */
    public AddFeedCommand(Feed feed) {
        requireNonNull(feed);
        toAdd = feed;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasFeed(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_FEED);
        }

        model.addFeed(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddFeedCommand // instanceof handles nulls
                && toAdd.equals(((AddFeedCommand) other).toAdd));
    }
}
