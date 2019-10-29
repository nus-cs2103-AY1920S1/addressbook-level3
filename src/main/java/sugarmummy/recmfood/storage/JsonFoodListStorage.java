package sugarmummy.recmfood.storage;

import java.nio.file.Path;

import seedu.address.storage.JsonGeneralStorage;
import sugarmummy.recmfood.model.UniqueFoodList;

/**
 * Represents the specific version {@code JsonGeneralStorage} about food list.
 */
public class JsonFoodListStorage extends JsonGeneralStorage<UniqueFoodList, JsonSerializableFoodList> {

    public JsonFoodListStorage(Path filePath) {
        super(filePath, UniqueFoodList.class, JsonSerializableFoodList.class);
    }
}
