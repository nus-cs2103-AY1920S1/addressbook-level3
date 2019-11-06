package seedu.sugarmummy.storage.foodrecommendations;

import java.nio.file.Path;

import seedu.sugarmummy.model.foodrecommendations.UniqueFoodList;
import seedu.sugarmummy.storage.JsonGeneralStorage;

/**
 * Represents the specific version {@code JsonGeneralStorage} about food list.
 */
public class JsonFoodListStorage extends JsonGeneralStorage<UniqueFoodList, JsonSerializableFoodList> {

    public JsonFoodListStorage(Path filePath) {
        super(filePath, UniqueFoodList.class, JsonSerializableFoodList.class);
    }
}
