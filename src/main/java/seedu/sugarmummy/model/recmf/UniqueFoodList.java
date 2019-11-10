package seedu.sugarmummy.model.recmf;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.ReadOnlyData;
import seedu.sugarmummy.model.recmf.exceptions.DuplicateFoodException;
import seedu.sugarmummy.model.recmf.exceptions.FoodNotFoundException;

/**
 * This class represents a list of foods that enforces uniqueness between its elements and does not allow nulls. A food
 * is considered unique by comparing using {@code Food#isSameFood(Food)}.
 *
 * @see Food#isSameFood(Food)
 */
public class UniqueFoodList implements Iterable<Food>, ReadOnlyData {

    protected final ObservableList<Food> internalList = FXCollections.observableArrayList();
    protected final ObservableList<Food> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private final Logger logger = LogsCenter.getLogger(UniqueFoodList.class);

    /**
     * Returns true if the list contains an equivalent food as the given argument.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameFood);
    }

    /**
     * Adds a food to the list. The food must not already exist in the list.
     */
    public void add(Food toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the food with specified food name from the list.
     */
    public void delete(FoodName foodName) {
        requireNonNull(foodName);
        Optional<Food> targetFood = internalList.stream().filter(food -> food.getFoodName().equals(foodName)).findAny();
        if (targetFood.isEmpty()) {
            throw new FoodNotFoundException();
        } else {
            assert internalList.contains(targetFood.get());
            internalList.remove(targetFood.get());
        }
    }

    public void setFoods(UniqueFoodList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code foods}. {@code foods} must not contain duplicate foods.
     */
    public void setFoods(List<Food> foods) {
        requireAllNonNull(foods);
        if (!foodsAreUnique(foods)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foods);
    }

    public void sortFoods(FoodComparator foodComparator) {
        internalList.sort(foodComparator);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Food> getUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    private Food getRandomFood(Food[] foodList) {
        Random random = new Random();
        return foodList[(random.nextInt(foodList.length))];
    }

    /**
     * Returns a random mix of one food from each type. A {@code Food} with {@code FoodName} "Summary" that calculates
     * the total nutrition values of foods in this random combination will be appended to the end.
     *
     * @return a {@code ObservableList} that contains a mixed combination of foods from each type
     */
    public ObservableList<Food> getMixedFoodList() {

        List<Food> mixedFoods = Stream.of(FoodType.values())
                .map(type -> internalUnmodifiableList.stream().filter(f -> f.getFoodType().equals(type))
                        .toArray(Food[]::new))
                .filter(arr -> arr.length > 0)
                .map(foodArr -> getRandomFood(foodArr))
                .collect(Collectors.toList());

        if (mixedFoods.size() == 0) {
            return FXCollections.observableArrayList();
        }
        FoodCalculator foodCalculator = new FoodCalculator(mixedFoods);
        Food summaryFood = new Food(new FoodName("Summary"), foodCalculator.getCalorieSum(),
                foodCalculator.getGiAverage(), foodCalculator.getSugarSum(), foodCalculator.getFatSum(), FoodType.MEAL);
        mixedFoods.add(summaryFood);

        ObservableList<Food> mixedFoodList = FXCollections.observableArrayList();
        mixedFoodList.setAll(mixedFoods);

        logger.info("----------------[Mixed Food][" + mixedFoodList + "]");

        return mixedFoodList;
    }

    @Override
    public Iterator<Food> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueFoodList
                && internalList.equals(((UniqueFoodList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code foods} contains only unique foods.
     */
    private boolean foodsAreUnique(List<Food> foods) {
        for (int i = 0; i < foods.size() - 1; i++) {
            for (int j = i + 1; j < foods.size(); j++) {
                if (foods.get(i).isSameFood(foods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
