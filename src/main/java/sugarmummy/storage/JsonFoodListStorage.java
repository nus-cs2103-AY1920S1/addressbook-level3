package sugarmummy.storage;

import java.nio.file.Path;

import sugarmummy.recmfood.model.UniqueFoodList;

/**
 * Represents the specific version {@code JsonGeneralStorage} about food list.
 */
public class JsonFoodListStorage extends JsonGeneralStorage<UniqueFoodList, JsonSerializableFoodList> {

    public JsonFoodListStorage(Path filePath) {
        super(filePath, UniqueFoodList.class, JsonSerializableFoodList.class);
    }
}
