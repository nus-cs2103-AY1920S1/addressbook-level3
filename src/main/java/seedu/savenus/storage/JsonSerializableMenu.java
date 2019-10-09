package seedu.savenus.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.savenus.commons.exceptions.IllegalValueException;
import seedu.savenus.model.Menu;
import seedu.savenus.model.ReadOnlyMenu;
import seedu.savenus.model.food.Food;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableMenu {

    public static final String MESSAGE_DUPLICATE_FOOD = "foods list contains duplicate food(s).";

    private final List<JsonAdaptedFood> foods = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given foods.
     */
    @JsonCreator
    public JsonSerializableMenu(@JsonProperty("foods") List<JsonAdaptedFood> foods) {
        this.foods.addAll(foods);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableMenu(ReadOnlyMenu source) {
        foods.addAll(source.getFoodList().stream().map(JsonAdaptedFood::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Menu toModelType() throws IllegalValueException {
        Menu menu = new Menu();
        for (JsonAdaptedFood jsonAdaptedFood : foods) {
            Food food = jsonAdaptedFood.toModelType();
            if (menu.hasFood(food)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FOOD);
            }
            menu.addFood(food);
        }
        return menu;
    }

}
