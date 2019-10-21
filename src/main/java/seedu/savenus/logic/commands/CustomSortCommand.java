package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;

/**
 * An Extended Commmand to do Sorting based on user specified custom fields.
 */
public class CustomSortCommand extends Command {
    public static final String COMMAND_WORD = "customsort";

    public static final String MESSAGE_SUCCESS = "You have successfully sorted the food items!!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Food> foodList = model.getFilteredFoodList();
        SortedList<Food> sortedList = foodList.sorted(model.getCustomSorter().getComparator());
        model.setFoods(sortedList);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
