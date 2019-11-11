package seedu.ifridge.logic.commands.templatelist;

import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.CommandTestUtil;
import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;
import seedu.ifridge.model.food.UniqueTemplateItems;

public class RedoTemplateCommandTest {
    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    @Test
    public void execute_templateListCanRedo_redoSuccessful() {
        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());

        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        UniqueTemplateItems templateToDelete = lastShownList.get(INDEX_FIRST_PERSON.getZeroBased());
        RedoTemplateCommand redoTemplateCommand = new RedoTemplateCommand();

        model.deleteTemplate(templateToDelete);
        model.commitTemplateList(null, null, -1);
        model.undoTemplateList();

        String expectedMessage = RedoTemplateCommand.MESSAGE_SUCCESS;

        expectedModel.deleteTemplate(templateToDelete);
        expectedModel.commitTemplateList(null, null, -1);

        CommandTestUtil.assertCommandSuccess(redoTemplateCommand, model, expectedMessage, expectedModel);
    }
}
