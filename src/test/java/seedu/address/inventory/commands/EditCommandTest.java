package seedu.address.inventory.commands;

import org.junit.jupiter.api.Test;
import seedu.address.inventory.logic.commands.EditCommand;
import seedu.address.inventory.model.Item;
import seedu.address.inventory.model.ModelManager;
import seedu.address.testutil.EditItemDescriptorBuilder;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.TypicalItem;

public class EditCommandTest {
    private ModelManager model = new ModelManager(TypicalItem.getTypicalInventoryListForInventoryUse());

    @Test
    public void edit_validIndex_successful() {
        
    }
}