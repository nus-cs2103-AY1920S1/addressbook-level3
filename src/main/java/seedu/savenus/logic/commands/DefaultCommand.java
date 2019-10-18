package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.savenus.logic.commands.exceptions.CommandException;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.DefaultComparator;
import seedu.savenus.model.food.Food;

/**
 * Sorts all the foods in the $aveNUS menu based on their default ordering, which is:
 * category, followed by name, then by price.
 */
public class DefaultCommand extends Command {

    public static final String COMMAND_WORD = "default";
    public static final String MESSAGE_SUCCESS = "Food items back in natural order.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Food> foodList = model.getFilteredFoodList();
        SortedList<Food> sortedList = foodList.sorted(new DefaultComparator());
        model.setFoods(sortedList);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DefaultCommand);
    }

}
