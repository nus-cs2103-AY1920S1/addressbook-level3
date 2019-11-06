package seedu.sugarmummy.storage.recmf;

import java.nio.file.Path;

import seedu.sugarmummy.model.recmf.UniqueFoodList;
import seedu.sugarmummy.storage.JsonGeneralStorage;

/**
 * Represents the specific version {@code JsonGeneralStorage} about food list.
 */
public class JsonFoodListStorage extends JsonGeneralStorage<UniqueFoodList, JsonSerializableFoodList> {

    public JsonFoodListStorage(Path filePath) {
        super(filePath, UniqueFoodList.class, JsonSerializableFoodList.class);
    }
}
