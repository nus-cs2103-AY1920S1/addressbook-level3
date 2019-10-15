package seedu.savenus.logic.commands;

import static seedu.savenus.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.savenus.model.Model;
import seedu.savenus.model.RecommendationSystem;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

/**
 * Creates a PreferenceCommand that either adds likes or dislikes to the $aveNUS recommendation system.
 */
public class PreferenceCommand extends Command {
    public static final String COMMAND_WORD = "like/dislike";
    public static final String MESSAGE_SUCCESS = "Success!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Likes or dislikes a particular category, tag "
            + "or location in our menu. Parameters: [" + PREFIX_CATEGORY + "CATEGORY]... [" + PREFIX_TAG + "TAG]... ["
            + PREFIX_LOCATION + "...]\n" + "Example: " + COMMAND_WORD + " " + PREFIX_CATEGORY + "Chinese "
            + PREFIX_CATEGORY + "Western " + PREFIX_LOCATION + "University Town " + PREFIX_LOCATION + "The Deck "
            + PREFIX_TAG + "Spicy " + PREFIX_TAG + "Healthy";

    private final Set<Category> categoryList;
    private final Set<Tag> tagList;
    private final Set<Location> locationList;

    /**
     * Creates an PreferenceCommand to add the user's recommendations
     */
    public PreferenceCommand(Set<Category> categoryList, Set<Tag> tagList, Set<Location> locationList) {
        requireAllNonNull(categoryList, tagList, locationList);

        // Convert all to lowercase
        this.categoryList = categoryList.stream()
                .map(c -> new Category(c.category.toLowerCase())).collect(Collectors.toSet());
        this.tagList = tagList.stream()
                .map(t -> new Tag(t.tagName.toLowerCase())).collect(Collectors.toSet());
        this.locationList = locationList.stream()
                .map(l -> new Location(l.location.toLowerCase())).collect(Collectors.toSet());
    }

    @Override
    public CommandResult execute(Model model) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Executes the preference command.
     *
     * @param model  The current model
     * @param isLike True if adding likes or false if adding dislikes
     * @return A success message including the list of likes and dislikes
     */
    public CommandResult execute(Model model, boolean isLike) {
        StringBuilder result = new StringBuilder();

        if (isLike) {
            model.addLikes(categoryList, tagList, locationList);
            result.append(" Liked: ");
        } else {
            model.addDislikes(categoryList, tagList, locationList);
            result.append(" Disliked: ");
        }

        String addedItems = "Categories: " + Arrays.toString(categoryList.toArray())
                + " Tags: " + Arrays.toString(tagList.toArray())
                + " Locations: " + Arrays.toString(locationList.toArray()) + "\n";
        result.append(addedItems);

        RecommendationSystem newSystem = model.getRecommendationSystem();
        String currentItems = "Current likes: Categories: " + Arrays.toString(newSystem.getLikedCategories().toArray())
                + " Tags: " + Arrays.toString(newSystem.getLikedTags().toArray())
                + " Locations: " + Arrays.toString(newSystem.getLikedLocations().toArray())
                + "\nCurrent dislikes: Categories: " + Arrays.toString(newSystem.getDislikedCategories().toArray())
                + " Tags: " + Arrays.toString(newSystem.getDislikedTags().toArray())
                + " Locations: " + Arrays.toString(newSystem.getDislikedLocations().toArray());
        result.append(currentItems);

        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
