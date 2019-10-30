package seedu.savenus.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.savenus.model.Model;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.sort.CustomSorter;

/**
 * An Extended Commmand to do Sorting based on user specified custom fields.
 */
public class CustomSortCommand extends Command {
    public static final String COMMAND_WORD = "customsort";

    public static final String MESSAGE_SUCCESS = "You have successfully sorted the food items! \n"
            + "Current CustomSorter: %s";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Food> foodList = model.getFilteredFoodList();
        CustomSorter sorter = model.getCustomSorter();
        SortedList<Food> sortedList = foodList.sorted(sorter.getComparator());
        model.setFoods(sortedList);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sorter));
    }
}
