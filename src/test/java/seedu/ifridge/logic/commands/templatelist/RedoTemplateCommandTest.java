package seedu.ifridge.logic.commands.templatelist;

import static seedu.ifridge.testutil.TypicalBoughtList.getTypicalBoughtList;
import static seedu.ifridge.testutil.TypicalGroceryItems.getTypicalGroceryList;
import static seedu.ifridge.testutil.TypicalShoppingList.getTypicalShoppingList;
import static seedu.ifridge.testutil.TypicalTemplateList.getTypicalTemplateList;
import static seedu.ifridge.testutil.TypicalWasteArchive.getTypicalWasteArchive;

import java.util.HashMap;

import seedu.ifridge.model.Model;
import seedu.ifridge.model.ModelManager;
import seedu.ifridge.model.UnitDictionary;
import seedu.ifridge.model.UserPrefs;

public class RedoTemplateCommandTest {
    private Model model = new ModelManager(getTypicalGroceryList(), new UserPrefs(), getTypicalTemplateList(),
            getTypicalWasteArchive(), getTypicalShoppingList(), getTypicalBoughtList(),
            new UnitDictionary(new HashMap<String, String>()));

    /*@Test
    public void execute_templateListCanRedo_redoSuccessful() {
        List<UniqueTemplateItems> lastShownList = model.getFilteredTemplateList();
        UniqueTemplateItems templateToDelete = lastShownList.get(INDEX_FIRST_PERSON.getZeroBased());
        model.deleteTemplate(templateToDelete);
        model.commitTemplateList(null, null, -1);
        RedoTemplateCommand redoTemplateCommand = new RedoTemplateCommand();

        String expectedMessage = RedoTemplateCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getGroceryList(), new UserPrefs(), model.getTemplateList(),
                model.getWasteArchive(), model.getShoppingList(), model.getBoughtList(), model.getUnitDictionary());
        expectedModel.deleteTemplate(templateToDelete);

        model.deleteTemplate(templateToDelete);
        model.commitTemplateList(null, null, -1);

        TemplateCommandTestUtil.assertCommandSuccess(redoTemplateCommand, model, expectedMessage, expectedModel);
    }*/
}
