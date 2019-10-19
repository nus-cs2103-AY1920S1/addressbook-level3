package seedu.address.storage;

import java.nio.file.Path;


import seedu.sgm.model.food.UniqueFoodList;

public class FoodListStorage extends JsonGeneralStorage<UniqueFoodList, JsonSerializableFoodList> {
    public FoodListStorage(Path filePath) {
        super(filePath, UniqueFoodList.class, JsonSerializableFoodList.class);
    }
}
