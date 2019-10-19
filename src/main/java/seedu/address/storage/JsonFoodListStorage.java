package seedu.address.storage;

import java.nio.file.Path;

import seedu.sgm.model.food.UniqueFoodList;

/**
 * Represents the specific version {@code JsonGeneralStorage} about food list.
 */
public class JsonFoodListStorage extends JsonGeneralStorage<UniqueFoodList, JsonSerializableFoodList> {

    public JsonFoodListStorage(Path filePath) {
        super(filePath, UniqueFoodList.class, JsonSerializableFoodList.class);
    }
}
