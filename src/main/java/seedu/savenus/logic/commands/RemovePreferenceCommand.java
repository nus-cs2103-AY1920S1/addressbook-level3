package seedu.savenus.logic.commands;

import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.recommend.RecommendationSystem;
import seedu.savenus.model.recommend.UserRecommendations;
import seedu.savenus.model.tag.Tag;

/**
 * Creates a RemovePreferenceCommand that either removes likes or dislikes from the $aveNUS recommendation system.
 */
public class RemovePreferenceCommand extends Command {
    public static final String COMMAND_WORD = "removelike/removedislike";
    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes likes or dislikes from a particular category, tag or location in our menu. Parameters: ["
            + PREFIX_CATEGORY + "CATEGORY]... [" + PREFIX_TAG + "TAG]... ["
            + PREFIX_LOCATION + "...]\n" + "Example: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "Chinese "
            + PREFIX_CATEGORY + "Western " + PREFIX_LOCATION + "University Town " + PREFIX_LOCATION + "The Deck "
            + PREFIX_TAG + "Spicy " + PREFIX_TAG + "Healthy";

    public static final String NOT_FOUND = "Please ensure all entries to remove currently exist in the list!";

    public final Set<Category> categoryList;
    public final Set<Tag> tagList;
    public final Set<Location> locationList;

    private final boolean isRemoveAll;

    /**
     * Creates an RemovePreferenceCommand to add the user's recommendations
     */
    public RemovePreferenceCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList,
                                   boolean isRemoveAll) {
        requireAllNonNull(categoryList, tagList, locationList, isRemoveAll);

        // Convert all to lowercase
        this.categoryList = categoryList.stream()
                .map(c -> new Category(c.category.toLowerCase())).collect(Collectors.toSet());
        this.tagList = tagList.stream()
                .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet());
        this.locationList = locationList.stream()
                .map(l -> new Location(l.location.toLowerCase())).collect(Collectors.toSet());

        this.isRemoveAll = isRemoveAll;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Executes the remove preference command.
     *
     * @param model  The current model
     * @param isLike True if adding likes or false if adding dislikes
     * @return A success message including the list of removed likes and dislikes
     */
    public CommandResult execute(Model model, boolean isLike, boolean isRemoveAll) throws CommandException {
        RecommendationSystem recommendationSystem = model.getRecommendationSystem();
        UserRecommendations userRecommendations = recommendationSystem.getUserRecommendations();

        StringBuilder result = new StringBuilder();

        if (isLike && isRemoveAll) {
            model.clearLikes();
            result.append("Removed all likes!\n");
        } else if (!isLike && isRemoveAll) {
            model.clearDislikes();
            result.append("Removed all dislikes!\n");
        }

        if (isLike && !isRemoveAll) {
            if (!(userRecommendations.getLikedCategories().containsAll(categoryList)
                    && userRecommendations.getLikedTags().containsAll(tagList)
                    && userRecommendations.getLikedLocations().containsAll(locationList))) {
                throw new CommandException(NOT_FOUND);
            }

            model.removeLikes(categoryList, tagList, locationList);
            result.append("Removed likes: ");
        } else if (!isLike && !isRemoveAll) {
            if (!(userRecommendations.getDislikedCategories().containsAll(categoryList)
                    && userRecommendations.getDislikedTags().containsAll(tagList)
                    && userRecommendations.getDislikedLocations().containsAll(locationList))) {
                throw new CommandException(NOT_FOUND);
            }

            model.removeDislikes(categoryList, tagList, locationList);
            result.append("Removed dislikes: ");
        }

        if (!isRemoveAll) {
            String addedItems = "Categories: " + categoryList.stream()
                    .map(c -> c.category).collect(Collectors.joining(", "))
                    + " | Tags: " + tagList.stream()
                    .map(t -> t.tagName).collect(Collectors.joining(", "))
                    + " | Locations: " + locationList.stream()
                    .map(l -> l.location).collect(Collectors.joining(", ")) + "\n";

            result.append(addedItems);
        }

        result.append(userRecommendations.toString());

        return new CommandResult(MESSAGE_SUCCESS + " " + result);
    }
}
