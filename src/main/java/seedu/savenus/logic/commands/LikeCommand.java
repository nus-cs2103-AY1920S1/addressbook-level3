package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

import java.util.Set;

/**
 * Adds the user's liked recommendations to the $aveNUS recommendation system.
 */
public class LikeCommand extends Command {

    public static final String COMMAND_WORD = "like";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Likes a particular category, tag or location "
            + "in our menu. Parameters: [" + PREFIX_CATEGORY + "CATEGORY]... [" + PREFIX_TAG + "TAG]... ["
            + PREFIX_LOCATION + "...]\n" + "Example: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "Chinese "
            + PREFIX_CATEGORY + "Western " + PREFIX_LOCATION + "University Town " + PREFIX_LOCATION + "The Deck "
            + PREFIX_TAG + "Spicy " + PREFIX_TAG + "Healthy";

    public static final String MESSAGE_SUCCESS = "Success!";

    private final Set<Category> categoryList;
    private final Set<Tag> tagList;
    private final Set<Location> locationList;

    /**
     * Creates an LikeCommand to add the user's recommendations
     */
    public LikeCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);
        this.categoryList = categoryList;
        this.tagList = tagList;
        this.locationList = locationList;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        StringBuilder added = new StringBuilder();
        added.append(" Added liked categories: ");

        for (Category c : categoryList) {
            System.out.println(c);
            added.append(c.toString()).append(", ");
        }

        added.append("Added liked tags: ");
        for (Tag t : tagList) {
            System.out.println(t);
            added.append(t.toString()).append(", ");
        }

        added.append("Added liked locations: ");
        for (Location l : locationList) {
            System.out.println(l);
            added.append(l.toString()).append(", ");
        }

        CommandResult commandResult = new CommandResult(MESSAGE_SUCCESS + added.toString());
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LikeCommand // instanceof handles nulls
                && categoryList.equals(((LikeCommand) other).categoryList))
                && tagList.equals(((LikeCommand) other).tagList)
                && locationList.equals(((LikeCommand) other).locationList);
    }
}
