package seedu.deliverymans.logic.commands.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.deliverymans.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import seedu.deliverymans.logic.commands.Command;
import seedu.deliverymans.logic.commands.CommandResult;
import seedu.deliverymans.logic.commands.exceptions.CommandException;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.restaurant.Rating;
import seedu.deliverymans.model.restaurant.Restaurant;

/**
 * Adds a rating to the restaurant in editing mode.
 */
public class AddRatingCommand extends Command {
    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a rating to the restaurant in editing mode currently.\n"
            + "Parameters: RATING (must be a non-negative integer from 0 to 5)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ADD_RATING_SUCCESS = "Added Rating to: %1$s";

    private final Rating rating;

    /**
     * Creates a AddRatingCommand to add the specified {@code Rating}
     */
    public AddRatingCommand(Rating rating) {
        this.rating = rating;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Restaurant restaurant = model.getEditingRestaurantList().get(0);
        Rating oldRating = restaurant.getRating();
        double newRatingValue = Rating.getRatingValue(oldRating.rating)
                + Rating.getRatingValue(rating.rating);
        Rating newRating = new Rating(String.format("%.3f", newRatingValue),
                oldRating.numberOfRatings + 1);
        Restaurant newRestaurant = new Restaurant(restaurant.getName(), restaurant.getLocation(), newRating,
                restaurant.getTags(), restaurant.getMenu(), restaurant.getOrders());

        model.setRestaurant(restaurant, newRestaurant);
        model.setEditingRestaurant(newRestaurant);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        model.updateEditingRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);

        return new CommandResult(String.format(MESSAGE_ADD_RATING_SUCCESS, newRestaurant));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRatingCommand // instanceof handles nulls
                && rating.equals(((AddRatingCommand) other).rating)); // state check
    }
}
