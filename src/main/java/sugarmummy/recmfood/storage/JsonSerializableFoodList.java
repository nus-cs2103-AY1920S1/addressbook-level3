package sugarmummy.recmfood.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.storage.JsonSerializableContent;
import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.UniqueFoodList;

/**
 * Represents an Immutable FoodList that is serializable to JSON format.
 */
@JsonRootName(value = "foodlist")
public class JsonSerializableFoodList implements JsonSerializableContent<UniqueFoodList> {

    public static final String MESSAGE_DUPLICATE_FOOD = "Food list contains duplicate food(s).";

    private final List<JsonAdaptedFood> foods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given foods.
     */
    @JsonCreator
    public JsonSerializableFoodList(@JsonProperty("foods") List<JsonAdaptedFood> foods) {
        this.foods.addAll(foods);
    }

    /**
     * Converts a given {@code UniqueFoodList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFoodList}.
     */
    public JsonSerializableFoodList(UniqueFoodList source) {
        foods.addAll(source.asUnmodifiableObservableList().stream().map(JsonAdaptedFood::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this food list into the sugarmummy.recmfood.model's {@code UniqueFoodList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    @Override
    public UniqueFoodList toModelType() throws IllegalValueException {
        UniqueFoodList foodList = new UniqueFoodList();
        for (JsonAdaptedFood jsonAdaptedFood : foods) {
            Food food = jsonAdaptedFood.toModelType();
            if (foodList.contains(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOOD);
            }
            foodList.add(food);
        }
        return foodList;
    }

}

